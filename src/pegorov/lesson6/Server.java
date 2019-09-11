package pegorov.lesson6;

import pegorov.lesson4.game.Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    private static boolean isRunning;

    public static void main(String[] args) {
        try {
            startServer();
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startServer() throws IOException {
        socket = null;
        serverSocket = new ServerSocket(8181);
        System.out.println("Сервер запущен, ожидаем подключения...");
        socket = serverSocket.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        isRunning = true;

        Thread trIn = new Thread(() -> {
            System.out.println("Клиент подключился");
            try {
                while (isRunning) {
                    if (!socket.isConnected()) {
                        System.out.println("Client is disconnected");
                        isRunning = false;
                        break;
                    }

                    String str = in.readUTF();

                    if (str.equalsIgnoreCase("/end")) {
                        System.out.println("Клиент отключился");
                        isRunning = false;
                        break;
                    }

                    System.out.println("Клиент сказал:" + str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread trOut = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            try {
                while (isRunning) {
                    if (!socket.isConnected()) {
                        isRunning = false;
                        break;
                    }

                    String str = sc.nextLine();
                    out.writeUTF(str);

                    if (str.equals("/end")) {
                        isRunning = false;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        trIn.start();
        trOut.start();

        try {
            trIn.join();
            trOut.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() throws IOException {
        in.close();
        out.close();

        if (!socket.isClosed()) {
            socket.close();
        }
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}
