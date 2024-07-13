package com.rtemi.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgres {
    public Connection connection;

    public ConnectionPostgres(String url, String user, String password) {
        try{
            this.connection = DriverManager.getConnection(url,user,password);
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
