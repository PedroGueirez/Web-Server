import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandle implements  Runnable{
    private Server server;



    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public ServerHandle(Server server, Socket socket){
        this.server = server;
        this.socket = socket;


    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(Thread.currentThread().getName() + "you enter the server");
              while (true){
                  String input = in.readLine();
                  System.out.println(input);
                  server.runAll(input,this);
                  if(input.equals("exit")){
                      in.close();
                  }
              }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Socket getSocket() {
        return socket;
    }
}
