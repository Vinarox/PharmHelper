package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.bsu.slabko.vladislav.pharmhelper.PreStartSettings;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class AsyncDBConnection extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        if (Constants.connection == null) {
            Log.d("DBConnection", "started");
            try {
                Connection connection = null;

                String url = "jdbc:mysql://bjxwurblbnm1couxsina-mysql.services.clever-cloud.com:3306/bjxwurblbnm1couxsina";
                //String url = "mysql://ub5yetjavfja2lo7:SKaMbEziKjoEX0XPaNES@bjxwurblbnm1couxsina-mysql.services.clever-cloud.com:3306/bjxwurblbnm1couxsina";
                String name = "ub5yetjavfja2lo7";
                String password = "SKaMbEziKjoEX0XPaNES";
                Class.forName("com.mysql.jdbc.Driver");
                try {
                    connection = DriverManager.getConnection(url, name, password);
                    Constants.connection = connection;
                /*PreparedStatement preparedStatement = connection.prepareStatement("select * from bjxwurblbnm1couxsina.CITIES");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String str = resultSet.getString("CITY");
                    System.out.println();
                }*/

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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


}
