package interact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Messenger {
    private Socket socket = null;
    protected DataInputStream dis = null;
    protected DataOutputStream dos = null;
    
    public Messenger(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Sends a string to socket.
     * @param outputString 
     */
    public void send(String outputString) {
        byte[] outputBytes = outputString.getBytes();
        try {
            dos.write(outputBytes);
        } catch (Exception e) {
            System.out.println(socket.getInetAddress().toString() + ":" + socket.getPort() + ": " + e.getLocalizedMessage());
        }
    }
    
    /**
     * Receives a string form socket.
     * @return 
     */
    public String receive() {
        byte[] inputBytes = new byte[8192];
        try {
            dis.read(inputBytes);
        } catch (Exception e) {
            System.out.println(socket.getInetAddress().toString() + ":" + socket.getPort() + ": " + e.getLocalizedMessage());
        }
        return new String(inputBytes).trim();
    }
}
