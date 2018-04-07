/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import CapaNegocio.clsNegocio;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import util.PruebaArchivosTXT;

/**
 *
 * @author udesarrollo2
 */
public class GenerarMatricula extends JFrame implements ActionListener {
    
    int frameNumber = -1;
    int delay;
    boolean frozen = false;
    JLabel label;
    JLabel numero;
    Timer timer;
    boolean respuesta;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    GenerarMatricula(int fps, String windowTitle) {
        delay = (fps > 0) ? (1000 / fps) : 100;
        
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                stopAnimation();
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
                startAnimation();
            }
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }  
        });
        
        @SuppressWarnings("OverridableMethodCallInConstructor")
        Container contentPane = getContentPane();
        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (frozen) {
                    frozen = false;
                    startAnimation();
                } 
                else {
                    frozen = true;
                    stopAnimation();
                }
            }
        });
        
        label = new JLabel(" Frame       ", JLabel.CENTER);
        contentPane.add(label, BorderLayout.CENTER);
        
    }

    public GenerarMatricula() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void startAnimation() {
        if (frozen) {
            //Do nothing.  The user has requested that we
            //stop changing the image.
        } else {
            //Start (or restart) animating!
            timer.start();
        }
    }
    
    public void stopAnimation() {
        //Stop the animating thread.
        timer.stop();
    }
    
    @Override
    @SuppressWarnings("DeadBranch")
    public void actionPerformed(ActionEvent e) {
        frameNumber ++;
        label.setText("            Tiempo : " + frameNumber+"            ");
        if (String.valueOf(frameNumber).equals("3")) {
            frameNumber = 0;
            clsNegocio objNegocio = new clsNegocio();
            try {
                PruebaArchivosTXT x = new PruebaArchivosTXT();
                x.LeerContenidoArchivos();
                
                respuesta = objNegocio.InsertarMatricula();
                if (respuesta = true) {
                    timer.stop();
                    //JOptionPane.showMessageDialog(null, "Registro Exitoso!!!");
                    System.exit(0);
                }
                else{
                    timer.stop();
                    //JOptionPane.showMessageDialog(null, "Error al registrar la matricula!!!");
                    System.exit(0);
                }
                
            } 
            catch (Exception ex) {
                Logger.getLogger(GenerarMatricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public static void main(String[] args) {
        GenerarMatricula animator;
        int fps = 1;
        
        if (args.length > 0) {
            try {
                fps = Integer.parseInt(args[0]);
            } 
            catch (Exception e) {
            }
        }
        animator = new GenerarMatricula(fps, "Registro Matricula");
        animator.pack();
        animator.setVisible(true);
        animator.startAnimation();
    }

}
