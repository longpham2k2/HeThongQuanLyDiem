# HeThongQuanLyDiem
Server chứa các dữ liệu về học sinh và lớp học gồm các thông tin được mô tả trong các bảng dưới đây:
DanhSachLop(#"Malop", "Tenlop", "Khoa", "Siso", "MaGV");
DanhSachHocSinh(#"Mahs", @"Malop", "HoTen", "Ngaysinh", "Email", "Password");
DanhSachMonHoc(#"Mamonhoc", "Tenmonhoc", "Ghichu");
DanhSachLopTheoMon(#"Maloptheomon", @"Mamonhoc", "MaGV", "Siso", "Hocky");
DanhSachDiem(#"Mahs", #"Maloptheomon", "Diemthi");

Sử dụng hệ quản trị cơ sở dữ liệu xây dựng CSDL như mô tả. 
Thiết kế và cài đặt hệ thống theo mô hình Client/Server theo giao thức TCP với các yêu cầu sau: 
 Sinh viên có thể đăng nhập vào hệ thống với username bằng mã SV và password bằng mã sinh viên.
 Xem được thông tin về điểm của mình.
