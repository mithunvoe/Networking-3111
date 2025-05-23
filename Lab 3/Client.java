import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) throws Exception {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner sc = new Scanner(System.in);
        
        String name = "";
        
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        
        Socket s = new Socket("localhost", 1812);
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        DataInputStream in = new DataInputStream(s.getInputStream());

        System.out.println("sdfsdfsdf:".split(":").length);
        System.out.println("Joined to server at ip: " + "192.168.20.167" + " and port " + "1812 as "+name);
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
                    // System.out.println("\\033[31mServer:\\033[0m" );
                    System.out.print("\033[31mServer: \033[0m");
                    System.out.println(message); // This prints the escape code itself, without color


                    // System.out.println("");

                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }).start();
    }
}
