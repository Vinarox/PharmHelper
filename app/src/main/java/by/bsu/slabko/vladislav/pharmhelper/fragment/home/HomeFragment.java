package by.bsu.slabko.vladislav.pharmhelper.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.fragment.home.adaptors.GridAdapter;


public class HomeFragment extends Fragment {
    private GridAdapter mGridAdapter;
    private static Context mContext;

    public HomeFragment() {
        // Required empty public constructor
    }



    public static HomeFragment newInstance(Context c) {
        HomeFragment.mContext = c;
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        GridView gvMain = (GridView) v.findViewById(R.id.grid_slot);
        gvMain.setNumColumns(2);
        View[] boxes = {inflater.inflate(R.layout.infobox, null, false),
                inflater.inflate(R.layout.infobox, null, false)};
        TextView info_text = boxes[0].findViewById(R.id.info_text);
        info_text.setText("Телефоны\nаптек");
        info_text = boxes[1].findViewById(R.id.info_text);
        info_text.setText("Обратная\nсвязь");
        mContext = HomeActivity.homeContext;
        mGridAdapter = new GridAdapter(mContext, boxes);
        gvMain.setAdapter(mGridAdapter);

        LinearLayout userBox = v.findViewById(R.id.user_cabinet);
        userBox.addView(inflater.inflate(R.layout.infobox, container, false));
        return v;
    }

}
