import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) throws Exception {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("sdfsdfsdf:".split(":").length);
        Socket s = new Socket("192.168.20.167", 1812);
        System.out.println("Joined to server at ip: " + "192.168.20.167" + " and port " + "1812");
        Scanner sc = new Scanner(System.in);

        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        DataInputStream in = new DataInputStream(s.getInputStream());
        String name = "";

        System.out.print("Enter your name: ");
        name = sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Welcome " + name + "!");
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
                    System.out.println("Server: " + message);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }).start();
    }
}
