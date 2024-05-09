import java.util.Scanner;

public class AppFtp {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Inserte el nombre del servidor: ");
        String nameFtp=sc.nextLine();
        ClienteFtp cliente=new ClienteFtp();
        boolean respServ;
        do{
            respServ=cliente.conectarServidor(nameFtp);
            if(respServ) System.out.println("Conexion satisfatoria");
            else {
                System.out.println("Se ha producido un error\nInserte el nombre del servidor:");
                nameFtp = sc.nextLine();
            }
        }while(!respServ);
        boolean logOk;
        do{
            System.out.println("Inserte su nombre: ");
            String nombre=sc.nextLine();
            System.out.println("Inserte su contraseña: ");
            String contrasenia=sc.nextLine();
            if(!(logOk= cliente.login(nombre,contrasenia))) System.out.println("Nombre o contraseña erroneo");
        }while(!logOk);

        String opcion;
        do{
            cliente.mostrarDirectorios();
            System.out.println("Inserte el directorio al que quiere navegar o archivo a descargar:");
            opcion=sc.nextLine();
            if(existeArchivo(cliente.listaArchivosString(),opcion)){
                int cod=cliente.isDirectorioOrFile(opcion);
                if (cod==0){
                    System.out.println("Es un directorio");
                    cliente.navegarDirectorio(opcion);
                }else{
                    System.out.println("Es un archivo");
                    cliente.descargarArchivo(opcion);
                }
            }else{
                System.out.println("No existe");
            }
        }while(true);

    }
    private static boolean existeArchivo(String[]archivos,String archivo){
        for (String s:archivos){
            if (s.equals(archivo)) return true;
        }
        return false;
    }

}
