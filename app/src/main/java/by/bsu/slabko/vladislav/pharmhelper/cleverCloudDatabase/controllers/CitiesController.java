package by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncGetDataFromDB;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.CloudAbstractController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Cities;

public class CitiesController extends CloudAbstractController<Cities, String> {
    public CitiesController(String item_name) {
        /*AsyncGetDataFromDB async = new AsyncGetDataFromDB();
        async.execute(item_name);
        try {
            async.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public List<Cities> getAll() {
        return null;
    }

    @Override
    public Cities getUniqueEntityById(int id) {
        return null;
    }

    @Override
    public List<Cities>  getEntityById(int id) {
        return null;
    }

    @Override
    public Cities getEntityByString(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
