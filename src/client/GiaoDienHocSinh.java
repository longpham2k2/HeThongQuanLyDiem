package client;

import interact.JSONConverter;
import interact.Message;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GiaoDienHocSinh extends JFrame{
    private final int APP_WIDTH = 640;
    private final int APP_HEIGHT = 360;
    private JPanel pnLogin;
    private JPanel pnMain;
    private static String username;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    public GiaoDienHocSinh() throws HeadlessException {
        super("Tra cứu điểm");
        this.setSize(APP_WIDTH, APP_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addControl();
    }
    
    private void addControl(){
        Container con = getContentPane();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        pnLogin = new JPanel();
        pnMain = new JPanel();
        con.add(pnLogin);
        con.add(pnMain);
        pnMain.setVisible(false);
        
        // pnLogin
        JPanel pnUsername = new JPanel();
        JPanel pnPassword = new JPanel();
        txtUsername = new JTextField(16);
        txtPassword = new JPasswordField(16);
        JButton btnLogin = new JButton("Login");
        //pnLogin.setLayout(new BoxLayout(pnLogin, BoxLayout.Y_AXIS));
        pnLogin.add(pnUsername);
        pnLogin.add(pnPassword);
        pnUsername.add(new JLabel("Username"));
        pnUsername.add(txtUsername);
        pnPassword.add(new JLabel("Password"));
        pnPassword.add(txtPassword);
        pnLogin.add(btnLogin);
        btnLogin.addActionListener((ActionEvent e) -> {
            List<List<Object>> result = login(
                txtUsername.getText(), 
                new String(txtPassword.getPassword())
            );
            if (result.size() > 0) {
                username = (String) result.get(0).get(0);
                pnLogin.setVisible(false);
                pnMain.setVisible(true);
                this.repaint();
            }
        });
        
        JButton btnDangXuat = new JButton("Đăng xuất");
        JButton btnXemHocSinh = new JButton("Xem thông tin cá nhân");
        JButton btnXemDiem = new JButton("Xem điểm");
        pnMain.add(btnDangXuat);
        pnMain.add(btnXemHocSinh);
        pnMain.add(btnXemDiem);
        btnDangXuat.addActionListener((ActionEvent e) -> {
            username = "";
            pnMain.setVisible(false);
            pnLogin.setVisible(true);
            this.repaint();
        });
        
        btnXemHocSinh.addActionListener((ActionEvent e) -> {
            JDialog dialog = dialogXemThongTinCaNhan();
        });
        btnXemDiem.addActionListener((ActionEvent e) -> {
            JDialog dialog = dialogXemDiem();
        });
        
    }
    
    public void showWindow() {
        this.setVisible(true);
    }
    
    private List<List<Object>> login (String username, String password) {
        List<Object> body = new ArrayList<Object>();
        body.add(username);
        body.add(password);
        
        List<List<Object>> result = new ArrayList<List<Object>>();
        String title = "HocSinhTheoMaHSvaPassword";
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
    
    private JDialog dialogXemDiem() {
        JTextField txtMahs = new JTextField(username);
        JComboBox<Object> cboMaloptheomon = new JComboBox<Object>(DanhSachLopTheoMonTheoMaHS(txtMahs.getText()));
        JTextField txtDiemthi = new JTextField(16);
        
        JDialog dialog = new JDialog(this, "Xem điểm", Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Container con = dialog.getContentPane();
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
        pnMahs.add(new JLabel("Mahs"));
        pnMahs.add(txtMahs);
        txtMahs.setEditable(false);
        
        // pnMaloptheomon
        cboMaloptheomon.addActionListener((ActionEvent e) -> {
            txtDiemthi.setText(
                (String) DiemTheoMaHSvaMaLopTheoMon(
                    (String) txtMahs.getText(),
                    (String) cboMaloptheomon.getSelectedItem()
                ).get(0).get(0));
            this.repaint();
        });
        pnMaloptheomon.add(new JLabel("Maloptheomon"));
        pnMaloptheomon.add(cboMaloptheomon);
        
        // pnHoten
        txtDiemthi.setEditable(false);
        pnDiem.add(new JLabel("Diemthi"));
        pnDiem.add(txtDiemthi);
        
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }
    
    private JDialog dialogXemThongTinCaNhan() {
        JTextField txtMahs = new JTextField(username);
        JComboBox<Object> cboMalop = new JComboBox<Object>(DanhSachLop());
        JTextField txtHoten = new JTextField(16);
        JFormattedTextField txtNgaySinh = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        txtNgaySinh.setColumns(16);
        JTextField txtEmail = new JTextField(16);
        JTextField txtPassword = new JTextField(16);
        
        List<List<Object>> listHocSinhTheoMaHS = HocSinhTheoMaHS(username);
        if (listHocSinhTheoMaHS.size() > 0) {
            txtMahs.setText((String) listHocSinhTheoMaHS.get(0).get(0));
            cboMalop.setSelectedItem(listHocSinhTheoMaHS.get(0).get(1));
            txtHoten.setText((String) listHocSinhTheoMaHS.get(0).get(2));
            txtNgaySinh.setText((String) listHocSinhTheoMaHS.get(0).get(3));
            txtEmail.setText((String) listHocSinhTheoMaHS.get(0).get(4));
            txtPassword.setText((String) listHocSinhTheoMaHS.get(0).get(5));
        }
        
        JDialog dialog = new JDialog(this, "Xem thông tin cá nhân", Dialog.ModalityType.DOCUMENT_MODAL);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Container con = dialog.getContentPane();
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
        //con.add(pnPassword);
        con.add(pnControl);
        
        // pnMahs
        pnMahs.add(new JLabel("Mahs"));
        pnMahs.add(txtMahs);
        txtMahs.setEditable(false);
        
        // pnMalop
        pnMalop.add(new JLabel("Malop"));
        pnMalop.add(cboMalop);
        cboMalop.setEnabled(false);
        
        // pnHoten
        pnHoten.add(new JLabel("Hoten"));
        pnHoten.add(txtHoten);
        txtHoten.setEditable(false);
        
        // pnNgaysinh
        pnNgaysinh.add(new JLabel("Ngaysinh"));
        pnNgaysinh.add(txtNgaySinh);
        txtNgaySinh.setEditable(false);
        
        // pnEmail
        pnEmail.add(new JLabel("Email"));
        pnEmail.add(txtEmail);
        txtEmail.setEditable(false);
        
        // pnPassword
        pnPassword.add(new JLabel("Password"));
        pnPassword.add(txtPassword);
        txtPassword.setEditable(false);
        
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }
    
    public Object[] DanhSachLopTheoMonTheoMaHS(String Mahs) {
        List<Object> body = new ArrayList<Object>();
        body.add(Mahs);
        
        List<Object> result = new ArrayList<Object>();
        String title = "DanhSachLopTheoMonTheoMaHS";
        Connector.give(new Message(title, JSONConverter.ListtoJSON(body)));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            List<List<Object>> data = JSONConverter.JSONtoListList((String) response.body);
            for (List<Object> row : data) {
                result.add(row.get(0));
            }
        }
        return result.toArray();
    }
    
    public List<List<Object>> DiemTheoMaHSvaMaLopTheoMon(String Mahs, String Maloptheomon) {
        List<Object> body = new ArrayList<Object>();
        body.add(Mahs);
        body.add(Maloptheomon);
        
        List<List<Object>> result = new ArrayList<List<Object>>();
        String title = "DiemTheoMaHSvaMaLopTheoMon";
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
