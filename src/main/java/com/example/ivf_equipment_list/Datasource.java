package com.example.ivf_equipment_list;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    public Connection connect = null;

    public Connection getConnect(){
        String devUserName = "devUser";
        String databasePassword = "Skittlesquad@@77";
        String databasename = "IVF_Equipment";
        String url = "jdbc:mySQL://localhost/" + databasename;

        try{
            connect = DriverManager.getConnection(url,devUserName,databasePassword);
            System.out.println("You are Connected!");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return connect;
    }

}

