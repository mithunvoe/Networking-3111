import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("sdfsdfsdf:".split(":").length);
        Socket s = new Socket("192.168.0.108", 1812);
        System.out.println("Joined to server at ip: " + "192.168.0.108" + " and port " + "1812");
        Scanner sc = new Scanner(System.in);

        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        DataInputStream in = new DataInputStream(s.getInputStream());
        String name = "";

        System.out.print("Enter your name: ");
        name = sc.nextLine();

        out.writeUTF(name);
        AtomicBoolean running = new AtomicBoolean(true);
        new Thread(() -> {
            try {
                while (true) {
                    String message = sc.nextLine();
                    out.writeUTF(message);
                    if (message.equals("quit")) {
                        running.set(false);
                        break;
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }).start();

        new Thread(() -> {
            try {
                while (running.get()) {
                    String message = in.readUTF();
                    System.out.println(message);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }).start();
    }
}
