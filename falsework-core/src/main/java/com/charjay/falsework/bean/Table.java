package com.charjay.falsework.bean;

import java.util.LinkedHashMap;

/**
 * 数据库表
 * 
 * @author
 */
public class Table {
    
    /**
     * 表名
     */
    private String                        sqlName;
    /**
     * 表注释
     */
    private String                        comment;
    /**
     * 主键字段
     */
    private String                        pkColName;
    /**
     * 序列名称
     */
    private String                        sequenceName;
    /**
     * 表的字段集。字段名作为key，LinkedHashMap保持put顺序。
     */
    private LinkedHashMap<String, Column> columnsMap;
    
    public Table() {
        super();
    }
    
    public Table( String sqlName, String comment ) {
        super();
        this.sqlName = sqlName;
        this.comment = comment;
        this.pkColName = "SEQ_ID";
    }

    public Table( String sqlName, String comment, String pkColName ) {
        super();
        this.sqlName = sqlName;
        this.comment = comment;
        this.pkColName = pkColName;
    }
    
    public Table addCol(Column c){
        if(columnsMap == null)
            columnsMap = new LinkedHashMap<>();
        columnsMap.put( c.getSqlName().toUpperCase(), c );
        return this;
    }

    public String getSqlName() {
        return sqlName;
    }
    
    public void setSqlName( String sqlName ) {
        this.sqlName = sqlName;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment( String comment ) {
        this.comment = comment;
    }
    
    public String getPkColName() {
        return pkColName;
    }
    
    public void setPkColName( String pkColName ) {
        this.pkColName = pkColName;
    }
    
    public String getSequenceName() {
        return sequenceName;
    }
    
    public void setSequenceName( String sequenceName ) {
        this.sequenceName = sequenceName;
    }
    
    public LinkedHashMap<String, Column> getColumnsMap() {
        return columnsMap;
    }
    
    public void setColumnsMap( LinkedHashMap<String, Column> columnsMap ) {
        this.columnsMap = columnsMap;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sqlName == null) ? 0 : sqlName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Table other = (Table) obj;
        if (sqlName == null) {
            if (other.sqlName != null)
                return false;
        } else if (!sqlName.equals(other.sqlName))
            return false;
        return true;
    }
    
}
