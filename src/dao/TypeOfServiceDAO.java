package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TypeOfService;
import util.JdbcHelper;

public class TypeOfServiceDAO extends HotelDAO<TypeOfService, String> {
    String INSERT_SQL = "INSERT INTO LoaiDichVu(MaLDV, TenDV) VALUES (?, ?)";
    String UPDATE_SQL = "UPDATE LoaiDichVu SET TenDV = ? WHERE MaLDV = ?";
    String DELETE_SQL = "DELETE FROM LoaiDichVu WHERE MaLDV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM LoaiDichVu";
    String SELECT_BY_ID_SQL = "SELECT * FROM LoaiDichVu WHERE MaLDV = ?";
    private JdbcHelper jdbcHelper = new JdbcHelper();

    @Override
    public void insert(TypeOfService entity) {
        try {
            jdbcHelper.update(INSERT_SQL, entity.getId(), entity.getName());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(TypeOfService entity) {
        try {
            jdbcHelper.update(UPDATE_SQL, entity.getName(), entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(TypeOfService entity) {
        try {
            jdbcHelper.update(DELETE_SQL, entity.getId());
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public TypeOfService selectById(String id) {
        List<TypeOfService> typesOfService = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (typesOfService.isEmpty()) {
            return null;
        }
        return typesOfService.get(0);
    }

    @Override
    public List<TypeOfService> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<TypeOfService> selectBySql(String sql, Object... args) {
        List<TypeOfService> typesOfService = new ArrayList<>();
        try (ResultSet resultSet = jdbcHelper.query(sql, args)) {
            while (resultSet.next()) {
                TypeOfService typeOfService = new TypeOfService();
                typeOfService.setId(resultSet.getString("MaLDV"));
                typeOfService.setName(resultSet.getString("TenDV"));
                typesOfService.add(typeOfService);
            }
            return typesOfService;
        } catch (SQLException ex) {
            Logger.getLogger(TypeOfServiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
