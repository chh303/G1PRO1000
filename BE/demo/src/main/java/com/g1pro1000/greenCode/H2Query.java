/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.g1pro1000.greenCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author ch.h
 */
public class H2Query {
        public static void getUserTotal() {
        String url = "jdbc:h2:file:./data/database";
        String username = "sa";
        String password = "";
        
        int totalRows = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement()){
            
            String sql = "SELECT COUNT (*) AS total FROM USERS;";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                totalRows = resultSet.getInt("total");
            }
            System.out.println("Total rows in Users: " + totalRows);
            
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
