package by.bsu.slabko.vladislav.pharmhelper.fragment.home.adaptors;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.zip.Inflater;

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

        if (convertView == null) {
            convertView = mBoxes[position];
        }
        return (convertView);
    }

    @Override
    public int getCount() {
        return mBoxes.length;
    }

    // возвращает содержимое выделенного элемента списка
    public View getItem(int position) {
        return mBoxes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
