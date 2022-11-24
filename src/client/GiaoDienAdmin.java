/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Admin
 */
public class GiaoDienAdmin extends JFrame{
    private final int APP_WIDTH = 640;
    private final int APP_HEIGHT = 360;
    public GiaoDienAdmin() throws HeadlessException {
        super("Quản lý điểm");
        this.setSize(APP_WIDTH, APP_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addControl();
    }
    
    private void addControl(){
        Container con = getContentPane();
        JPanel pnMain = new JPanel();
        con.add(pnMain);
        
        JButton btnQuanLyLop = new JButton("Quản lý lớp");
        JButton btnQuanLyHocSinh = new JButton("Quản lý học sinh");
        JButton btnQuanLyMonHoc = new JButton("Quản lý môn học");
        JButton btnQuanLyLopTheoMon = new JButton("Quản lý lớp theo môn");
        JButton btnQuanLyDiem = new JButton("Quản lý điểm");
        pnMain.add(btnQuanLyLop);
        pnMain.add(btnQuanLyHocSinh);
        pnMain.add(btnQuanLyMonHoc);
        pnMain.add(btnQuanLyLopTheoMon);
        pnMain.add(btnQuanLyDiem);
        
        btnQuanLyLop.addActionListener((ActionEvent e) -> {
            forms.FormQuanLyLop fmQuanLy = new forms.FormQuanLyLop();
            fmQuanLy.setVisible(true);
            fmQuanLy.setLocationRelativeTo(this);
            this.dispose();
        });
        btnQuanLyHocSinh.addActionListener((ActionEvent e) -> {
            forms.FormQuanLyHocSinh fmQuanLy = new forms.FormQuanLyHocSinh();
            fmQuanLy.setVisible(true);
            fmQuanLy.setLocationRelativeTo(this);
            this.dispose();
        });
        btnQuanLyMonHoc.addActionListener((ActionEvent e) -> {
            forms.FormQuanLyMonHoc fmQuanLy = new forms.FormQuanLyMonHoc();
            fmQuanLy.setVisible(true);
            fmQuanLy.setLocationRelativeTo(this);
            this.dispose();
        });
        btnQuanLyLopTheoMon.addActionListener((ActionEvent e) -> {
            forms.FormQuanLyLopTheoMon fmQuanLy = new forms.FormQuanLyLopTheoMon();
            fmQuanLy.setVisible(true);
            fmQuanLy.setLocationRelativeTo(this);
            this.dispose();
        });
        btnQuanLyDiem.addActionListener((ActionEvent e) -> {
            forms.FormQuanLyDiem fmQuanLy = new forms.FormQuanLyDiem();
            fmQuanLy.setVisible(true);
            fmQuanLy.setLocationRelativeTo(this);
            this.dispose();
        });
        
    }
    
    public void showWindow() {
        this.setVisible(true);
    }
}
