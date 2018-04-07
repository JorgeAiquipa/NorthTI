/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author udesarrollo2
 */
public class clsDatos {
    
    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public boolean InsertarMatricula() throws Exception {
        boolean respuesta = false;
        Connection cn;
        PreparedStatement pstm;
        try {
            cn = new ConexionDB().getConectar();
            pstm = cn.prepareCall("call SP_INSERT_MATRICULA();");
            respuesta = pstm.executeUpdate() > 0;
            System.out.print("\n*****Registro de Matrícula Exitosa*****\n");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.print("\n*****Error de Registro de Matrícula*****\n");
        }
        return respuesta;
    }
}
