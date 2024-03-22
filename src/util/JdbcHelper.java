/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamn
 */
public class JdbcHelper {

    private static final SQLServerDataSource dataSource = new SQLServerDataSource();

    static {
        dataSource.setServerName("localhost");
        dataSource.setUser("sa");
        dataSource.setPassword("Bom474002");
        dataSource.setDatabaseName("KhachSan");
        dataSource.setEncrypt(false);
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLServerException ex) {
            ex.printStackTrace();
        } finally {
            return conn;
        }
    }

    public static PreparedStatement preparedStatement(String sql, Object... agrs) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps;
        if (sql.startsWith("{")) {
            ps = conn.prepareCall(sql);
        } else {
            ps = conn.prepareStatement(sql);
        }
        for (int i = 0; i < agrs.length; i++) {
            ps.setObject(i + 1, agrs[i]);
        }
        return ps;
    }

    public static ResultSet query(String sql, Object... agrs) throws SQLException {
        PreparedStatement ps = preparedStatement(sql, agrs);
        return ps.executeQuery();
    }
//    public static ResultSet query(String sql) throws SQLException {
//        PreparedStatement ps = preparedStatement(sql, agrs);
//        return ps.executeQuery();
//    }

    public static int update(String sql, Object... agrs) throws SQLException {
        PreparedStatement ps = preparedStatement(sql, agrs);
        try {
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
}
