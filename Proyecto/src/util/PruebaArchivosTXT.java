/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.StringTokenizer;

/**
 *
 * @author udesarrollo2
 */
public class PruebaArchivosTXT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PruebaArchivosTXT x = new PruebaArchivosTXT();
        x.LeerContenidoArchivos();
        
    }
    
    public void LeerArchivos() {
        // Aquí la carpeta que queremos explorar
        String path = "D:/Archivos TXT/";
        
        String nomArchivo;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        for (File listOfFile : listOfFiles) {
            nomArchivo = listOfFile.getName();
            if (nomArchivo.endsWith(".txt") || nomArchivo.endsWith(".TXT")) {
                System.out.println(nomArchivo);
            }
        }
        System.out.println("Fin");
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void LeerContenidoArchivos() {
        // Aquí la carpeta que queremos explorar
        //String path = "D:/Archivos TXT/";
        String path = "C:/Users/FEBAN/Documents/Archivos TXT/";
        //String destino = "D:/Archivos Pagados TXT/";
        
        String nomArchivo;
        File archivos;
        String[] arregloTXT = new String[17];
        
        File src;
        File dst;
        
        String valor = "";
        String valorArchivo = "";
        
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivos = new File(path);
            File[] listOfFiles = archivos.listFiles();
            
            for (File listOfFile : listOfFiles) {
                nomArchivo = listOfFile.getName();
                FileReader fr = new FileReader(path + nomArchivo);
                BufferedReader br = new BufferedReader(fr);
                
                // Lectura del fichero
                System.out.println("\nLeyendo el contendio del archivo: " + nomArchivo);
                String linea;
                if((linea = br.readLine()) != null) {
                    System.out.println(linea);
                    
                    StringTokenizer st = new StringTokenizer(linea,"|");
                    int numeroTokens = st.countTokens();
                    
                    for(int i = 0; i < numeroTokens; i++) {
                        arregloTXT[i] = st.nextToken();
                        System.out.println("Recorriendo texto...");
                        System.out.println("Validando texto...");
                        //System.out.println(arregloTXT[i2]);
                        
                        if(arregloTXT[i].equals("01")) {
                            System.out.println("******PAGADO*****");
                            valor = "01";
                            valorArchivo = nomArchivo;
                            break;
                        }
                        else {
                            System.out.println("******NO PAGADO*****\n");
                            valor = "00";
                            valorArchivo = "";
                            break;
                        }
                    }
                }
                if(valor.equals("01")) {
                    fr.close();
                    br.close();
                    
                    String origen = path + valorArchivo;
                    //String destino = "D:/Archivos Pagados TXT/";
                    String destino = "C:/Users/FEBAN/Documents/Archivos Pagados TXT/";
                    src = new File(origen);
                    dst = new File(destino);
                    //Cortar y Pegar Archivo
                    CortarPegarArchivo(src, dst);
                }
                else if(valor.equals("00")) {
                    fr.close();
                    br.close();
                }
            }
            System.out.println("\nProceso Finalizado");
            System.out.println("Archivos Procesados: " + listOfFiles.length);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public void MoverArchivo(String sourceFile, String destinationFile) {
        System.out.println("Moviendo Archivo...");
        System.out.println("Desde: " + sourceFile);
        System.out.println("Hacia: " + destinationFile);
        
        try {
            File inFile = new File(sourceFile);
            File outFile = new File(destinationFile);
            
            InputStream in = new FileInputStream(inFile);
            OutputStream out = new FileOutputStream(outFile);
            
            int c;
            while( (c = in.read() ) != -1) {
                out.write(c);
            }
            in.close();
            out.close();
        } 
        catch (Exception e) {
            System.err.println("Hubo un error de entrada/salida!!!");
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void EliminarArchivo(String rutaArchivo) {
        try {
            //rutaArchivo = unidadBase + Variables.solicitud + Variables.parametro + nomArchivo;
            File ruta;
            ruta = new File(rutaArchivo);
            if(ruta.delete()) {
                System.out.print("Eliminacion exitosa\n");
            }
            else {
                System.out.print("Error al eliminar archivo\n");
            }
	} 
        catch (Exception e) {
            e.printStackTrace();
        }
     }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public void EscribirArchivos() {
        // Aquí la carpeta que queremos explorar
        String path = "D:/Archivos TXT/";
        
        String nomArchivo;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        
        FileWriter fichero;
        PrintWriter pw;
        
        for (File listOfFile : listOfFiles) {
            nomArchivo = listOfFile.getName();
            if (nomArchivo.endsWith(".txt") || nomArchivo.endsWith(".TXT")) {
                System.out.println(nomArchivo);
                
                try {
                    fichero = new FileWriter(path + nomArchivo);
                    pw = new PrintWriter(fichero);
                    
                    System.out.println("Escribiendo en el archivo" + nomArchivo);
                    for (int i2 = 0; i2 < 10; i2++) {
                        pw.println("Linea " + i2);
                        System.out.println(i2);
                    }
                } 
                catch (IOException ex) {
                    Logger.getLogger(PruebaArchivosTXT.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void CortarPegarArchivo(File src, File dst) throws IOException {
        boolean success = src.renameTo(new File(dst, src.getName()));
        
        if (!success) {
            System.out.println("Error en mover archivo: " + src.getName() + "\n");
        }
        else {
            System.out.println("Exito en mover archivo: " + src.getName() + "\n");
        }
    }
}
