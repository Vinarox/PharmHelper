package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class AsyncAppsInfo extends AsyncTask<Context, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected String doInBackground(Context... f_cont) {
        long count;
        downloadFile(f_cont[0], Environment
                .getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString(), Constants.FIREBASE_DATA_URL);

        return "wow";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }



    public long downloadFile(Context context, String destinationDirectory, String url) {

        Log.d("Destination", destinationDirectory);
        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(
                context.getApplicationContext(),
                destinationDirectory,
                Constants.DATA_FILE_NAME
        );
        Constants.PHARM_DATA_PATH = destinationDirectory + "/" + Constants.DATA_FILE_NAME;
        return downloadmanager.enqueue(request);
    }


    private void downloadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://pharm-222921.appspot.com/");
        StorageReference  islandRef = storageRef.child("SmallIDMedicine_utf-8.csv");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "file_name");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"SmallIDMedicine_utf-8.csv");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }
}
