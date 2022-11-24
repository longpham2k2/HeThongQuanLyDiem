/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import client.Connector;
import client.GiaoDienAdmin;
import interact.JSONConverter;
import interact.Message;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class FormQuanLyDiem extends JFrame{

    public FormQuanLyDiem() {
        super("Quản lý điểm");
        this.setSize(480, 360);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new GiaoDienAdmin().showWindow();
                super.windowClosing(e);
            }
        
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addControl();
    }
    
    private void addControl() {
        Container con = getContentPane();
        JSplitPane pnMain = new JSplitPane();
        con.add(pnMain);
        
        pnMain.setOrientation(JSplitPane.VERTICAL_SPLIT);
        JPanel pnControl = new JPanel();
        JPanel pnTable = new JPanel();
        pnMain.setTopComponent(pnControl);
        pnMain.setBottomComponent(pnTable);
        
        pnTable.setLayout(new BoxLayout(pnTable, BoxLayout.Y_AXIS));
        JPanel pnTitle = new JPanel();
        JTable tb = new JTable();
        pnTable.add(pnTitle);
        pnTable.add(new JScrollPane(tb));
        
        pnTitle.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitle = new JLabel("Danh sách điểm");
        pnTitle.add(lblTitle);
        
        tb.setModel(DanhSach());
        tb.setEnabled(false);
        
        pnControl.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton btnCreate = new JButton("Thêm mới");
        JButton btnUpdate = new JButton("Chỉnh sửa");
        JButton btnDelete = new JButton("Xoá bỏ");
        pnControl.add(btnCreate);
        pnControl.add(btnUpdate);
        pnControl.add(btnDelete);
        
        // ActionListener
        btnCreate.addActionListener((ActionEvent e) -> {
            JDialog dialog = new DialogThemDiem(this);
            dialog.setVisible(true);
            tb.setModel(DanhSach());
            this.repaint();
        });
        btnUpdate.addActionListener((ActionEvent e) -> {
            JDialog dialog = new DialogSuaDiem(this);
            dialog.setVisible(true);
            tb.setModel(DanhSach());
            this.repaint();
        });
        btnDelete.addActionListener((ActionEvent e) -> {
            JDialog dialog = new DialogXoaDiem(this);
            dialog.setVisible(true);
            tb.setModel(DanhSach());
            this.repaint();
        });
    }
    
    public DefaultTableModel DanhSach() {
        String[] attributes = new String[]{"Mahs", "Maloptheomon", "Diemthi"};
        DefaultTableModel dtm = new DefaultTableModel();
        for (String columnName : attributes) {
            dtm.addColumn(columnName);
        }
        
        String title = "DanhSachDiem";
        Connector.give(new Message(title, ""));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            List<List<Object>> data = JSONConverter.JSONtoListList((String) response.body);
            for (List<Object> row : data) {
                dtm.addRow(row.toArray());
            }
        }
        
        return dtm;
    }
}
