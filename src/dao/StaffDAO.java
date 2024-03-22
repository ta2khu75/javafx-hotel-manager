package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Staff;
import util.JdbcHelper;

public class StaffDAO extends HotelDAO<Staff, String> {
    String INSERT_SQL = "INSERT INTO NhanVien(MaNV, MatKhau, HoTen,NgaySinh, GioiTinh, CCCD, Email, SoDienThoai, VaiTro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NhanVien SET MatKhau = ?, HoTen = ?,NgaySinh = ?, GioiTinh = ?, CCCD = ?, Email = ?, SoDienThoai = ?, VaiTro = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    @Override
    public void insert(Staff entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getId(), entity.getPassword(), entity.getFullName(),
                    entity.getBirthDate(),entity.isGender(), entity.getDocumentCard(), entity.getEmail(), 
                    entity.getPhoneNum(),entity.isRole());
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Staff entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getPassword(), entity.getFullName(), entity.getBirthDate(),entity.isGender(),
                    entity.getDocumentCard(), entity.getEmail(), entity.getPhoneNum(), entity.isRole(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Staff entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Staff selectById(String id) {
        List<Staff> staffs = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (staffs.isEmpty()) {
            return null;
        }
        return staffs.get(0);
    }

    @Override
    public List<Staff> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Staff> selectBySql(String sql, Object... args) {
        List<Staff> staffs = new ArrayList<>();
        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {

            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setId(resultSet.getString("MaNV"));
                staff.setPassword(resultSet.getString("MatKhau"));
                staff.setFullName(resultSet.getString("HoTen"));
                staff.setBirthDate(resultSet.getDate("NgaySinh"));
                staff.setGender(resultSet.getBoolean("GioiTinh"));
                staff.setDocumentCard(resultSet.getString("CCCD"));
                staff.setEmail(resultSet.getString("Email"));
                staff.setPhoneNum(resultSet.getString("SoDienThoai"));
                staff.setRole(resultSet.getBoolean("VaiTro"));
                staffs.add(staff);
            }
            return staffs;
        } catch (SQLException ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public Staff selectByEmail(String email) {
        String sql = "SELECT * FROM NhanVien WHERE Email = ?";
        List<Staff> staffs = this.selectBySql(sql, email);
        if (staffs.isEmpty()) {
            return null;
        }
        return staffs.get(0);
    }
}
