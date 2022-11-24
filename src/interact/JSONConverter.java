package interact;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class JSONConverter {
    public static List<List<Object>> JSONtoListList(String json) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        try {
            JSONArray ja = new JSONArray(json);
            int len = ja.length();
            for (int i = 0; i < len; i++) {
                List<Object> sublist = new ArrayList<Object>();
                
                String str = ja.get(i).toString();
                JSONArray subja = new JSONArray(str);
                int sublen = subja.length();
                for (int j = 0; j < sublen; j++) {
                    String substr = subja.get(j).toString();
                    sublist.add(substr);
                }
                list.add(sublist);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } 
        return list;
    }
    
    public static String ListListtoJSON(List<List<Object>> list) {
        JSONArray ja = new JSONArray(list);
        return ja.toString();
    }
    
    public static List<Object> JSONtoList(String json) {
        List<Object> list = new ArrayList<Object>();
        try {
            JSONArray ja = new JSONArray(json);
            int len = ja.length();
            for (int i = 0; i < len; i++) {
                String str = ja.getString(i);
                list.add(str);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } 
        return list;
    }
    
    public static String ListtoJSON(List<Object> list) {
        JSONArray ja = new JSONArray(list);
        return ja.toString();
    }
}
