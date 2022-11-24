package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("Server đã sẵn sàng....");
            
            boolean flag = true;
            while (flag) {
                Socket socket = serverSocket.accept();//Chờ Client kết nối đến
                
                ThreadServe serve = new ThreadServe(socket);
                serve.start();
            }
            
            serverSocket.close();
        } catch (Exception ex) {
            System.out.println("Lỗi kết nối "+ex);
        }
    }
    
}
