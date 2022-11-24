/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interact.JSONConverter;
import interact.JSONMessenger;
import interact.Message;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThreadServe extends Thread{
    boolean flag;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Connection conn;
    JSONMessenger messenger;
    public ThreadServe(Socket socket) {
        try {
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Tiếp khách không thành công: " + ex.getLocalizedMessage());
        }
    }

    
    @Override
    public void run() {
        messenger = new JSONMessenger(socket);
        
        flag = true;
        while (flag) {
            Message message = messenger.expect();
            String log = socket.getInetAddress() + ":" + socket.getPort() + " sends";
            if (message.header == null || message.header.isEmpty()) {
                log += " a headless message";
            } else {
                log += " " + message.header;
            }
            if (message.body == null || message.body.isEmpty()) {
                log += " without any data";
            } else {
                log += " " + message.body;
            }
            System.out.println(log);
            handle(message);
        }
    }
    
    private void handle(Message message) {
        if (message.header == null || message.header.isEmpty()) {
            flag = false;
            System.out.println("Disconnects with " + socket.getInetAddress() + ":" + socket.getPort());
            return;
        }
        String messageHeader = message.header;
        if (messageHeader.equals("DanhSachHocSinh")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachHocSinh();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("ThemHocSinh")) {
            try {
                DBHandler.ThemHocSinh(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("SuaHocSinh")) {
            try {
                DBHandler.SuaHocSinh(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("XoaHocSinh")) {
            try {
                DBHandler.XoaHocSinh(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("HocSinhTheoMaHSvaPassword")) {
            try {
                List<List<Object>> data = DBHandler.HocSinhTheoMaHSvaPassword(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("HocSinhTheoMaHS")) {
            try {
                List<List<Object>> data = DBHandler.HocSinhTheoMaHS(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachLopTheoMon")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachLopTheoMon();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachDiem")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachDiem();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("ThemDiem")) {
            try {
                DBHandler.ThemDiem(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachLopTheoMonTheoMaHS")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachLopTheoMonTheoMaHS(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DiemTheoMaHSvaMaLopTheoMon")) {
            try {
                List<List<Object>> data = DBHandler.DiemTheoMaHSvaMaLopTheoMon(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("SuaDiem")) {
            try {
                DBHandler.SuaDiem(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("XoaDiem")) {
            try {
                DBHandler.XoaDiem(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachLop")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachLop();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        }  else if (messageHeader.equals("ThemLop")) {
            try {
                DBHandler.ThemLop(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("SuaLop")) {
            try {
                DBHandler.SuaLop(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("XoaLop")) {
            try {
                DBHandler.XoaLop(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachGiaoVien")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachGiaoVien();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("LopTheoMaLop")) {
            try {
                List<List<Object>> data = DBHandler.LopTheoMaLop(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("DanhSachMonHoc")) {
            try {
                List<List<Object>> data = DBHandler.DanhSachMonHoc();
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        }  else if (messageHeader.equals("ThemMonHoc")) {
            try {
                DBHandler.ThemMonHoc(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("SuaMonHoc")) {
            try {
                DBHandler.SuaMonHoc(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("XoaMonHoc")) {
            try {
                DBHandler.XoaMonHoc(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", ""));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        } else if (messageHeader.equals("MonHocTheoMaMonHoc")) {
            try {
                List<List<Object>> data = DBHandler.MonHocTheoMaMonHoc(JSONConverter.JSONtoList(message.body));
                messenger.give(new Message("success", JSONConverter.ListListtoJSON(data)));
            } catch (Exception e) {
                messenger.give(new Message("failure", e.toString()));
            }
        }
    
    }
}
