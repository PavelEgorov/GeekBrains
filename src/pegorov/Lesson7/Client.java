package pegorov.Lesson7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final static String SERVER_ADDR = "localhost";
    private final static int SERVER_PORT = 8181;

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    private static boolean isRunning;
    private ChatInterface clientInterface;

    public void startClient(ChatInterface inr) {
        this.clientInterface = inr;

        try {
            connectToServer(inr);
            closeConnection();
        } catch (IOException e) {
            clientInterface.updateDialog("Не удалось подключиться к серверу");
            e.printStackTrace();
        }
    }

    private static void connectToServer(ChatInterface inr) throws IOException {

        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        isRunning = true;

        Thread tr1in = new Thread(() -> {
            try {
                while (isRunning) {
                    if (!socket.isConnected()) {
                        System.out.println("Server is disconnected");
                        isRunning = false;
                        break;
                    }

                    String strFromServer = in.readUTF();

                    if (strFromServer.equalsIgnoreCase("/end")) {
                        System.out.println("Отключились от сервера");
                        isRunning = false;
                        break;
                    }

                    inr.updateDialog(strFromServer);
                    System.out.println(strFromServer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        /*
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
                    out.flush();

                    if (str.equalsIgnoreCase("/end")) {
                        isRunning = false;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        */
        tr1in.start();
        //trOut.start();

        try {
            tr1in.join();
            //trOut.join();
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
    }

    public void sendMessage(String text) {
        try {
            if (!isRunning) {
                return;
            }

            if (!socket.isConnected()) {
                isRunning = false;
                return;
            }

            if (socket == null) {
                isRunning = false;
                return;
            }

            out.writeUTF(text);
            out.flush();

            if (text.equalsIgnoreCase("/end")) {
                isRunning = false;
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
