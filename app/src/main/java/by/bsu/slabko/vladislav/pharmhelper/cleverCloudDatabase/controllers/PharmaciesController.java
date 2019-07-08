package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.CloudAbstractController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Pharmacies;

public class PharmaciesController extends CloudAbstractController<Pharmacies, String> {

    public PharmaciesController(){}

    @Override
    public List<Pharmacies> getAll() {
        return null;
    }

    @Override
    public Pharmacies getUniqueEntityById(int id_pharm) {
        String command = "select * from bjxwurblbnm1couxsina.PHARMACIES where ID_PHARMACY = ?";
        PreparedStatement preparedStatement = getPrepareStatement(command);
        try {
            preparedStatement.setInt(1, id_pharm);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            if(resultSet1.next()) {
                return new Pharmacies(
                        id_pharm,
                        resultSet1.getString("pharmacy_name"),
                        resultSet1.getString("address"),
                        resultSet1.getString("district"),
                        resultSet1.getString("phone"),
                        resultSet1.getDouble("latitude"),
                        resultSet1.getDouble("longitude")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public List<Pharmacies> getEntityById(int id) {
        return null;
    }

    @Override
    public Pharmacies getEntityByString(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
