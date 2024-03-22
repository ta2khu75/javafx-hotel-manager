package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;
import util.JdbcHelper;

public class RoomDAO extends HotelDAO<Room, String> {
    String INSERT_SQL = "INSERT INTO Phong(MaPhong, TrangThai, MaLoaiPhong) VALUES (?, ?, ?)";
    String UPDATE_SQL = "UPDATE Phong SET TrangThai = ?, MaLoaiPhong = ? WHERE MaPhong = ?";
    String DELETE_SQL = "DELETE FROM Phong WHERE MaPhong = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Phong";
    String SELECT_BY_ID_SQL = "SELECT * FROM Phong WHERE MaPhong = ?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    @Override
    public void insert(Room entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getId(), entity.getStatus(), entity.getIdTypeofRoom());
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Room entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getStatus(), entity.getIdTypeofRoom(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Room entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Room selectById(String id) {
        List<Room> rooms = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (rooms.isEmpty()) {
            return null;
        }
        return rooms.get(0);
    }

    @Override
    public List<Room> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Room> selectBySql(String sql, Object... args) {
        List<Room> rooms = new ArrayList<>();
        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getString("MaPhong"));
                room.setStatus(resultSet.getInt("TrangThai"));
                room.setIdTypeofRoom(resultSet.getInt("MaLoaiPhong"));
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

