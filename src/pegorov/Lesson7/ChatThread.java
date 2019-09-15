package pegorov.Lesson7;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatThread {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public ChatThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;

        in = new DataInputStream(this.socket.getInputStream());
        out = new DataOutputStream(this.socket.getOutputStream());
    }

    public void start() throws IOException {
          /*
         Первым делом нужно спросить кто к нам подключается.
         */
        System.out.println("Клиент подключился требуется аутентификация");
        Thread thread = new Thread(()-> {
            try {
                System.out.println("Создаем поток чтения сообщений от клиента: " + name);

                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (authentication()) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        closeConnection();
    }

    private boolean authentication() throws IOException {
        String str = in.readUTF();

        if (str.equalsIgnoreCase("/end")) {
            System.out.println("Клиент отключился на этапе аутентификации");

            return false;
        }

        this.name = str;

        String msg = "";
        if (server.addClient(name, this)) {
            msg = "Добро пожаловать в чат! " + name;
            server.sendAll(msg);

            System.out.println("Клиент добавлен в список. Имя нового клиента: " + name);

            return true;
        } else {
            msg = "Клиент уже есть в чате, введите новый ник.";

            out.writeUTF(msg);
            System.out.println("Клиент не добавлен в список. Имя нового клиента: " + name + " сообщение: " + msg);

            return false;
        }
    }

    private void readMessage() throws IOException {

        while (server.isRunning()) {
            if (!socket.isConnected()) {
                System.out.println("Клиент: " + this.name + " отключился");
                closeConnection();
                break;
            }

            String str = in.readUTF();

            System.out.println("Клиент: " + name + " написал сообщение: " + str);

            if (str.equalsIgnoreCase("/end")) {
                closeConnection();
                break;
            }

            if (str.equalsIgnoreCase("/stopServer")) {
                System.out.println("Поступила команды выключения сервера!");

                server.stopServer();
                break;
            }

            String[] msg = str.split(" ", 3);
            if (msg[0].equalsIgnoreCase("/w")){
                String nameOut = msg[1];
                server.sendPrivateMsg(msg[2], name, nameOut);
            }else {
                server.sendAll(this.name, str);
            }
        }
    }

    public void closeConnection() {
        try {
            if (!server.closeClient(this.name)) return;
            server.sendAll("Клиент: " + this.name + " отключился.");

            sendMessage("/end");

            in.close();
            out.close();

            if (!socket.isClosed()) {
                socket.close();
            }

            System.out.println(this.name + " отключился");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) throws IOException {
        if (!socket.isConnected()) {
            closeConnection();
            return;
        }

        out.writeUTF(msg);
        out.flush();

        System.out.println("Отправлено сообщение клиенту: " + this.name + " " + msg);

    }
}
