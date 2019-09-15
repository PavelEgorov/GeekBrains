package pegorov.Lesson7;

import java.io.IOException;

public class Lesson7 {
    public static void main(String[] args) {
        Server srv = new Server();
        try {
            srv.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
