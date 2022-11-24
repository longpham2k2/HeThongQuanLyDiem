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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogXoaLop extends JDialog{
    JComboBox<Object> cboMalop;
    JTextField txtTenlop;
    JTextField txtKhoa;
    JTextField txtSiso;
    JComboBox<Object> cboMaGV;
    public DialogXoaLop(Frame owner) {
        super(owner, "Xoá lớp", ModalityType.DOCUMENT_MODAL);
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
        cboMalop = new JComboBox<Object>(DanhSachLop());
        cboMalop.addActionListener((ActionEvent e) -> {
            List<Object> lop = LopTheoMaLop(
                (String) cboMalop.getSelectedItem()
            ).get(0);
            txtTenlop.setText((String) lop.get(1));
            txtKhoa.setText((String) lop.get(2));
            txtSiso.setText((String) lop.get(3));
            cboMaGV.setSelectedItem(lop.get(4));
            this.repaint();
        });
        pnMalop.add(new JLabel("Malop"));
        pnMalop.add(cboMalop);
        
        // pnTenlop
        txtTenlop = new JTextField(16);
        pnTenlop.add(new JLabel("Maloptheomon"));
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
            XoaLop(
                (String) cboMalop.getSelectedItem()
            );
        });
        
    }
    
    private void XoaLop(String Malop) {
        List<Object> data = new ArrayList<Object>();
        data.add(Malop);
        
        String title = "XoaLop";
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
    
    public List<List<Object>> LopTheoMaLop(String Malop) {
        List<Object> body = new ArrayList<Object>();
        body.add(Malop);
        
        List<List<Object>> result = new ArrayList<List<Object>>();
        String title = "LopTheoMaLop";
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
