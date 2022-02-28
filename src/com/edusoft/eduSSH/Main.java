package com.edusoft.eduSSH;
import com.jcraft.jsch.*;

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
            session.connect();

            System.out.println("Conexión a la máquina correcta!!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
