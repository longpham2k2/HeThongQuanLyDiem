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

public class DialogThemHocSinh extends JDialog{

    public DialogThemHocSinh(Frame owner) {
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
        
        // pnMahs
        JTextField txtMahs = new JTextField(16);
        pnMahs.add(new JLabel("Mahs"));
        pnMahs.add(txtMahs);
        
        // pnMalop
        JComboBox<Object> cboMalop = new JComboBox<Object>(DanhSachLop());
        pnMalop.add(new JLabel("Malop"));
        pnMalop.add(cboMalop);
        
        // pnHoten
        JTextField txtHoten = new JTextField(16);
        pnHoten.add(new JLabel("Hoten"));
        pnHoten.add(txtHoten);
        
        // pnNgaysinh
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField txtNgaysinh = new JFormattedTextField(format);
        txtNgaysinh.setColumns(16);
        txtNgaysinh.setValue(new Date());
        pnNgaysinh.add(new JLabel("Ngaysinh"));
        pnNgaysinh.add(txtNgaysinh);
        
        // pnEmail
        JTextField txtEmail = new JTextField(16);
        pnEmail.add(new JLabel("Email"));
        pnEmail.add(txtEmail);
        
        // pnPassword
        JTextField txtPassword = new JTextField(16);
        pnPassword.add(new JLabel("Password"));
        pnPassword.add(txtPassword);
        
        // pnControl
        JButton btnSave = new JButton("Lưu");
        pnControl.add(btnSave);
        btnSave.addActionListener((ActionEvent e) -> {
            ThemHocSinh(
                txtMahs.getText(), 
                (String) cboMalop.getSelectedItem(), 
                txtHoten.getText(), 
                txtNgaysinh.getText(), 
                txtEmail.getText(), 
                txtPassword.getText()
            );
        });
        
    }
    
    private void ThemHocSinh(String Mahs, String Malop, String Hoten, String Ngaysinh, String Email, String Password) {
        List<Object> data = new ArrayList<Object>();
        data.add(Mahs);
        data.add(Malop);
        data.add(Hoten);
        data.add(Ngaysinh);
        data.add(Email);
        data.add(Password);
        
        String title = "ThemHocSinh";
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
}
