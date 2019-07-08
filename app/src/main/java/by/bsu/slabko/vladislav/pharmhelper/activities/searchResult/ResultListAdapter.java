package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.singlePharmacyResults.SinglePharmacyResults;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.PharmacySearchFragment;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;

public class ResultListAdapter  extends RecyclerView.Adapter<ItemSearchListAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater ltInflater;

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
    public ResultListAdapter(Context c, LayoutInflater ltInflater) {
        this.mContext = c;
        this.ltInflater = ltInflater;
    }

    @Override
    public ItemSearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ltInflater.inflate(R.layout.three_line_list_avatar, null, false);
        ItemSearchListAdapter.MyViewHolder vh = new ItemSearchListAdapter.MyViewHolder(view, null);

       /* View view = ltInflater.inflate(R.layout.expanded_list, null, false);

        //view.setLongClickable(true);
        Log.d("Check", "1111111111111");

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(mContext, ltInflater);
        Constants.adapters.add(adapter);

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);

        ItemSearchListAdapter.MyViewHolder vh = new ItemSearchListAdapter.MyViewHolder(view, adapter);
        Log.d("Check", "22222222222");*/


        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ItemSearchListAdapter.MyViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PharmacySearchFragment.newIntent(position);
            }
        });
        ImageView avatar = holder.view.findViewById(R.id.avatar);
        TextView app_name = holder.view.findViewById(R.id.app_name);
        TextView app_name1 = holder.view.findViewById(R.id.app_name1);
        TextView app_name2 = holder.view.findViewById(R.id.app_name2);
        TextView app_name3 = holder.view.findViewById(R.id.full_price);

        avatar.setImageResource(R.drawable.button_circle);

        app_name.setText(
                Constants.searchRes.get(position).pharmName
        );
        app_name.setTextColor(mContext.getResources().getColor(R.color.green));
        app_name1.setText(Constants.searchRes.get(position).district);
        app_name2.setText(Constants.searchRes.get(position).phone);
        app_name2.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        String displayed = String.valueOf(Constants.searchRes.get(position).fullPrice) + "р.";
        app_name3.setText(displayed);
        app_name3.setTextColor(mContext.getResources().getColor(R.color.red));
        /*
        Log.d("Adapter", String.valueOf(position));
        Log.d("Check", "33333333333");
        holder.adapter.pos = position;
        holder.adapter.notifyDataSetChanged();
        Log.d("Check", "44444444444444");*/

    }


    @Override
    public int getItemCount() {
        return Constants.searchRes.size();
    }

}
