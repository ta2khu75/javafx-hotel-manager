package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Service;
import model.TypeOfService;
import util.JdbcHelper;

public class ServiceDAO extends HotelDAO<Service, Integer> {
    String INSERT_SQL = "INSERT INTO DichVu(TenDV, DonGia, MaLDV) VALUES (?, ?, ?)";
    String UPDATE_SQL = "UPDATE DichVu SET TenDV = ?, DonGia = ?, MaLDV = ? WHERE MaDV = ?";
    String DELETE_SQL = "DELETE FROM DichVu WHERE MaDV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM DichVu";
    String SELECT_BY_ID_SQL = "SELECT * FROM DichVu WHERE MaDV = ?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    @Override
    public void insert(Service entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getName(), entity.getPrice(), entity.getIdTypeOfService());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Service entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getName(), entity.getPrice(), entity.getIdTypeOfService(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Service entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Service selectById(Integer id) {
        List<Service> services = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (services.isEmpty()) {
            return null;
        }
        return services.get(0);
    }

    @Override
    public List<Service> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Service> selectBySql(String sql, Object... args) {
        List<Service> services = new ArrayList<>();
        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                Service service = new Service();
                service.setId(resultSet.getInt("MaDV"));
                service.setName(resultSet.getString("TenDV"));
                service.setPrice(resultSet.getFloat("DonGia"));
                service.setIdTypeOfService(resultSet.getString("MaLDV"));
                services.add(service);
            }
            return services;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<Service> selectServiceByIdTypeOfService(TypeOfService tos) {
        String sql = "select * from DichVu\n" +
                    "where MaLDV = ?";
        return selectBySql(sql, tos.getId());
    }
}
