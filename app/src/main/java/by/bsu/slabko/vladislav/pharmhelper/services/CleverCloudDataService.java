package by.bsu.slabko.vladislav.pharmhelper.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.CitiesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.ManufacturersController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MedecinesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MinskInfoController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.PharmaciesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Manufacturers;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.MinskInfo;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Pharmacies;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesProvider;

public class CleverCloudDataService extends Service {
        private ExecutorService es;

    public CleverCloudDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RunSearch runSearch = new RunSearch("",5);
        runSearch.run();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




    class RunSearch implements Runnable {
        private CitiesController citiesController;
        private ManufacturersController manufacturersController;
        private MedecinesController medecinesController;
        private MinskInfoController minskInfoController;
        private PharmaciesController pharmaciesController;

        private int startId;
        private String medecineName;

        public RunSearch(String medecineName, int startId) {
            this.startId = startId;
            this.medecineName = medecineName;

            this.citiesController = new CitiesController("");
            this.manufacturersController = new ManufacturersController();
            this.medecinesController = new MedecinesController();
            this.minskInfoController = new MinskInfoController();
            this.pharmaciesController = new PharmaciesController();
        }

        public void run() {
            /*Medecines medecines = medecinesController.getEntityByString(medecineName);
            if(medecines != null) {
                ArrayList<MinskInfo> minskInfos =
                        (ArrayList<MinskInfo>) minskInfoController.getEntityById(medecines.getIdMedicine());
                addToSearchRes(medecines.getMedecine(), minskInfos, Constants.searchRes.size());
            }
            stop();*/
            MedecinesController controller = new MedecinesController();
            LocalMedicineNamesProvider provider =
                    LocalMedicineNamesProvider.getInstance();
            ArrayList<Medecines> list = (ArrayList<Medecines>) controller.getAll();
            provider.addItemsToDatabase(list);
            stop();
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


        void stop() {
            stopSelf(startId);
        }
    }
}
