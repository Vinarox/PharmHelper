package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class AsyncGetDataFromDB extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... name) {
        try {
            findInfo(name[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = Constants.connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    public void findInfo(String name) throws SQLException {
        String command = "select * from bjxwurblbnm1couxsina.MEDECINES where medecine like ?";
        PreparedStatement preparedStatement = getPrepareStatement(command);
        preparedStatement.setString(1, "%" + name + "%");
        //preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        int id_med;
        int id_pharm;
        int id_manuf;
        int quantity = 0;
        float price = .0f;
        if (resultSet.next()) {
            id_med = resultSet.getInt("ID_MED");
        }
        else return;
        command = "select * from bjxwurblbnm1couxsina.MINSK_INFO where ID_MED = ?";
        preparedStatement = getPrepareStatement(command);
        preparedStatement.setInt(1, id_med);
        resultSet = preparedStatement.executeQuery();
        if(Constants.searchRes.size() == 0){
            String pharmName = "";
            String address = "";
            String district = "";
            String phone = "";
            float longitude = .0f;
            float latitude = .0f;

            int counter = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
                price = resultSet.getFloat("price");
                id_pharm = resultSet.getInt("ID_PHARMACY");
                id_manuf = resultSet.getInt("ID_MANUF");

////////////
                String manufacturer = "none";

                command = "select * from bjxwurblbnm1couxsina.MANUFACTURERS where ID_MANUF = ?";
                preparedStatement = getPrepareStatement(command);
                preparedStatement.setInt(1, id_manuf);
                ResultSet items = preparedStatement.executeQuery();
                if (items.next()) {
                    manufacturer = items.getString("manufacturer");
                }


                /////////////////////////////
                command = "select * from bjxwurblbnm1couxsina.PHARMACIES where ID_PHARMACY = ?";
                preparedStatement = getPrepareStatement(command);
                preparedStatement.setInt(1, id_pharm);
                ResultSet resultSet1 = preparedStatement.executeQuery();
                if(resultSet1.next()) {
                    pharmName = resultSet1.getString("pharmacy_name");
                    address = resultSet1.getString("address");
                    district = resultSet1.getString("district");
                    phone = resultSet1.getString("phone");
                    latitude = resultSet1.getFloat("latitude");
                    longitude = resultSet1.getFloat("longitude");

                    Constants.searchRes.add(new SearchItem(pharmName, address, district, phone,
                            latitude, longitude));
                    Constants.searchRes.get(counter).addInfo(name, price, quantity, manufacturer);
                    counter++;
                }
                /////////////////////////////


            }

        }
            else {
            int counter = 0;
            while (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
                price = resultSet.getFloat("price");
                id_manuf = resultSet.getInt("ID_MANUF");

////////////
                String manufacturer = "none";

                command = "select * from bjxwurblbnm1couxsina.MANUFACTURERS where ID_MANUF = ?";
                preparedStatement = getPrepareStatement(command);
                preparedStatement.setInt(1, id_manuf);
                ResultSet items = preparedStatement.executeQuery();
                if (items.next()) {
                    manufacturer = items.getString("manufacturer");
                }
                if (counter < Constants.searchRes.size()) {
                    Constants.searchRes.get(counter).addInfo(name, price, quantity, manufacturer);
                }
                counter++;
            }
        }

    }

}
