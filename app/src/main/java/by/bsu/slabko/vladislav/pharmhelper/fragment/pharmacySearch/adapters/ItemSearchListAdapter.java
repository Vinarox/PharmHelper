package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.ConsoleHandler;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SimpleExpandableListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.cleverCloudDatabase.AbstractController;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;


public class ItemSearchListAdapter extends RecyclerView.Adapter<ItemSearchListAdapter.MyViewHolder> {
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
    public ItemSearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ltInflater.inflate(R.layout.search_line, null, false);
        Log.d("Adapter", "!!!  " + String.valueOf(viewType));
        //SearchLine line = Constants.lines.get(viewType);
        MyViewHolder vh = new MyViewHolder(view, null);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("Adapter", String.valueOf(position));
        final int pos = position;
        final SearchLine line = Constants.lines.get(pos);
        holder.view.setClickable(true);
        final Button but = holder.view.findViewById(R.id.acces_button);
        final EditText edit = holder.view.findViewById(R.id.input);
        but.setClickable(true);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edit.getText().toString().trim();
                if (line.isGreen) {
                    if(str.length() > 0) {
                        callSearch(str);
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
        });
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

    public void callSearch(String query) {
        query = query.trim();
        //Constants.searchRes.clear();
        new AbstractController(query);
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


}
