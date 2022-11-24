package server;

import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {

    public DBHandler() {
        
    }
    
    public static List<List<Object>> DanhSachHocSinh() throws SQLException{
        String sql = "SELECT Mahs, Malop, Hoten, Ngaysinh, Email, Password FROM DanhSachHocSinh";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static void ThemHocSinh(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "INSERT INTO DanhSachHocSinh(Mahs, Malop, Hoten, Ngaysinh, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        statement.setString(3, data.get(2).toString());
        java.util.Date ngaysinh = new SimpleDateFormat("yyyy-MM-dd").parse(data.get(3).toString());
        Date d = new Date(ngaysinh.getYear(), ngaysinh.getMonth(), ngaysinh.getDate());
        statement.setDate(4, d);
        statement.setString(5, data.get(4).toString());
        statement.setString(6, data.get(5).toString());
        statement.execute();
    }
    
    public static void SuaHocSinh(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "UPDATE DanhSachHocSinh SET Malop = ?, Hoten = ?, Ngaysinh = ?, Email = ?, Password = ? WHERE (Mahs = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(1).toString());
        statement.setString(2, data.get(2).toString());
        java.util.Date ngaysinh = new SimpleDateFormat("yyyy-MM-dd").parse(data.get(3).toString());
        Date d = new Date(ngaysinh.getYear(), ngaysinh.getMonth(), ngaysinh.getDate());
        statement.setDate(3, d);
        statement.setString(4, data.get(4).toString());
        statement.setString(5, data.get(5).toString());
        statement.setString(6, data.get(0).toString());
        statement.execute();
        
    }
    
    public static void XoaHocSinh(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "DELETE FROM DanhSachHocSinh WHERE (Mahs = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.execute();        
    }
    
    public static List<List<Object>> DanhSachLopTheoMon() throws SQLException{
        String sql = "SELECT Maloptheomon, Mamonhoc, MaGV, Siso, Hocky FROM DanhSachLopTheoMon";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> DanhSachDiem() throws SQLException{
        String sql = "SELECT Mahs, Maloptheomon, Diemthi FROM DanhSachDiem";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static void ThemDiem(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "INSERT INTO DanhSachDiem(Mahs, Maloptheomon, Diemthi) VALUES (?, ?, ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        statement.setInt(3, Integer.parseInt(data.get(2).toString()));
        statement.execute();
    }
    
    public static void SuaDiem(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "UPDATE DanhSachDiem SET Diemthi = ? WHERE (Mahs = ?) AND (Maloptheomon = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(data.get(2).toString()));
        statement.setString(2, data.get(0).toString());
        statement.setString(3, data.get(1).toString());
        statement.execute();
    }
    
    public static void XoaDiem(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "DELETE FROM DanhSachDiem WHERE (Mahs = ?) AND (Maloptheomon = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        statement.execute();
    }
    
    public static List<List<Object>> DanhSachLopTheoMonTheoMaHS(List<Object> data) throws SQLException{
        String sql = "SELECT Maloptheomon, Diemthi FROM DanhSachDiem WHERE (Mahs = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> DiemTheoMaHSvaMaLopTheoMon(List<Object> data) throws SQLException{
        String sql = "SELECT Diemthi FROM DanhSachDiem WHERE (Mahs = ?) AND (Maloptheomon = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> DanhSachLop() throws SQLException{
        String sql = "SELECT Malop, Tenlop, Khoa, Siso, MaGV FROM DanhSachLop";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static void ThemLop(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "INSERT INTO DanhSachLop(Malop, Tenlop, Khoa, Siso, MaGV) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        statement.setString(3, data.get(2).toString());
        statement.setInt(4, Integer.parseInt(data.get(3).toString()));
        statement.setString(5, data.get(4).toString());
        statement.execute();
    }
    
    public static void SuaLop(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "UPDATE DanhSachLop SET Tenlop = ?, Khoa = ?, Siso = ?, MaGV = ? WHERE (Malop = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(1).toString());
        statement.setString(2, data.get(2).toString());
        statement.setInt(3, Integer.parseInt(data.get(3).toString()));
        statement.setString(4, data.get(4).toString());
        statement.setString(5, data.get(0).toString());
        statement.execute();
    }
    
    public static void XoaLop(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "DELETE FROM DanhSachLop WHERE (Malop = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.execute();
    }
    
    public static List<List<Object>> LopTheoMaLop(List<Object> data) throws SQLException{
        String sql = "SELECT Malop, Tenlop, Khoa, Siso, MaGV FROM DanhSachLop WHERE (Malop = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> DanhSachGiaoVien() throws SQLException{
        String sql = "SELECT MaGV FROM DanhSachGiaoVien";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> DanhSachMonHoc() throws SQLException{
        String sql = "SELECT Mamonhoc, Tenmonhoc, Ghichu FROM DanhSachMonHoc";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static void ThemMonHoc(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "INSERT INTO DanhSachMonHoc(Mamonhoc, Tenmonhoc, Ghichu) VALUES (?, ?, ?)";
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        statement.setString(3, data.get(2).toString());
        statement.execute();
    }
    
    public static void SuaMonHoc(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "UPDATE DanhSachMonHoc SET Tenmonhoc = ?, Ghichu = ? WHERE (Mamonhoc = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(1).toString());
        statement.setString(2, data.get(2).toString());
        statement.setString(3, data.get(0).toString());
        statement.execute();
    }
    
    public static void XoaMonHoc(List<Object> data) throws SQLException, IllegalArgumentException, ParseException{
        String sql = "DELETE FROM DanhSachMonHoc WHERE (Mamonhoc = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.execute();
    }
    
    public static List<List<Object>> MonHocTheoMaMonHoc(List<Object> data) throws SQLException{
        String sql = "SELECT Mamonhoc, Tenmonhoc, Ghichu FROM DanhSachMonHoc WHERE (Mamonhoc = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> HocSinhTheoMaHSvaPassword(List<Object> data) throws SQLException{
        String sql = "SELECT Mahs, Malop, Hoten, Ngaysinh, Email, Password FROM DanhSachHocSinh WHERE (Mahs = ?) AND (Password = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        statement.setString(2, data.get(1).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
    
    public static List<List<Object>> HocSinhTheoMaHS(List<Object> data) throws SQLException{
        String sql = "SELECT Mahs, Malop, Hoten, Ngaysinh, Email, Password FROM DanhSachHocSinh WHERE (Mahs = ?)";
        
        PreparedStatement statement = DB.getConnection().prepareStatement(sql);
        statement.setString(1, data.get(0).toString());
        ResultSet rs = statement.executeQuery();
            
        List<List<Object>> result = new ArrayList<List<Object>>();
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
        
        return result;
    }
}
