package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.objects.SearchItem;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class SimpleExpandableListAdapter extends BaseExpandableListAdapter {
    public int pos;
    private Context mContext;
    private LayoutInflater ltInflater;

    public SimpleExpandableListAdapter(Context c, LayoutInflater inflater){
        this.mContext = c;
        this.ltInflater = inflater;
        this.pos = 0;
    }
    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.d("Check", "55555555555");
        convertView = ltInflater.inflate(R.layout.three_line_list_avatar, null, false);

        ImageView avatar = convertView.findViewById(R.id.avatar);
        TextView app_name = convertView.findViewById(R.id.app_name);
        TextView app_name1 = convertView.findViewById(R.id.app_name1);
        TextView app_name2 = convertView.findViewById(R.id.app_name2);
        TextView app_name3 = convertView.findViewById(R.id.full_price);

        avatar.setImageResource(R.drawable.button_circle);

        app_name.setText(
                Constants.searchRes.get(pos).pharmName
        );
        app_name.setTextColor(mContext.getResources().getColor(R.color.green));
        app_name1.setText(Constants.searchRes.get(pos).district);
        app_name2.setText(Constants.searchRes.get(pos).phone);
        app_name2.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        app_name3.setText(String.valueOf(Constants.searchRes.get(pos).fullPrice));
        app_name3.setTextColor(mContext.getResources().getColor(R.color.red));
        Log.d("Check", "6666666");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("Check", "77777777");
        LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.search_line, null);
        Log.d("Check", "8888888");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
