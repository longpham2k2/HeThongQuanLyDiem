package interact;

import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessenger extends Messenger{

    public JSONMessenger(Socket socket) {
        super(socket);
    }
    
    /**
     * Sends a Message to socket. 
     * @param request 
     */
    public void give(Message request) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("header", request.header); 
            jo.put("body", request.body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        send(jo.toString()); 
    }
    
    /**
     * Expect a Message from socket.
     * @return Message {message: String, data: Object}
     */
    public Message expect() {
        Message response = new Message();
        String received = receive();
        if (! received.isEmpty()) {
            JSONObject jo = new JSONObject();
            try {
                jo = new JSONObject(received);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                response.header = jo.getString("header");
            } catch (JSONException e) {
                response.header = "";
            }
            try {
                response.body = jo.getString("body");
            } catch (JSONException e) {
                response.body = "";
            }
        }
        return response;
    }
}
