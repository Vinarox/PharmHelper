package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.CloudAbstractController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Manufacturers;

public class ManufacturersController extends CloudAbstractController<Manufacturers, String> {

    public ManufacturersController(){}

    @Override
    public List<Manufacturers> getAll() {
        return null;
    }

    @Override
    public Manufacturers getUniqueEntityById(int id_manuf) {
        String command = "select * from bjxwurblbnm1couxsina.MANUFACTURERS where ID_MANUF = ?";
        try {
            PreparedStatement preparedStatement = getPrepareStatement(command);
            preparedStatement.setInt(1, id_manuf);
            ResultSet items = preparedStatement.executeQuery();
            if (items.next()) {
                return new Manufacturers(id_manuf, items.getString("manufacturer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Manufacturers> getEntityById(int id) {
        return null;
    }

    @Override
    public Manufacturers getEntityByString(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
