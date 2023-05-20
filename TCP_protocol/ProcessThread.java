import java.io.*;
import java.net.*;

class ProcessThread extends Thread {
    private Socket socket;

    public ProcessThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Tạo luồng đọc dữ liệu từ client
            DataInputStream in = new DataInputStream(socket.getInputStream());
            // Tạo luồng ghi dữ liệu tới client
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Gửi danh sách các chức năng tới client
            String menu = "1. Chuyển các chữ cái đầu tiên của các từ trong chuỗi thành chữ in hoa.\n2. Đảo ngược chuỗi.\n";
            out.writeUTF(menu);

            // Nhận yêu cầu từ client và xử lý
            while (true) {
                String choice = in.readUTF();

                if (choice.equals("exit")) {
                    break;
                }
                String result = "";
                String str = "";
                switch (choice) {
                    case "1":
                        out.writeUTF("Nhập chuỗi:");
                        out.flush();
                        str = in.readUTF();
                        result = capitalizeWords(str);
                        out.writeUTF(result);
                        out.flush();
                        break;
                    case "2":
                        out.writeUTF("Nhập chuỗi:");
                        out.flush();
                        str = in.readUTF();
                        result = reverseString(str);
                        out.writeUTF(result);
                        out.flush();
                        break;
                    default:
                        result = "Lựa chọn không phù hợp.";
                        break;
                }

                // Gửi kết quả xử lý về cho client
                out.writeUTF(result);
            }

            // Đóng kết nối tới client
            socket.close();
            System.out.println("Client disconnected: " + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Chuyển các chữ cái đầu tiên của các từ trong chuỗi thành chữ in hoa
    private static String capitalizeWords(String s) {
        String[] words = s.split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1).toLowerCase());
            }

            sb.append(" ");
        }

        return sb.toString().trim();
    }

    // Đảo ngược chuỗi
    private static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}