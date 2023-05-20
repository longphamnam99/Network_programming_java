import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Client <host>");
            return;
        }
        String addr = args[0];
        try {
            InetAddress address = InetAddress.getByName(addr);
            DatagramSocket socket = new DatagramSocket();
            String send = "menu";
            byte[] buffer = send.getBytes();
            // chuyen string sang byte
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9256);
            // gui cho server
            socket.send(packet);
            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String get = new String(packet.getData(), 0, packet.getLength());
            System.out.println(get);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Nhập lựa chọn: ");
                int function = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nhập chuỗi: ");
                String input = scanner.nextLine();
                String request = function + "\n" + input;
                buffer = request.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, address, 9256);
                socket.send(packet);
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                System.out.println(response);
            }
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}
