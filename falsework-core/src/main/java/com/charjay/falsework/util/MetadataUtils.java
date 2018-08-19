package com.charjay.falsework.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.charjay.falsework.bean.Column;
import com.charjay.falsework.bean.JdbcParam;
import com.charjay.falsework.bean.Table;
import com.charjay.falsework.jdbc.JdbcConnector;
import com.charjay.falsework.util.CustomFileUtils;


/**
 * 元数据工具
 * 
 * @author
 */
public class MetadataUtils {
    
    /**
     * 查找表的元数据
     *
     * @param jdbcParam
     *            jdbc连接参数
     * @param like
     *            表名模糊匹配。支持：TABLE_NAME %NAME TABLE% %BLE%
     * @param include
     *            包含表
     * @param exclude
     *            排除表
     * @return
     * @throws SQLException
     */
    public static List<Table> findTables( JdbcParam jdbcParam, List<String> likes, List<String> include, List<String> exclude )
            throws SQLException {
        JdbcConnector connector = new JdbcConnector( jdbcParam.getDriver(), jdbcParam.getUrl(), jdbcParam.getUsername(),
                jdbcParam.getPassword() );
        
        List<String> includeCopy = null;
        if (include != null) {
            includeCopy = new LinkedList<String>( include );
        }
        Connection conn = null;
        ResultSet rs = null;
        try {
            Properties props = new Properties();
            props.put( "remarksReporting", "true" );// oracle需要此参数来取注释
            conn = connector.getLocalCon( props );
            Map<String, String> tableComments = getTableSComment( conn, jdbcParam.getDriver() );
            
            DatabaseMetaData dbmd = conn.getMetaData();
            
            List<Table> tables = new LinkedList<Table>();
            for(String like : likes){
                rs = dbmd.getTables( null, jdbcParam.getSchema(), like, new String[] { "TABLE" } );
                while (rs.next()) {
                    String tname = rs.getString( "TABLE_NAME" );
                    
                    if (exclude != null && exclude.contains( tname )) {
                        continue;// 排除
                    }
                    
                    if (includeCopy != null && includeCopy.contains( tname )) {
                        includeCopy.remove( tname );
                    }
                    
                    Table table = new Table();
                    table.setSqlName( tname );
                    table.setComment( tableComments.get( tname ) );
                    tables.add( table );
                }
                rs.close();
            }
            
            if (includeCopy != null && !includeCopy.isEmpty()) {
                for (String includeTname : includeCopy) {
                    rs = dbmd.getTables( null, jdbcParam.getSchema(), includeTname, new String[] { "TABLE" } );
                    while (rs.next()) {
                        Table table = new Table();
                        table.setSqlName( rs.getString( "TABLE_NAME" ) );
                        table.setComment( rs.getString( "REMARKS" ) );
                        tables.add( table );
                    }
                    rs.close();
                }
            }
            
            // 补充每张表的信息
            for (Table t : tables) {
                // 列
                LinkedHashMap<String, Column> columnsMap = new LinkedHashMap<String, Column>();
                t.setColumnsMap( columnsMap );
                rs = dbmd.getColumns( null, jdbcParam.getSchema(), t.getSqlName(), null );
                while (rs.next()) {
                    Column column = new Column();
                    column.setTableName( t.getSqlName() );
                    column.setSqlName( rs.getString( "COLUMN_NAME" ) );
                    column.setDataType( rs.getInt( "DATA_TYPE" ) );
                    column.setTypeName( rs.getString( "TYPE_NAME" ) );
                    int size = rs.getInt( "COLUMN_SIZE" );
                    column.setDataLen( size );
                    column.setDataPrecision( size );
                    column.setDataScale( rs.getInt( "DECIMAL_DIGITS" ) );
                    column.setDefaultVal( rs.getString( "COLUMN_DEF" ) );
                    column.setComment( rs.getString( "REMARKS" ) );
                    column.setNullable( "YES".equals( rs.getString( "IS_NULLABLE" ) ) );
                    
                    columnsMap.put( column.getSqlName(), column );
                }
                rs.close();
                // 主键
                rs = dbmd.getPrimaryKeys( null, jdbcParam.getSchema(), t.getSqlName() );
                while (rs.next()) {
                    String pkcol = rs.getString( "COLUMN_NAME" );
                    t.getColumnsMap().get( pkcol ).setPkcol( true );
                    short ks = rs.getShort( "KEY_SEQ" );
                    if (ks < 1.5) {
                        t.setPkColName( pkcol );
                    }
                }
                rs.close();
            }
            
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            CustomFileUtils.closeStreams( rs );
            connector.closeLocalCon();
        }
        
    }
    
    public static Map<String, String> getTableSComment( Connection conn, String driver ) throws SQLException {
        Map<String, String> resultMap = new HashMap<>();
        Statement st = conn.createStatement();
        ResultSet rt = null;
        try {
            if (driver.contains( "oracle" )) {
                rt = st.executeQuery( "SELECT * FROM USER_TAB_COMMENTS WHERE TABLE_TYPE = 'TABLE' " );
                while (rt.next()) {
                    resultMap.put( rt.getString( "TABLE_NAME" ), rt.getString( "COMMENTS" ) );
                }
            } else if (driver.contains( "mysql" )) {
                rt = st.executeQuery( "SHOW TABLE STATUS" );
                while (rt.next()) {
                    resultMap.put( rt.getString( "Name" ), rt.getString( "Comment" ) );
                }
            }
        } finally {
            CustomFileUtils.closeStreams( rt, st );
        }
        
        return resultMap;
    }
    
    public static void main( String[] args ) throws SQLException {
        JdbcParam jp = new JdbcParam();
        jp.setUrl("" );
        jp.setDriver("" );
        List<Table> tables = findTables( jp, null, null, null );
        System.out.println(JSON.toJSONString(tables));
    }
}
