import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClienteFtp {
    private FTPClient cliente;
    public ClienteFtp(){
        cliente=new FTPClient();
        cliente.setControlEncoding("UTF-8");
    }
    public boolean conectarServidor(String nameFTP)  {
        try {
            System.out.println("Intentando conectar al servidor FTP "+nameFTP);
            cliente.connect(nameFTP);//conectamos al servidor
            //sino se produce excepcion recogemos el codigo de respuesta del servidor
            int codResp=cliente.getReplyCode();
            //comprobamos si la respuesta ha sido positiva o no
            if(!FTPReply.isPositiveCompletion(codResp)){
                cliente.disconnect();
                return false;
            }else{
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
    public boolean login(String name,String password){
        try {
            return cliente.login(name,password);
        } catch (IOException e) {
            return false;
        }
    }
    public void mostrarDirectorios(){
        try {
            //muestra la ruta del contenido actual
            System.out.println("Directorio actual: "+cliente.printWorkingDirectory());

            //recuperamos todos los archivos que contiene la ruta actual y los mostramos
           FTPFile[] ftpFile= cliente.listFiles();
           for(FTPFile file:ftpFile){
               System.out.println(file.getName());
           }

        } catch (IOException e) {

        }
    }
    public boolean tipoArchivo(String directorio){
        try {
            return cliente.changeWorkingDirectory(directorio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] listaArchivosString(){
        try {
            return cliente.listNames();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public FTPFile[] listaArchivos(){
        try {
            return cliente.listFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int isDirectorioOrFile(String nombre){
        FTPFile[] ftpFile;
        try {
            ftpFile = cliente.listFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(FTPFile file:ftpFile){
            if(file.getName().equals(nombre)){
                if(file.isDirectory()) return 0;
                else return 1;
            }
        }
        return -1;
    }
    public void navegarDirectorio(String directorio){
        try {
            cliente.changeWorkingDirectory(directorio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean descargarArchivo(String fichero){
        //para descargar un archivo nos un stream y la coenxion del cliente
       try( BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(fichero))){
           boolean descargaOk=cliente.retrieveFile(fichero,bos);
           if(descargaOk) return true;
           return false;
       }catch(IOException e){
           return false;
       }
    }
}
