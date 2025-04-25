import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("172.20.10.2", 1);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        Scanner sc = new Scanner(System.in);

        while (true) {
            String temp = sc.nextLine();

            if (temp.equals("quit"))
                break;

            else if (temp.equals("list")) {
                out.writeUTF(temp);
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());

                System.out.println("Give file name: ");

                String fileName = sc.nextLine();

                out.writeUTF(fileName);

                String str = in.readUTF();
                if (str.equals("NOT_FOUND")) {
                    System.out.println("File not found with this name!");
                    s.close();
                    return;
                }
                int sz = Integer.parseInt(str);
                byte[] ara = new byte[sz];
                ara = in.readAllBytes();
                // for (int i = 0; i < sz; i++) {
                //     ara[i] = in.readByte();
                // }

                try (FileOutputStream os = new FileOutputStream(fileName)) {
                    os.write(Arrays.copyOfRange(ara, 0, sz));
                    os.close();
                }

            }
            // break;
        }

    }
}
