package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.CloudAbstractController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.AllDbInfo;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.MinskInfo;

public class MedecinesController extends CloudAbstractController<Medecines, String> {

    public MedecinesController(){}

    @Override
    public List<Medecines> getAll() {
        String command = "select * from bjxwurblbnm1couxsina.MEDECINES";
        List<Medecines> list = new ArrayList<>();
        try {
            PreparedStatement prSt = getPrepareStatement(command);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(new Medecines(
                        resultSet.getInt("ID_MED"),
                        resultSet.getString("medecine")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Medecines getUniqueEntityById(int id) {
        return null;
    }

    @Override
    public List<Medecines> getEntityById(int id) {
        return null;
    }

    @Override
    public Medecines getEntityByString(String name) {
        String command = "select * from bjxwurblbnm1couxsina.MEDECINES where medecine like ?";
        try {
            PreparedStatement prSt = getPrepareStatement(command);
            prSt.setString(1, "%" + name + "%");
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                return new Medecines(
                        resultSet.getInt("ID_MED"),
                        resultSet.getString("medecine")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Medecines> getSetOfEntitiesByString(String name) {
        String command = "select * from bjxwurblbnm1couxsina.MEDECINES where medecine like ?";
        List<Medecines> list = new ArrayList<>();
        try {
            PreparedStatement prSt = getPrepareStatement(command);
            prSt.setString(1, "%" + name + "%");
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(new Medecines(
                        resultSet.getInt("ID_MED"),
                        resultSet.getString("medecine")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<AllDbInfo> getAllJoinedInfo(String name, String id){
        List<AllDbInfo> list = new ArrayList<>();
        String additional;
        if(id != null)
            additional = "bjxwurblbnm1couxsina.MEDECINES.ID_MED = " + id;
        else
            additional = "bjxwurblbnm1couxsina.MEDECINES.medecine like ?";
        String command = "select * from bjxwurblbnm1couxsina.MEDECINES " +
                "INNER JOIN bjxwurblbnm1couxsina.MINSK_INFO " +
                "ON bjxwurblbnm1couxsina.MEDECINES.ID_MED = bjxwurblbnm1couxsina.MINSK_INFO.ID_MED " +
                "INNER JOIN bjxwurblbnm1couxsina.PHARMACIES " +
                "ON bjxwurblbnm1couxsina.MINSK_INFO.ID_PHARMACY = bjxwurblbnm1couxsina.PHARMACIES.ID_PHARMACY " +
                "INNER JOIN bjxwurblbnm1couxsina.MANUFACTURERS " +
                "ON bjxwurblbnm1couxsina.MINSK_INFO.ID_MANUF = bjxwurblbnm1couxsina.MANUFACTURERS.ID_MANUF " +
                "WHERE " +
                additional;
        try {
            PreparedStatement prSt = getPrepareStatement(command);
            if(id == null)
                prSt.setString(1, name);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(new AllDbInfo(
                        resultSet.getInt("ID_MANUF"),
                        resultSet.getString("manufacturer"),
                        resultSet.getInt("ID_MED"),
                        resultSet.getString("medecine"),
                        resultSet.getInt("ID_PHARMACY"),
                        resultSet.getInt("ID_CITY"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("pharmacy_name"),
                        resultSet.getString("address"),
                        resultSet.getString("district"),
                        resultSet.getString("phone"),
                        resultSet.getDouble("longitude"),
                        resultSet.getDouble("latitude")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
