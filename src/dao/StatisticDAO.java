/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ta2khu75
 */
public class StatisticDAO {
    String totalService = "select sum(soluong) from DichVuPhong";
    String totalServiceName="select  TenDV ,sum(soluong) from DichVuPhong p join DichVu d on p.MaDV=d.MaDV GROUP by TenDV order by sum(soluong) desc";
    String totalRoom="select count(MaPDP) from PhieuDatPhong";
    String totalTypeRoom="select TenLoaiPhong ,count(MaPDP) from PhieuDatPhong pdp join phong p on pdp.MaPhong=p.MaPhong join LoaiPhong l on l.MaLoaiPhong=p.MaLoaiPhong group by l.TenLoaiPhong";
    String revenue="select top 7 year(ngaygioTra), month(ngaygiotra), day(NgayGioTra), sum(tongtien) from PhieuDatPhong where TrangThai=1 GROUP by year(ngaygioTra), month(ngaygiotra), day(NgayGioTra) ORDER by year(ngaygioTra), month(ngaygiotra), day(NgayGioTra) desc";
    private JdbcHelper jdbcHelper = new JdbcHelper();
    
    public List<Object[]> getTotalDayOfWeek(){
        List<Object[]> list=new ArrayList<>();
        try {
            ResultSet rs=jdbcHelper.query(revenue);
            while(rs.next()){
                
                Object[] object={rs.getInt(1)+"/"+rs.getInt(2)+"/"+rs.getInt(3), rs.getInt(4)};
                list.add(object);
            }
            return list;
        } catch (Exception e) {
        }return null;
    }
    public int getTotalRoom(){
        try {
            ResultSet rs = jdbcHelper.query(totalRoom);
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }return 0;
    }
    public HashMap<String, Integer> getTotalTypeRoom(){
        HashMap<String, Integer> map=new HashMap<>();
        try {
            ResultSet rs=jdbcHelper.query(totalTypeRoom);
            while(rs.next()){
                map.put(rs.getString(1), rs.getInt(2));
            }
            return map;
        } catch (Exception e) {
        }return null;
    }
    public int getTotalService() {
        try {
            ResultSet rs = jdbcHelper.query(totalService);
            while(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }return 0;
    }
    public HashMap<String, Integer> getTotalServiceName(){
        HashMap<String, Integer> map=new HashMap<>();
        try {
            ResultSet rs=jdbcHelper.query(totalServiceName);
            while(rs.next()){
                map.put(rs.getString(1), rs.getInt(2));
            }
            return map;
        } catch (Exception e) {
        }return null;
    }
//    protected List<Staff> selectBySql(String sql, Object... args) {
//        List<Staff> staffs = new ArrayList<>();
//        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {
//
//            while (resultSet.next()) {
//                Staff staff = new Staff();
//                staff.setId(resultSet.getString("MaNV"));
//                staff.setPassword(resultSet.getString("MatKhau"));
//                staff.setFullName(resultSet.getString("HoTen"));
//                staff.setBirthDate(resultSet.getDate("NgaySinh"));
//                staff.setGender(resultSet.getBoolean("GioiTinh"));
//                staff.setDocumentCard(resultSet.getString("CCCD"));
//                staff.setEmail(resultSet.getString("Email"));
//                staff.setPhoneNum(resultSet.getString("SoDienThoai"));
//                staff.setRole(resultSet.getBoolean("VaiTro"));
//                staffs.add(staff);
//            }
//            return staffs;
//        } catch (SQLException ex) {
//            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
}
