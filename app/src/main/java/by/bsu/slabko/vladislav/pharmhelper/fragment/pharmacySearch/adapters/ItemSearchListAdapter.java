package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.slabko.vladislav.pharmhelper.AsyncTasks.AsyncGetDataFromDB;
import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.SuggestionAdapter;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SimpleExpandableListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.controllers.CitiesController;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;
import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesEntity;
import by.bsu.slabko.vladislav.pharmhelper.localDBofMedicineNames.LocalMedicineNamesProvider;
import by.bsu.slabko.vladislav.pharmhelper.services.CleverCloudDataService;


public class ItemSearchListAdapter extends RecyclerView.Adapter<ItemSearchListAdapter.MyViewHolder> {
    SuggestionAdapter<String> adapter;
    List<LocalMedicineNamesEntity>[] items;
    List<String> items_str;

    MyButtonClick myButtonClick;

    Button but;
    AutoCompleteTextView edit;
    SearchLine line;

    private Context mContext;
    private LayoutInflater ltInflater;
    private MenuInflater menuInflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public SimpleExpandableListAdapter adapter;
        public MyViewHolder(View v, SimpleExpandableListAdapter adapter) {
            super(v);
            view = v;
            this.adapter = adapter;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemSearchListAdapter(Context c, LayoutInflater ltInflater, MenuInflater menuInflater) {
        this.mContext = c;
        this.ltInflater = ltInflater;
        this.menuInflater = menuInflater;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ltInflater.inflate(R.layout.search_line, null, false);
        //SearchLine line = Constants.lines.get(viewType);
        MyViewHolder vh = new MyViewHolder(view, null);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Log.d("Adapter", String.valueOf(position));
        final int pos = position;
        myButtonClick = new MyButtonClick();
        line = Constants.lines.get(pos);
        holder.view.setClickable(true);
        but = holder.view.findViewById(R.id.acces_button);
        edit = holder.view.findViewById(R.id.input);
        items = new List[]{new ArrayList<>()};
        LocalMedicineNamesProvider provider = LocalMedicineNamesProvider.getInstance();
        items[0] = provider.getAll();
        items_str = itemsToString(items[0]);
        edit.setAdapter(new ArrayAdapter<>(HomeActivity.homeContext,
                android.R.layout.simple_dropdown_item_1line, items_str));
        edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //edit.setText(items[0].get(position).medicine);
                //myButtonClick.setID(String.valueOf(items[0].get(position).med_id));
                but.callOnClick();
                return;
            }
        });
        but.setClickable(true);

        but.setOnClickListener(myButtonClick);
        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    but.callOnClick();
                    return true;
                }
                return false;
            }
        });
        /*EditText edit = holder.view.findViewById(R.id.input);
        Button acces = holder.view.findViewById(R.id.acces_button);
        line.setViews(HomeActivity.homeContext, edit, acces);*/
    }


    @Override
    public int getItemCount() {
        return Constants.lines.size();
    }

    public void callSearch(String query, String id) {
        query = query.trim();
        //Constants.searchRes.clear();
        //HomeActivity.homeContext.startService(new Intent(HomeActivity.homeContext, CleverCloudDataService.class)
          //      .putExtra("name", query));
        AsyncGetDataFromDB createDatabase = new AsyncGetDataFromDB();
        if(id == null)
            createDatabase.execute(query, null);
        else
            createDatabase.execute(query, id);
        try {
            createDatabase.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyOnLongClickListener implements View.OnLongClickListener {
        private int pos;
        public MyOnLongClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public boolean onLongClick(View v) {
            //v.getVerticalScrollbarPosition();
            Snackbar snackbar = Snackbar.make(v, "Delete item?", Snackbar.LENGTH_LONG)
                    .setAction("Да", new SnackbarOnClickListener(pos))
                    .setDuration(5000);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.argb(255, 0,215,208));
            snackbar.show();
            return false;
        }
    }

    class SnackbarOnClickListener implements View.OnClickListener {
        private int pos;
        public SnackbarOnClickListener(int pos){
            this.pos = pos;
        }
        @Override
        public void onClick(View view) {
            ItemSearchListAdapter.this.notifyItemRemoved(pos);
            //MyListAdapter.this.notifyItemInserted(0);
        }
    }

    private List<String> itemsToString(List<LocalMedicineNamesEntity> list){
        List<String> result = new ArrayList<>();
        for(LocalMedicineNamesEntity obj: list){
            result.add(obj.medicine);
        }
        return result;
    }

    class MyButtonClick implements View.OnClickListener {
        private String id = null;

        public void setID(String id){
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            String str = edit.getText().toString().trim();
            if (line.isGreen) {
                if(str.length() > 0) {
                    callSearch(str, null);
                    line.enteredString = str;
                    line.isGreen = false;
                    edit.setText(line.enteredString);
                    edit.setEnabled(false);
                    line.setViews(HomeActivity.homeContext, but);
                    PharmacySearchFragment.setPrices();
                    PharmacySearchFragment.addSearchLine(true);
                }
            } else {
                PharmacySearchFragment.deleteSearchLine(line.object);
            }
        }
    }

}
