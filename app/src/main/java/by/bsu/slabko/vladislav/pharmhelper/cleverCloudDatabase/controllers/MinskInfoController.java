package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers;

import com.yandex.mapkit.RequestPoint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.CloudAbstractController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.MinskInfo;

public class MinskInfoController extends CloudAbstractController<MinskInfo, String> {

    public MinskInfoController(){}

    @Override
    public List<MinskInfo> getAll() {
        return null;
    }

    @Override
    public MinskInfo getUniqueEntityById(int id) {
        return null;
    }

    @Override
    public List<MinskInfo> getEntityById(int id_med) {
        List<MinskInfo> list = new ArrayList<>();
        String command = "select * from bjxwurblbnm1couxsina.MINSK_INFO where ID_MED = ?";
        try {
            PreparedStatement preparedStatement = getPrepareStatement(command);
            preparedStatement.setInt(1, id_med);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new MinskInfo(
                        resultSet.getInt("ID_MED"),
                        resultSet.getInt("ID_PHARMACY"),
                        resultSet.getInt("ID_CITY"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("ID_MANUF")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public MinskInfo getEntityByString(String name) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
