import java.io.*;
import java.net.*;

class Server {
    public static void main(String[] args) {
        try {
            // dung server
            DatagramSocket socket = new DatagramSocket(9256);
            byte[] buffer = new byte[1024];
            System.out.println("Server đã được tạo");
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                // cho goi tin duoc goi toi
                socket.receive(packet);

                // lay goi tin, doi byte ra string
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.equals("exit")) {
                    break;
                }

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                System.out.println("Client da ket noi: " + address + ":" + port);

                ThreadProcess thread = new ThreadProcess(socket, address, port, received);
                thread.start();
            }

        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}