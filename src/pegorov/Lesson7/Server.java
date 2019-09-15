package pegorov.Lesson7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private int PORT;
    private boolean isRunnable;
    private ServerSocket srv;
    private HashMap<String, ChatThread> listClient;

    public Server() {
        this.PORT = 8181;
        this.listClient = new HashMap<String, ChatThread>();
    }

    public Server(int port) {
        this.PORT = port;
        this.listClient = new HashMap<String, ChatThread>();
    }

    public synchronized boolean isRunning() {
        return isRunnable;
    }

    public void startServer() throws IOException {
        srv = new ServerSocket(this.PORT);
        this.isRunnable = true;

        System.out.println("Сервер запущен, ожидаем подключения...");

        while (isRunning()) {
            Socket socket = srv.accept();
            ChatThread thread = new ChatThread(this, socket);
            thread.start();
        }
    }

    public boolean closeClient(String name) {
        if (!listClient.isEmpty()) {
            listClient.remove(name);

            System.out.println("Клиент удален из чата! " + name);

            return true;
        }

        System.out.println("Клиента уже нет в чате! " + name);
        return false;
    }

    public boolean addClient(String name, ChatThread cTh){
        if (!listClient.containsKey(name)) {
            listClient.put(name, cTh);

            System.out.println("В чате новый клиент! " + name);
            return true;
        }

        System.out.println("Клиент попытался подключиться под уже заведенным ником! " + name);

        return false;
    }

    public void stopServer() throws IOException {
        this.isRunnable = false;

        if (!listClient.isEmpty()) {
            for (String name : listClient.keySet()) {
                ChatThread map = listClient.get(name);
                map.closeConnection();

                System.out.println("Клиент: " + name + " был отключен сервером.");
            }
            listClient.clear();
        }

        if (!srv.isClosed()) {
            srv.close();
        }

        System.out.println("Сервер остановлен");
    }

    public void sendAll(String nameClient, String msg) throws IOException {
        if (!listClient.isEmpty()) {
            for (ChatThread map : listClient.values()) {
                map.sendMessage("" + nameClient + ": " + msg);
            }
       }
    }

    public void sendAll(String msg) throws IOException {
        if (!listClient.isEmpty()) {
            for (ChatThread map : listClient.values()) {
                map.sendMessage(msg);

                System.out.println("Сообщение сервера: " + msg);
            }
        }
    }

    public void sendPrivateMsg(String str, String nameIn, String nameOut) throws IOException {
        ChatThread clientIn = listClient.get(nameIn);
        ChatThread clientOut = listClient.get(nameOut);

        if (clientOut == null){
            clientIn.sendMessage("Сообщение сервера: нет такого пользователя [" + nameOut + "]");
            System.out.println("Нет такого клиента! " + nameOut);
        }else{
            clientIn.sendMessage("[" + nameIn + " -> " + nameOut + "]: " + str);
            clientOut.sendMessage("[" + nameIn + " -> " + nameOut + "]: " + str);
            System.out.println("Отправлено приватное сообщение: [" + nameIn + " -> " + nameOut + "] " + str);
        }
    }
}
