package multiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {

        // return new Consumer<Socket>() {
        //     @Override
        //     public void accept(Socket clientSocket) {
        //         try {
        //             PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        //             toClient.println("Hello from the server");
        //             toClient.close();
        //             clientSocket.close()
        //         }catch(IOException ex) {
        //             ex.printStackTrace();
        //         }
        //     }
        // }

        return (clientSocket)->{
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();
            }catch(IOException ex) {
                ex.printStackTrace();
            }
        };
    }
    
    public static void main(String[] args) throws IOException{
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server listening on port: " + port);
            while(true) {
                Socket acceptedSocket = serverSocket.accept();//the new socket is created on connection 
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
