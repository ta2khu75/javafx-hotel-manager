package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Guest;
import util.JdbcHelper;

public class GuestDAO extends HotelDAO<Guest, String> {

    String INSERT_SQL = "INSERT INTO KhachHang(CCCD, HoTen, NgaySinh, GioiTinh, SoDienThoai) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE KhachHang SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, SoDienThoai = ? WHERE CCCD = ?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE CCCD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE CCCD = ?";
    String SELECT_BY_ROOM = "select k.* from PhieuDatPhong p join KhachHang k on p.MaKH=k.CCCD where p.TrangThai=0 and MaPhong=?";

    private JdbcHelper jdbcHelper = new JdbcHelper();
    
    public Guest selectByRoom(String idRoom) {
        List<Guest> guests = this.selectBySql(SELECT_BY_ROOM, idRoom);
        if (guests.isEmpty()) {
            return null;
        }
        return guests.get(0);
    }

    @Override
    public void insert(Guest entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getId(), entity.getFullName(), entity.getBirthDate(),
                    entity.isGender(), entity.getPhoneNum());
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Guest entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getFullName(), entity.getBirthDate(),
                    entity.isGender(), entity.getPhoneNum(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Guest entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Guest selectById(String id) {
        List<Guest> guests = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (guests.isEmpty()) {
            return null;
        }
        return guests.get(0);
    }

    @Override
    public List<Guest> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Guest> selectBySql(String sql, Object... args) {
        List<Guest> guests = new ArrayList<>();
        try ( ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                Guest guest = new Guest();
                guest.setId(resultSet.getString("CCCD"));
                guest.setFullName(resultSet.getString("HoTen"));
                guest.setBirthDate(resultSet.getTimestamp("NgaySinh").toLocalDateTime().toLocalDate());
                guest.setGender(resultSet.getBoolean("GioiTinh"));
                guest.setPhoneNum(resultSet.getString("SoDienThoai"));
                guests.add(guest);
            }
            return guests;
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
