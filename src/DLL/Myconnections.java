/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author Md. Mazharul Islam
 */
public abstract class Myconnections {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/rentcardatabase?autoReconnect=true&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    protected Connection connection = null;
    protected PreparedStatement statement = null;
    protected ResultSet resultSet = null;
    
    public Myconnections(){


        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error occured (in myConnection ) : " + e.getMessage());
        }

    }

    public void disconnectDB() throws SQLException {
        
            resultSet.close();
            statement.close();
            connection.close();
        
    }

    public void setConnection() throws SQLException {
       
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, PASSWORD);
            }
       
    }

    
}
