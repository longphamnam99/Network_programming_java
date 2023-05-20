import java.io.*;
import java.net.*;

public class ServerTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server đã chạy và đang chờ kết nối từ client...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client kết nối: " + socket);

            ProcessThread processThread = new ProcessThread(socket);
            processThread.start();
        }
    }
}

