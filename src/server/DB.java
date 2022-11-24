package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DB {
    private static String databaseURL = "jdbc:ucanaccess://QuanLyDiem.mdb";
    private static Connection conn = null;
    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(databaseURL);
        }
        
        return conn;
    }
    
    public static List<List<Object>> executeQuery(String sql) {
        List<List<Object>> result = new ArrayList<List<Object>>();
        
        try {
            // Execute SQL
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            
            // Convert Result to Java DataType
            while (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();
                List<Object> row = new ArrayList<Object>();
                
                for (int col = 1; col <= colCount; col++) {
                    Object colValue = rs.getObject(col);
                    row.add(colValue);
                }
                
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
