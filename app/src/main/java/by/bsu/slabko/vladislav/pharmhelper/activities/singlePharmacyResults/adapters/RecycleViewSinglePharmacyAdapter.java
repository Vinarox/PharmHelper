package by.bsu.slabko.vladislav.pharmhelper.activities.singlePharmacyResults.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class RecycleViewSinglePharmacyAdapter extends RecyclerView.Adapter<RecycleViewSinglePharmacyAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater ltInflater;
    private int index;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    public RecycleViewSinglePharmacyAdapter(Context c, LayoutInflater ltInflater, int index) {
        this.mContext = c;
        this.ltInflater = ltInflater;
        this.index = index;
    }

    @Override
    public RecycleViewSinglePharmacyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ltInflater.inflate(R.layout.two_line_list_avatar, null, false);
        RecycleViewSinglePharmacyAdapter.MyViewHolder vh = new RecycleViewSinglePharmacyAdapter.MyViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView app_name = holder.view.findViewById(R.id.name);
        TextView app_name1 = holder.view.findViewById(R.id.manuf);
        TextView app_name3 = holder.view.findViewById(R.id.single_price);


        app_name.setText(
                Constants.searchRes.get(index).expandedList.get(position).name
        );
        app_name.setTextColor(mContext.getResources().getColor(android.R.color.black));

        app_name1.setText(Constants.searchRes.get(index).expandedList.get(position).manuf);
        app_name1.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));

        app_name3.setText(String.valueOf(Constants.searchRes.get(index).expandedList.get(position).price));
        app_name3.setTextColor(mContext.getResources().getColor(R.color.red));
    }


    @Override
    public int getItemCount() {
        return Constants.searchRes.get(index).expandedList.size();
    }

}
