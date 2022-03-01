package com.edusoft.eduSSH;

import com.jcraft.jsch.*;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 22;

        Scanner scanner = new Scanner(System.in);

        System.out.printf("Introduzca un usuario del sistema:");
        String username = scanner.nextLine();

        System.out.printf("Introduzca la contraseña del usuario:");
        String password = scanner.nextLine();

        Session session = null;
        ChannelExec channel = null;

        try{

            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Conectando con la máquina remota...");
            session.connect();
            System.out.println("-------------------------------------");
            System.out.println("Conexión a la máquina correcta!!");

            boolean wantExit = true;
            System.out.printf("Introduce el nombre del archivo que desea ver:");
            String file = scanner.nextLine();


            while(wantExit) {

                System.out.println("Introduce 'exit' si quiere salir");
                System.out.printf("Introduce el nombre del archivo que desea ver:");
                file = scanner.nextLine();

                switch (file) {
                    case "exit":
                        System.out.println("Se cerrará el programa... ¡Hasta la próxima!");
                        wantExit = false;
                        break;
                    default:
                        channel = (ChannelExec) session.openChannel("exec");
                        channel.setCommand("cat /var/log/" + file);

                        ByteArrayOutputStream response = new ByteArrayOutputStream();
                        channel.setOutputStream(response);
                        channel.connect();

                        while(channel.isConnected()) {
                            Thread.sleep(100);
                        }

                        String responseString = new String(response.toByteArray());
                        if(responseString.isEmpty()) {
                            System.out.println("El archivo que ha especificado está vacío o no existe,se termina el programa.");
                            return;
                        }else{
                            System.out.println(responseString);
                        }
                        break;
                }
            }

        }catch(JSchException | InterruptedException e){
            if(e.getMessage().equals("Auth fail")){
                System.out.println("Se ha introducido un usuario o contraseña incorrecta, reinicie la aplicación");
            }else{
                e.printStackTrace();
            }
        }finally{
            if(session != null){
                session.disconnect();
            }
            if(channel != null){
                channel.disconnect();
            }
        }
    }
}
