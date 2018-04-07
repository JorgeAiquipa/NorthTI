/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import CapaDatos.clsDatos;

/**
 *
 * @author udesarrollo2
 */
public class clsNegocio {
    
    clsDatos objDatos = new clsDatos();
    
    public boolean InsertarMatricula() throws Exception {
        return objDatos.InsertarMatricula();
    }
}
