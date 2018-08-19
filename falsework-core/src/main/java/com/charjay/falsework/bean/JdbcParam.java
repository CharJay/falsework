package com.charjay.falsework.bean;

public class JdbcParam {
    
    /**
     * 数据库驱动类全限定名
     */
    private String driver;
    /**
     * 数据库URL
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * SCHEMA
     */
    private String schema;
    
    public String getDriver() {
        return driver;
    }
    
    public void setDriver( String driver ) {
        this.driver = driver;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl( String url ) {
        this.url = url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername( String username ) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword( String password ) {
        this.password = password;
    }
    
    public String getSchema() {
        return schema;
    }

    public void setSchema( String schema ) {
        this.schema = schema;
    }
    
}
