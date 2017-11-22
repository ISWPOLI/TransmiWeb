/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Miguel
 */
public class Conexion {
    private Connection conexion=null;
    private String servidor="http://localhost";
    private String database="transmiweb";
    private String usuario="root";
    private String password="";
    
    public Conexion(){
        
        try {
 
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:mysql://localhost/transmiweb";
            conexion=DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion a Base de Datos "+url+" . . . . .Ok");
 
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
 
    public Connection getConexion(){
        return conexion;
    }
 
    public Connection cerrarConexion(){
        try {
            conexion.close();
             System.out.println("Cerrando conexion");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        conexion=null;
        return conexion;
    }
}
