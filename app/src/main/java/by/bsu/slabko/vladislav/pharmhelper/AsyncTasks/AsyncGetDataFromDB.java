package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.content.Intent;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.CitiesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.ManufacturersController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MedecinesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MinskInfoController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.PharmaciesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.AllDbInfo;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Manufacturers;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.MinskInfo;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Pharmacies;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class AsyncGetDataFromDB extends AsyncTask<String, Void, Void> {
    private CitiesController citiesController;
    private ManufacturersController manufacturersController;
    private MedecinesController medecinesController;
    private MinskInfoController minskInfoController;
    private PharmaciesController pharmaciesController;

    @Override
    protected Void doInBackground(String... name) {
        /*Medecines medecines;
        if(name.length == 2){
            medecines = new Medecines(Integer.parseInt(name[1]), name[0]);
        }
        else {
            medecines = medecinesController.getEntityByString(name[0]);
        }
        if(medecines != null) {
            ArrayList<MinskInfo> minskInfos =
                    (ArrayList<MinskInfo>) minskInfoController.getEntityById(medecines.getIdMedicine());
            addToSearchRes(medecines.getMedecine(), minskInfos, Constants.searchRes.size());
        }*/
        getJoinedInfo(name[0], name[1], Constants.searchRes.size());
        return null;
    }

    private void addToSearchRes(String medecineName, List<MinskInfo> list, int isFirstAdd){
        Pharmacies pharmacies;
        Manufacturers manufacturers;
        for(MinskInfo obj: list){
            pharmacies = pharmaciesController.getUniqueEntityById(obj.getIdPharmacy());
            manufacturers = manufacturersController.getUniqueEntityById(obj.getIdManuf());//реализовать
            if(pharmacies != null && manufacturers != null)
                addItemToList(medecineName, obj, pharmacies, manufacturers, isFirstAdd);
        }
    }

    private void getJoinedInfo(String medicineName, String id_med, int isFirstAdd) {
        List<AllDbInfo> list =  medecinesController.getAllJoinedInfo(medicineName, id_med);
        for(AllDbInfo obj: list) {
            addJoinedItemsToList(medicineName, obj, isFirstAdd);
        }
    }

    private void addJoinedItemsToList(String medicineName, AllDbInfo allDbInfo, int isFirstAdd){
        if(isFirstAdd == 0){
            Constants.searchRes.add(new SearchItem(
                    allDbInfo.getIdPharmacy(),
                    allDbInfo.getPharmacyName(),
                    allDbInfo.getAddress(),
                    allDbInfo.getDistrict(),
                    allDbInfo.getPhone(),
                    allDbInfo.getLatitude(),
                    allDbInfo.getLongitude()));
            Constants.searchRes.get(Constants.searchRes.size() - 1).addInfo(
                    medicineName,
                    allDbInfo.getPrice(),
                    allDbInfo.getQuantity(),
                    allDbInfo.getManufacturers()
            );
        }
        else {
            int index = findIndexToAddMedecine(allDbInfo.getIdPharmacy());
            if(index != -1) {
                Constants.searchRes.get(index).addInfo(
                        medicineName,
                        allDbInfo.getPrice(),
                        allDbInfo.getQuantity(),
                        allDbInfo.getManufacturers()
                );
            }
        }
    }

    private void addItemToList(String medecineName, MinskInfo minskInfo, Pharmacies pharmacies,
                               Manufacturers manufacturers, int isFirstAdd){
        if(isFirstAdd == 0){
            Constants.searchRes.add(new SearchItem(
                    minskInfo.getIdPharmacy(),
                    pharmacies.getPharmacyName(),
                    pharmacies.getAddress(),
                    pharmacies.getDistrict(),
                    pharmacies.getPhone(),
                    pharmacies.getLatitude(),
                    pharmacies.getLongitude()));
            Constants.searchRes.get(Constants.searchRes.size() - 1).addInfo(
                    medecineName,
                    minskInfo.getPrice(),
                    minskInfo.getQuantity(),
                    manufacturers.getManufacturers()
            );
        }
        else {
            int index = findIndexToAddMedecine(minskInfo.getIdPharmacy());
            if(index != -1) {
                Constants.searchRes.get(index).addInfo(
                        medecineName,
                        minskInfo.getPrice(),
                        minskInfo.getQuantity(),
                        manufacturers.getManufacturers()
                );
            }
        }
    }


    private int findIndexToAddMedecine(int idPharm){
        return Constants.searchRes.indexOf(new SearchItem(idPharm));
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.citiesController = new CitiesController("");
        this.manufacturersController = new ManufacturersController();
        this.medecinesController = new MedecinesController();
        this.minskInfoController = new MinskInfoController();
        this.pharmaciesController = new PharmaciesController();
    }

    @Override
    protected void onPostExecute(Void result) {
        int i = 0;
        List<SearchItem> list = new ArrayList<>();
        for(SearchItem obj: Constants.searchRes){
            list.add(obj);
            if(i++ == 18)
                break;
        }
        Constants.searchRes = new ArrayList<>(list);
        super.onPostExecute(result);
    }
}
