package pegorov.Lesson7;

import java.io.IOException;

public class Lesson7Client {
    public static void main(String[] args){
        Client client = null;
        client = new Client();

        ChatInterface chatInterface = new ChatInterface();
        chatInterface.initField(client);
        client.startClient(chatInterface);
    }
}
