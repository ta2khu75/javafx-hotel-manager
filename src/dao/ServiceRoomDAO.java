package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ServiceRoom;
import util.JdbcHelper;

public class ServiceRoomDAO extends HotelDAO<ServiceRoom, Integer> {

    String INSERT_SQL = "INSERT INTO DichVuPhong(MaPDPhong, MaDV, SoLuong) VALUES (?, ?, ?)";
    String UPDATE_SQL = "UPDATE DichVuPhong SET MaPDPhong = ?, MaDV = ?, SoLuong = ? WHERE MaDVP = ?";
    String DELETE_SQL = "DELETE FROM DichVuPhong WHERE MaDVP = ?";
    String SELECT_ALL_SQL = "SELECT * FROM DichVuPhong";
    String SELECT_BY_ID_SQL = "SELECT * FROM DichVuPhong WHERE MaDVP = ?";
    String SELECT_BY_ID_BOOKING = "SELECT * FROM DichVuPhong WHERE MaPDPhong=?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    public List<ServiceRoom>  selectByBooking(Integer id) {
        List<ServiceRoom> serviceRooms = this.selectBySql(SELECT_BY_ID_BOOKING, id);
        if (serviceRooms.isEmpty()) {
            return null;
        }
        return serviceRooms;
    }

    @Override
    public void insert(ServiceRoom entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getIdBooking(), entity.getIdService(), entity.getQuantity());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ServiceRoom entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getIdBooking(), entity.getIdService(), entity.getQuantity(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(ServiceRoom entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ServiceRoom selectById(Integer id) {
        List<ServiceRoom> serviceRooms = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (serviceRooms.isEmpty()) {
            return null;
        }
        return serviceRooms.get(0);
    }

    @Override
    public List<ServiceRoom> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ServiceRoom> selectBySql(String sql, Object... args) {
        List<ServiceRoom> serviceRooms = new ArrayList<>();
        try ( ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                ServiceRoom serviceRoom = new ServiceRoom();
                serviceRoom.setId(resultSet.getInt("MaDVP"));
                serviceRoom.setIdBooking(resultSet.getInt("MaPDPhong"));
                serviceRoom.setIdService(resultSet.getInt("MaDV"));
                serviceRoom.setQuantity(resultSet.getInt("SoLuong"));
                serviceRooms.add(serviceRoom);
            }
            return serviceRooms;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
