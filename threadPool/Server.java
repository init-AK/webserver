package threadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private final ExecutorService threadPool;

    public Server(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try{
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
            toClient.println("Hello from the server" + clientSocket.getInetAddress());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server listening on port: " + port);
            while(true) {
                Socket clientSocket = serverSocket.accept();//the new socket is created on connection 
                
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
