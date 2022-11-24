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

public class DialogThemLop extends JDialog{
    JTextField txtMalop;
    JTextField txtTenlop;
    JTextField txtKhoa;
    JTextField txtSiso;
    JComboBox<Object> cboMaGV;
    public DialogThemLop(Frame owner) {
        super(owner, "Thêm lớp", ModalityType.DOCUMENT_MODAL);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addControl();
        this.pack();
    }
    
    private void addControl() {
        Container con = this.getContentPane();
        
        JPanel pnMalop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnTenlop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnKhoa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnSiso = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnMaGV = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnControl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
        con.add(pnMalop);
        con.add(pnTenlop);
        con.add(pnKhoa);
        con.add(pnSiso);
        con.add(pnMaGV);
        con.add(pnControl);
        
        // pnMalop
        txtMalop = new JTextField(16);
        pnMalop.add(new JLabel("Malop"));
        pnMalop.add(txtMalop);
        
        // pnTenlop
        txtTenlop = new JTextField(16);
        pnTenlop.add(new JLabel("Tenlop"));
        pnTenlop.add(txtTenlop);
        
        // pnKhoa
        txtKhoa = new JTextField(16);
        pnKhoa.add(new JLabel("Khoa"));
        pnKhoa.add(txtKhoa);
        
        // pnSiso
        txtSiso = new JTextField(16);
        pnSiso.add(new JLabel("Siso"));
        pnSiso.add(txtSiso);
        
        // pnSiso
        cboMaGV = new JComboBox<Object>(DanhSachGiaoVien());
        pnMaGV.add(new JLabel("MaGV"));
        pnMaGV.add(cboMaGV);
        
        // pnControl
        JButton btnSave = new JButton("Lưu");
        pnControl.add(btnSave);
        btnSave.addActionListener((ActionEvent e) -> {
            ThemLop(
                txtMalop.getText(),
                txtTenlop.getText(),
                txtKhoa.getText(),
                txtSiso.getText(),
                (String) cboMaGV.getSelectedItem()
            );
        });
        
    }
    
    private void ThemLop(String Malop, String Tenlop, String Khoa, String Siso, String MaGV) {
        List<Object> data = new ArrayList<Object>();
        data.add(Malop);
        data.add(Tenlop);
        data.add(Khoa);
        data.add(Siso);
        data.add(MaGV);
        
        String title = "ThemLop";
        Connector.give(new Message(title, JSONConverter.ListtoJSON(data)));
        Message response = Connector.expect();
        if (response.header.equals("success")) {
            this.dispose();
        } else {
            System.out.println("failure: " + response.body);
            // Không thành công
        }
    }
    public Object[] DanhSachGiaoVien() {
        List<Object> result = new ArrayList<Object>();
        String title = "DanhSachGiaoVien";
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
