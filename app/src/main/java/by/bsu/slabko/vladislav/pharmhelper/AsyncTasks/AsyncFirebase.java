package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.searchResults.SearchInfo;

public class AsyncFirebase extends AsyncTask<String, Void, Void> {
    private DatabaseReference myRef;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected Void doInBackground(final String... ID) {
        Log.d("AsyncFirebase", String.valueOf(ID[0]));
        FirebaseApp.initializeApp(HomeActivity.homeContext);
        myRef = FirebaseDatabase.getInstance().getReference().child("pharm-222921");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // <List<HashMap<String, List<String>>>>
                GenericTypeIndicator<List<HashMap<String, List<String>>>> t =
                        new GenericTypeIndicator <List<HashMap<String, List<String>>>>(){};
                //List<HashMap<String, List<String>>> res = dataSnapshot.child(String.valueOf(ID[0])).getValue(t);
                long num = dataSnapshot.getChildrenCount();//.getValue(t);
                SearchInfo searchInfo = SearchInfo.getInstance();
                //searchInfo.addNewInfo(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }



}
