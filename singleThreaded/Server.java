package singleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    
    public void run() {
        int port = 8010;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port); // Create a server socket
            socket.setSoTimeout(10000); // sets the timeout of the socket for 10 seconds
        } catch(SocketException ex) {
            ex.printStackTrace();
            return; // Stop execution if there's an error setting up the socket
        } catch(IOException ex) {
            ex.printStackTrace();
            return; // Stop execution if there's an error opening the port
        }

        while(true) {
            try {
                System.out.println("Server is listening on port: " + port);
                Socket acceptedConnection = socket.accept(); // Waits for client to connect here, eligible for 10 seconds
                System.out.println("Connection accepted from client " + acceptedConnection.getRemoteSocketAddress());

                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream())); // Buffered Reader taken in a reader so InputStreamReader returns a Reader

                toClient.println("Hello from the server");
                // Note: You should also add code to read from the client and properly close sockets
                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            } catch(IOException ex) {
                ex.printStackTrace();
                break; // Exit the loop if an I/O error occurs, potentially close socket here
            }
        }
        
       
    }

    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.run();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
