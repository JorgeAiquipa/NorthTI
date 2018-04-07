/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author udesarrollo2
 */
public class ConexionDB {
    
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public Connection getConectar() {
        Connection cn = null;
        try {
           Class.forName("com.mysql.jdbc.Driver");
           cn = DriverManager.getConnection("jdbc:mysql://50.87.151.170/xtremex_sistema_integradosb", "xtremex_upc", "$LoSBR@VoS$");
           //cn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/xtremex_sistema_integradosb", "root", "mysql");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }
}
