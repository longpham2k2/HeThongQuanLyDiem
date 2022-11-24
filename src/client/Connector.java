package client;

import interact.JSONMessenger;
import interact.Message;
import java.net.Socket;

public class Connector {
    private static String host = "localhost";
    private static int port = 6666;
    
    public static JSONMessenger clientSide = null;
    public static void give(Message request) {
        if (clientSide == null) {
            try { 
                clientSide = new JSONMessenger(new Socket(host, port)); 
            }
            catch (Exception e) { 
                e.printStackTrace(); 
            }
        }
        clientSide.give(request);
    }
    
    public static Message expect() {
        if (clientSide == null) {
            try { 
                clientSide = new JSONMessenger(new Socket(host, port)); 
            }
            catch (Exception e) { 
                e.printStackTrace(); 
            }
        }
        return clientSide.expect();
    } 
}
