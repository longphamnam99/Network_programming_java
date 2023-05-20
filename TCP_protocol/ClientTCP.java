import java.io.*;
import java.net.*;

public class ClientTCP {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Client <host> <port>");
            return;
        }
    
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(host, port);
        System.out.println("Đã kết nối tới server!");

        System.out.println();

        // Tạo luồng ghi dữ liệu tới server
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // Nhận danh sách các chức năng từ server
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String menu = in.readUTF();
        System.out.println("Menu:\n" + menu);

        // Chọn chức năng và gửi yêu cầu tới server
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice = "";
        while (!choice.equals("exit")) {
            System.out.print("Nhập lựa chọn: ");
            choice = br.readLine();

            // Gửi yêu cầu tới server
            out.writeUTF(choice);

            // Tạm ngưng chờ request
            out.flush();

            // Nhận kết quả từ server và in ra màn hình
            String response = in.readUTF();

            if (choice.equals("exit")) {
                break;
            }

            if (response.equals("Nhập chuỗi:")) {
                System.out.print(response + " ");
                String str = br.readLine();
                out.writeUTF(str);
                String result = in.readUTF();

                System.out.println("Kết quả: " + result);

                break;
            } else {
                String result = in.readUTF();
                System.out.println(result);
                break;
            }
        }

        // Ngắt kết nối tới server
        socket.close();
        System.out.println("Không thể kết nối tới server");
    }
}
