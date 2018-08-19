package com.charjay.falsework.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnector {
    
    private ThreadLocal<Connection> conTL = new ThreadLocal<>();
    
    private String                  url;
    private String                  user;
    private String                  password;
    
    public JdbcConnector( String dirverClass, String url, String user, String password ) {
        try {
            Class.forName( dirverClass );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException( e.getMessage(), e );
        }
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public Connection getLocalCon( Properties extraProps ) throws SQLException {
        Connection con = conTL.get();
        if (con == null || con.isClosed()) {
            DriverManager.setLoginTimeout( 3 );
            if (extraProps != null) {
                extraProps.put( "user", this.user );
                extraProps.put( "password", this.password );
                con = DriverManager.getConnection( url, extraProps );
            } else {
                con = DriverManager.getConnection( url, user, password );
            }
            conTL.set( con );
        }
        
        return con;
        
    }
    
    public void closeLocalCon() {
        Connection con = conTL.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conTL.remove();
        }
    }
    
}
