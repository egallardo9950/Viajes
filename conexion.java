/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajes.interfaces;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ESTUDIANTE
 */
public class conexion {
    Connection conect=null;
    public Connection conectar()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conect=DriverManager.getConnection("jdbc:mysql://localhost/viajes","root","");
            //JOptionPane.showMessageDialog(null, "Ok coorecto");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Sin conexión intentala mas tarde, ponte en contacto con el administrador");
        } 
        return conect;
        
    }
    
}
