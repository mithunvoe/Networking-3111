
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    public static Vector<ClientThread> clientThreads = new Vector<>();

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1812);
        System.out.println("Created Server Socket at port '1812'");
        while (true) {
            Socket s = ss.accept();

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            ClientThread thread = new ClientThread(s, in, out);
            clientThreads.add(thread);

            thread.start();
        }

    }
}

class ClientThread extends Thread {
    public Socket s;
    DataInputStream in;
    DataOutputStream out;
    String name;

    public ClientThread(Socket s, DataInputStream in, DataOutputStream out) {
        this.s = s;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        super.run();
        try {
            name = in.readUTF();
            System.out.println(name + " joined the server!");
            while (true) {
                String str = in.readUTF();
                if (str.equals("quit")) {
                    break;
                }
                if (str.strip().equals(""))
                    continue;
                System.out.println(name + ": " + str);
                for (var clientThread : Server.clientThreads) {
                    if (clientThread.s.getPort() != s.getPort()) {
                        clientThread.out.writeUTF(name + ": " + str);
                    }
                }
            }
            Server.clientThreads.remove(this);
            System.out.println(name + " cholegelo amader chere :\"\"(");

        } catch (Exception e) {
            System.out.println("Client thread e jahmela");
        }
    }

}