package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TypeOfRoom;
import util.JdbcHelper;

public class TypeOfRoomDAO extends HotelDAO<TypeOfRoom, Integer> {
    String INSERT_SQL = "INSERT INTO LoaiPhong(TenLoaiPhong, GiaTheoGio, GiaTheoNgay) VALUES (?, ?, ?)";
    String UPDATE_SQL = "UPDATE LoaiPhong SET TenLoaiPhong = ?, GiaTheoGio = ?, GiaTheoNgay = ? WHERE MaLoaiPhong = ?";
    String DELETE_SQL = "DELETE FROM LoaiPhong WHERE MaLoaiPhong = ?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiPhong";
    String SELECT_BY_ID_SQL = "SELECT * FROM LoaiPhong WHERE MaLoaiPhong = ?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    @Override
    public void insert(TypeOfRoom entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getName(), entity.getHourlyPrice(), entity.getPricePerDay());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(TypeOfRoom entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getName(), entity.getHourlyPrice(), entity.getPricePerDay(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(TypeOfRoom entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TypeOfRoom selectById(Integer id) {
        List<TypeOfRoom> typeOfRooms = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (typeOfRooms.isEmpty()) {
            return null;
        }
        return typeOfRooms.get(0);
    }

    @Override
    public List<TypeOfRoom> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<TypeOfRoom> selectBySql(String sql, Object... args) {
        List<TypeOfRoom> typeOfRooms = new ArrayList<>();
        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                TypeOfRoom typeOfRoom = new TypeOfRoom();
                typeOfRoom.setId(resultSet.getInt("MaLoaiPhong"));
                typeOfRoom.setName(resultSet.getString("TenLoaiPhong"));
                typeOfRoom.setHourlyPrice(resultSet.getFloat("GiaTheoGio"));
                typeOfRoom.setPricePerDay(resultSet.getFloat("GiaTheoNgay"));
                typeOfRooms.add(typeOfRoom);
            }
            return typeOfRooms;
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
