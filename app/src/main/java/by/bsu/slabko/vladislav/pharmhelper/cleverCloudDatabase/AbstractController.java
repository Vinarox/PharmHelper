package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncGetDataFromDB;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public  class AbstractController {
    private Connection connection;
    public AbstractController(String item_name) {
        connection = Constants.connection;
        AsyncGetDataFromDB async = new AsyncGetDataFromDB();
        async.execute(item_name);
        try {
            async.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
