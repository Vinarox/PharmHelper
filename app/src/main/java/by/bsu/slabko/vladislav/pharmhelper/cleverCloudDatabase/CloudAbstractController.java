package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public abstract class CloudAbstractController<E, K> {
    private Connection connection;

    public CloudAbstractController() {
        connection = Constants.connection;
    }

    public abstract List<E> getAll();
    public abstract E getUniqueEntityById(int id);
    public abstract List<E> getEntityById(int id);
    public abstract E getEntityByString(String id);
    public abstract boolean delete(K id);

    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
