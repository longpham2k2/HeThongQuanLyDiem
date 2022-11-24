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
import java.text.ParseException;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DialogXoaHocSinh extends JDialog{

    public DialogXoaHocSinh(Frame owner) {
        super(owner, "Thêm học sinh", ModalityType.DOCUMENT_MODAL);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addControl();
        this.pack();
    }
    
    private void addControl() {
        Container con = this.getContentPane();
        
        JPanel pnMahs = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnMalop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnHoten = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnNgaysinh = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnEmail = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(pnMahs);
        con.add(pnMalop);
        con.add(pnHoten);
        con.add(pnNgaysinh);
        con.add(pnEmail);
        con.add(pnPassword);
        con.add(pnControl);
        
        JComboBox<Object> cboMahs = new JComboBox<Object>(DanhSachHocSinh());
        JComboBox<Object> cboMalop = new JComboBox<Object>(DanhSachLop());
        JTextField txtHoten = new JTextField(16);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField txtNgaysinh = new JFormattedTextField(format);
        txtNgaysinh.setColumns(16);
        txtNgaysinh.setValue(new Date());
        JTextField txtEmail = new JTextField(16);
        JPasswordField txtPassword = new JPasswordField(16);
        cboMahs.addActionListener((ActionEvent e) -> {
            List<List<Object>> data = HocSinhTheoMaHS((String) cboMahs.getSelectedItem());
            if (data.size() > 0) {
                cboMalop.setSelectedItem(data.get(0).get(1));
                txtHoten.setText((String)data.get(0).get(2));
                Date d = new Date();
                try {
                    d = format.parse((String)data.get(0).get(3));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                txtNgaysinh.setValue(d);
                txtEmail.setText((String)data.get(0).get(4));
                txtPassword.setText((String)data.get(0).get(5));
            }
        });
        // pnMahs
        pnMahs.add(new JLabel("Mahs"));
        pnMahs.add(cboMahs);
        
        // pnMalop
        pnMalop.add(new JLabel("Malop"));
        pnMalop.add(cboMalop);
        
        // pnHoten
        pnHoten.add(new JLabel("Hoten"));
        pnHoten.add(txtHoten);
        
        // pnNgaysinh
        pnNgaysinh.add(new JLabel("Ngaysinh"));
        pnNgaysinh.add(txtNgaysinh);
        
        // pnEmail
        pnEmail.add(new JLabel("Email"));
        pnEmail.add(txtEmail);
        
        // pnPassword
        pnPassword.add(new JLabel("Password"));
        pnPassword.add(txtPassword);
        
        // pnControl
        JButton btnSave = new JButton("Lưu");
        pnControl.add(btnSave);
        btnSave.addActionListener((ActionEvent e) -> {
            XoaHocSinh(
                (String) cboMahs.getSelectedItem()
            );
        });
        
    }
    
    private void XoaHocSinh(String Mahs) {
        List<Object> data = new ArrayList<Object>();
        data.add(Mahs);
        
        String title = "XoaHocSinh";
        Connector.give(new Message(title, JSONConverter.ListtoJSON(data)));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            this.dispose();
        } else {
            System.out.println("failure: " + response.body);
            // Không thành công
        }
    }
            
    public Object[] DanhSachLop() {
        List<Object> result = new ArrayList<Object>();
        String title = "DanhSachLop";
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
    
    public List<List<Object>> HocSinhTheoMaHS(String Mahs) {
        List<Object> body = new ArrayList<Object>();
        body.add(Mahs);
        
        List<List<Object>> result = new ArrayList<List<Object>>();
        String title = "HocSinhTheoMaHS";
        Connector.give(new Message(title, JSONConverter.ListtoJSON(body)));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            List<List<Object>> data = JSONConverter.JSONtoListList((String) response.body);
            for (List<Object> row : data) {
                result.add(row);
            }
        }
        return result;
    }
}
