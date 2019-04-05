package by.bsu.slabko.vladislav.pharmhelper.fragment.home.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridAdapter  extends BaseAdapter {
    private static View[] mBoxes;

    Context mContext;

    // Конструктор
    public GridAdapter(Context context, View[] boxes) {
        GridAdapter.mBoxes = boxes;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView == null) {
            convertView = mBoxes[position];
       // }
        return (convertView);
    }

    @Override
    public int getCount() {
        return mBoxes.length;
    }

    // возвращает содержимое выделенного элемента списка
    public Object getItem(int position) {
        return mBoxes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
