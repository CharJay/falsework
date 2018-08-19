package com.charjay.falsework.bean;

import java.sql.Types;

/**
 * 数据库表的列
 * 
 */
public class Column {
    
    /**
     * 名称
     */
    private String  sqlName;
    /**
     * 所属表名
     */
    private String  tableName;
    /**
     * 类型, SQL type from java.sql.Types
     */
    private int     dataType;
    /**
     * 类型名称
     */
    private String  typeName;
    /**
     * 长度
     */
    private Integer dataLen;
    /**
     * 精度
     */
    private Integer dataPrecision;
    /**
     * 小数长度
     */
    private Integer dataScale;
    /**
     * 是否可null
     */
    private boolean nullable;
    /**
     * 默认值
     */
    private String  defaultVal;
    /**
     * 列注释
     */
    private String  comment;
    
    /**
     * 是否是主键
     */
    private boolean pkcol;
    
    public Column() {
        super();
    }

    public Column( String sqlName, int dataType, Integer dataLen, Integer dataPrecision,
            Integer dataScale, String comment, boolean pkcol ) {
        super();
        this.sqlName = sqlName;
        this.dataType = dataType;
        this.dataLen = dataLen;
        this.dataPrecision = dataPrecision;
        this.dataScale = dataScale;
        this.comment = comment;
        this.pkcol = pkcol;
    }


    public String getJavaType() {
        switch (dataType) {
        case Types.NUMERIC:
        case Types.DECIMAL:
            if (dataScale == 0) {
                return dataPrecision > 9 ? java.lang.Long.class.getName() : java.lang.Integer.class.getName();
            } else {
                return java.lang.Double.class.getName();
            }
            
        case Types.TINYINT:
        case Types.SMALLINT:
        case Types.INTEGER:
            return java.lang.Integer.class.getName();
            
        case Types.FLOAT:
        case Types.DOUBLE:
            return java.lang.Double.class.getName();
            
        case Types.BOOLEAN:
            return java.lang.Boolean.class.getName();
            
        case Types.BIGINT:
            return java.lang.Long.class.getName();
            
        case Types.TIME:
        case Types.TIMESTAMP:
        case Types.DATE:
            return java.util.Date.class.getName();
            
        case Types.CHAR:
        case Types.NCHAR:
        case Types.VARCHAR:
        case Types.NVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
            return java.lang.String.class.getName();
            
        default:
            return java.lang.Object.class.getName();
        }
    }
    
    public String getSqlType() {
        switch (dataType) {
        case Types.NUMERIC:
        case Types.DECIMAL:
            if (dataScale == 0) {
                return dataPrecision > 9 ? "long" : "integer";
            } else {
                return "double";
            }
            
        case Types.TINYINT:
            return "tinyint";
        case Types.SMALLINT:
            return "short";
        case Types.INTEGER:
            return "integer";
            
        case Types.FLOAT:
            return "float";
        case Types.DOUBLE:
            return "double";
        case Types.BIT:
        case Types.BOOLEAN:
            return "boolean";
            
        case Types.BIGINT:
            return "long";
            
        case Types.TIME:
            return "time";
        case Types.TIMESTAMP:
            return "timestamp";
        case Types.DATE:
            return "date";

        case Types.CHAR:
            return "character";
        case Types.NCHAR:
        case Types.VARCHAR:
        case Types.NVARCHAR:
            return "string";
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.CLOB:
        case Types.NCLOB:
            return "text";
        case Types.BLOB:
            return "blob";
        default:
            return "string";
        }
    }
    
    public String getTypeCons(){
        switch (dataType) {
        case Types.NUMERIC:
            return "Types.NUMERIC";
        case Types.DECIMAL:
            return "Types.DECIMAL";
        case Types.TINYINT:
            return "Types.TINYINT";
        case Types.SMALLINT:
            return "Types.SMALLINT";
        case Types.INTEGER:
            return "Types.INTEGER";
        case Types.FLOAT:
            return "Types.FLOAT";
        case Types.DOUBLE:
            return "Types.DOUBLE";
        case Types.BIT:
            return "Types.BIT";
        case Types.BOOLEAN:
            return "Types.BOOLEAN";
        case Types.BIGINT:
            return "Types.BIGINT";
        case Types.TIME:
            return "Types.TIME";
        case Types.TIMESTAMP:
            return "Types.TIMESTAMP";
        case Types.DATE:
            return "Types.DATE";
        case Types.CHAR:
            return "Types.CHAR";
        case Types.NCHAR:
            return "Types.NCHAR";
        case Types.VARCHAR:
            return "Types.VARCHAR";
        case Types.NVARCHAR:
            return "Types.NVARCHAR";
        case Types.LONGVARCHAR:
            return "Types.LONGVARCHAR";
        case Types.LONGNVARCHAR:
            return "Types.LONGNVARCHAR";
        case Types.CLOB:
            return "Types.CLOB";
        case Types.NCLOB:
            return "Types.NCLOB";
        case Types.BLOB:
            return "Types.BLOB";
        default:
            return "Types.VARCHAR";
        }
    }
    
    public String getSqlName() {
        return sqlName;
    }
    
    public void setSqlName( String sqlName ) {
        this.sqlName = sqlName;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }
    
    public int getDataType() {
        return dataType;
    }
    
    public void setDataType( int dataType ) {
        this.dataType = dataType;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName( String typeName ) {
        this.typeName = typeName;
    }
    
    public Integer getDataLen() {
        return dataLen;
    }
    
    public void setDataLen( Integer dataLen ) {
        this.dataLen = dataLen;
    }
    
    public Integer getDataPrecision() {
        return dataPrecision;
    }
    
    public void setDataPrecision( Integer dataPrecision ) {
        this.dataPrecision = dataPrecision;
    }
    
    public Integer getDataScale() {
        return dataScale;
    }
    
    public void setDataScale( Integer dataScale ) {
        this.dataScale = dataScale;
    }
    
    public boolean isNullable() {
        return nullable;
    }
    
    public void setNullable( boolean nullable ) {
        this.nullable = nullable;
    }
    
    public String getDefaultVal() {
        return defaultVal;
    }
    
    public void setDefaultVal( String defaultVal ) {
        this.defaultVal = defaultVal;
    }
    
    public String getComment() {
        if(comment==null || comment.trim().length()==0) comment = null;
        return comment;
    }
    
    public void setComment( String comment ) {
        this.comment = comment;
    }
    
    public boolean isPkcol() {
        return pkcol;
    }
    
    public void setPkcol( boolean pkcol ) {
        this.pkcol = pkcol;
    }
    
}
