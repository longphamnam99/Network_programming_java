import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadProcess extends Thread {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private String send;

    ThreadProcess(DatagramSocket socket, InetAddress address, int port, String send) {
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.send = send;
    }

    @Override
    public void run() {
        super.run();
        String response = processRequest(send);
        byte[] buffer = response.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        if (request.equals("menu")) {
            return "1. Đảo ngược chuỗi đồng thời in hoa ký tự đầu của mỗi từ\n2. Tính tổng chuỗi các nguyên\n3. Thoát";
        }
        Scanner scanner = new Scanner(request);
        int choice = scanner.nextInt();
        scanner.nextLine();
        String input = scanner.nextLine();
        switch (choice) {
            case 1:
                return CapitalizeAndReverseString(input);
            case 2:
                return count(input);
            default:
                return "Sai lựa chọn".toString();
        }
    }

    public static String CapitalizeAndReverseString(String str) {
        String string;
        string = new StringBuilder(str).reverse().toString();

        String[] words = string.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1).toLowerCase());
            }

            sb.append(" ");
        }

        string = sb.toString().trim();
        return string;
    }

    public static String count(String numbers) {
        String[] arrStr = numbers.split(" "); // Phân tách chuỗi bằng khoảng trắng để lấy các phần tử riêng biệt
        int[] arrInt = new int[arrStr.length]; // Tạo mảng số nguyên với độ dài bằng số phần tử của mảng chuỗi

        // Chuyển đổi các phần tử trong mảng chuỗi thành số nguyên và lưu vào mảng số
        for (int i = 0; i < arrStr.length; i++) {
            arrInt[i] = Integer.parseInt(arrStr[i]);
        }

        int count = 0;
        // In ra mảng số đã chuyển đổi
        for (int i = 0; i < arrInt.length; i++) {
            count += arrInt[i];
        }

        return Integer.toString(count);
    }
}
