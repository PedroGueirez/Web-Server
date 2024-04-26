import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int port = 8888;
    private Socket clientSocket;
    private ServerSocket bindSocket;
    private BufferedReader inputBufferedReader;
    private PrintWriter out;
    private BufferedReader in;
    private ExecutorService service;
    private ArrayList<ServerHandle> mylist = new ArrayList<>();

    public Server() {
        service = Executors.newFixedThreadPool(5);
    }
    public static void main(String[] args) {

        Server server = new Server();
        server.listen(port);
    }
    private void listen(int port) {
        try {
            bindSocket = new ServerSocket(port);
            System.out.println("server run on " + port);
            dispatch(bindSocket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void dispatch(ServerSocket bindSocket) throws IOException {
        while (true) {
            try {
                Socket clientSocket = bindSocket.accept();
                ServerHandle cliente1 = new ServerHandle(this, clientSocket);
                mylist.add(cliente1);
                service.submit(cliente1);
                System.out.println("you got connect " + port);
                //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("new connection " + getAddress(clientSocket));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void runAll(String input, ServerHandle serverHandle){
        for(int i = 0; i<mylist.size(); i++){
            if(mylist.get(i).equals(serverHandle)){
                continue;
            }
            try {
                out = new PrintWriter(mylist.get(i).getSocket().getOutputStream(), true);
                out.println(input);
                //out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private String getAddress(Socket socket) {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }


}

