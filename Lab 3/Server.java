
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    public static Vector<ClientHandlerThread> clientThreads = new Vector<>();
    public static int selectedIndex = 0;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1812);
        System.out.println("Created Server Socket at port '1812'");
        Scanner sc = new Scanner(System.in);
        new Thread(() -> {
            try {
                while (true) {
                    Socket s = ss.accept();

                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    ClientHandlerThread thread = new ClientHandlerThread(s, in, out, clientThreads.size());
                    clientThreads.add(thread);

                    thread.start();
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Couldn't create Client Handler Thread");
            }

        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("0. Exit");
                    System.out.println("1. View available users: ");
                    String read = sc.nextLine();
                    if (read.equals("0")) {
                        System.out.println("Quitting...");
                        break;
                    } else if (read.equals("1")) {
                        for (int i = 0; i < clientThreads.size(); i++) {
                            System.out.println(i + 1 + ". " + clientThreads.get(i).name);
                        }
                        try {
                            read = sc.nextLine();
                            selectedIndex = Integer.parseInt(read);
                            var temp = clientThreads.get(Integer.parseInt(read) - 1);
                            System.out.println("Chatting With " + temp.name);
                            System.out.println("Enter 'quit' to exit the application.");

                            new Thread(() -> {

                                try {
                                    while (true) {
                                        String str = temp.in.readUTF();
                                        if (str.equals("quit")) {
                                            break;
                                        }
                                        if (str.strip().equals(""))
                                            continue;
                                        temp.messages.add(str);
                                        System.out.println(temp.name+": "+str);
                                    }
                                } catch (Exception e) {
                                    System.out.println("boink");
                                }

                            }).start();

                            try {
                                while (true) {
                                    String str = sc.nextLine();
                                    if (str.equals("quit")) {
                                        break;
                                    }
                                    if (str.strip().equals(""))
                                        continue;

                                    temp.out.writeUTF(str);
                                }
                            } catch (Exception e) {
                                System.out.println("Couldn't send to client");

                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println("Not a valid integer");
                        }
                    }

                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }).start();

    }
}

class ClientHandlerThread extends Thread {
    public Socket s;
    DataInputStream in;
    DataOutputStream out;
    String name;
    int index;
    Vector<String> messages = new Vector();

    public ClientHandlerThread(Socket s, DataInputStream in, DataOutputStream out, int index) {
        this.s = s;
        this.in = in;
        this.out = out;
        this.index = index;
    }

    @Override
    public void run() {
        super.run();
        try {
            name = in.readUTF();
            System.out.println(name + " joined the server!");

        } catch (Exception e) {
            System.out.println("Client thread e jahmela");
        }
    }

}