package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.database.MyContentProvider;

public class AsyncCreateDatabase extends AsyncTask<File, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected Void doInBackground(File... file) {
        createDatabase(file[0]);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }


    private void createDatabase(File dir)  {
        Log.d("FIle", Environment
                .getExternalStorageDirectory().toString() + "/" + Constants.DATA_FILE_NAME);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(dir , "/storage/emulated/0/Download/" + Constants.DATA_FILE_NAME)));

            MyContentProvider db = MyContentProvider.getInstance();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                db.addItemToDatabase(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}

