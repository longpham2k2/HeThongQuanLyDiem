package forms;

import client.Connector;
import interact.JSONConverter;
import interact.Message;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogThemDiem extends JDialog{
    JComboBox<Object> cboMahs; 
    JComboBox<Object> cboMaloptheomon; 
    public DialogThemDiem(Frame owner) {
        super(owner, "Thêm điểm", ModalityType.DOCUMENT_MODAL);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addControl();
        this.pack();
    }
    
    private void addControl() {
        Container con = this.getContentPane();
        
        JPanel pnMahs = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnMaloptheomon = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnDiem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(pnMahs);
        con.add(pnMaloptheomon);
        con.add(pnDiem);
        con.add(pnControl);
        
        // pnMahs
        cboMahs = new JComboBox<Object>(DanhSachHocSinh());
        pnMahs.add(new JLabel("Mahs"));
        pnMahs.add(cboMahs);
        
        // pnMaloptheomon
        cboMaloptheomon = new JComboBox<Object>(DanhSachLopTheoMon());
        pnMaloptheomon.add(new JLabel("Maloptheomon"));
        pnMaloptheomon.add(cboMaloptheomon);
        
        // pnHoten
        JTextField txtDiem = new JTextField(16);
        pnDiem.add(new JLabel("Diem"));
        pnDiem.add(txtDiem);
        
        // pnControl
        JButton btnSave = new JButton("Lưu");
        pnControl.add(btnSave);
        btnSave.addActionListener((ActionEvent e) -> {
            ThemDiem(
                (String) cboMahs.getSelectedItem(), 
                (String) cboMaloptheomon.getSelectedItem(), 
                txtDiem.getText()
            );
        });
        
    }
    
    private void ThemDiem(String Mahs, String Maloptheomon, String Diem) {
        List<Object> data = new ArrayList<Object>();
        data.add(Mahs);
        data.add(Maloptheomon);
        data.add(Diem);
        
        String title = "ThemDiem";
        Connector.give(new Message(title, JSONConverter.ListtoJSON(data)));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            this.dispose();
        } else {
            System.out.println("failure: " + response.body);
            // Không thành công
        }
    }
    public Object[] DanhSachHocSinh() {
        List<Object> result = new ArrayList<Object>();
        String title = "DanhSachHocSinh";
        Connector.give(new Message(title, ""));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            List<List<Object>> data = JSONConverter.JSONtoListList((String) response.body);
            for (List<Object> row : data) {
                result.add(row.get(0));
            }
        }
        return result.toArray();
    }
    public Object[] DanhSachLopTheoMon() {
        List<Object> result = new ArrayList<Object>();
        String title = "DanhSachLopTheoMon";
        Connector.give(new Message(title, ""));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            List<List<Object>> data = JSONConverter.JSONtoListList((String) response.body);
            for (List<Object> row : data) {
                result.add(row.get(0));
            }
        }
        return result.toArray();
    }
}
