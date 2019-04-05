package by.bsu.slabko.vladislav.pharmhelper.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.ResultListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;

public class ReservAdapter extends RecyclerView.Adapter<ItemSearchListAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater ltInflater;
    private MenuInflater menuInflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReservAdapter(Context c, LayoutInflater ltInflater) {
        this.mContext = c;
        this.ltInflater = ltInflater;
        this.menuInflater = menuInflater;
    }

    @Override
    public ItemSearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ltInflater.inflate(R.layout.three_line_list_avatar, null, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println();

            }
        });
        view.setLongClickable(true);
        Log.d("Adapter", "11111111111111111111111111");
        ItemSearchListAdapter.MyViewHolder vh = new ItemSearchListAdapter.MyViewHolder(view, null);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ItemSearchListAdapter.MyViewHolder holder, int position) {
        Log.d("Adapter", String.valueOf(position));
        final int pos = position;
        SearchItem appInfo = SearchItem.getInstance();
        //holder.view.setOnLongClickListener(new MyOnLongClickListener(position));
        ImageView avatar = holder.view.findViewById(R.id.avatar);
        TextView app_name = holder.view.findViewById(R.id.app_name);
        TextView app_name1 = holder.view.findViewById(R.id.app_name1);
        TextView app_name2 = holder.view.findViewById(R.id.app_name2);

        avatar.setImageResource(R.drawable.button_circle);

        app_name.setText(
                Constants.searchRes.get(pos).pharmName
        );
        app_name.setTextColor(mContext.getResources().getColor(R.color.green));
        app_name1.setText(Constants.searchRes.get(pos).pharmName);
        app_name2.setText(String.valueOf(Constants.searchRes.get(pos).fullPrice));
        app_name2.setTextColor(mContext.getResources().getColor(R.color.red));
    }


    @Override
    public int getItemCount() {
        return Constants.searchRes.size();
    }



    class MyOnLongClickListener implements View.OnLongClickListener {
        private int pos;
        public MyOnLongClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
