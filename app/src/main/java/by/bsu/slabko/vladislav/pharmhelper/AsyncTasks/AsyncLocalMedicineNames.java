package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;


import android.os.AsyncTask;

import java.util.ArrayList;

import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.MedecinesController;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.tables.Medecines;
import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesProvider;

public class AsyncLocalMedicineNames extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        MedecinesController controller = new MedecinesController();
        LocalMedicineNamesProvider provider =
                LocalMedicineNamesProvider.getInstance();
        ArrayList<Medecines> list = (ArrayList<Medecines>) controller.getAll();
        provider.addItemsToDatabase(list);
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
