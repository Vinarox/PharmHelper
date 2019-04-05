package by.bsu.slabko.vladislav.pharmhelper.fragment.userList;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.image.ImageProvider;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class UserListFragment extends Fragment {
    private MapView mapview;
    private UserLocationLayer userLocationLayer;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);
        MapKitFactory.initialize(HomeActivity.homeContext);


        View v = inflater.inflate(R.layout.fragment_user_list, container, false);

        mapview = (MapView)v.findViewById(R.id.my_mapview2);
        mapview.getMap().getMapObjects().addPlacemark(
                Constants.loacotionPoint,
                ImageProvider.fromResource(HomeActivity.homeContext, R.drawable.ic_add_location)
        );

        mapview.getMap().move(
                new CameraPosition(Constants.loacotionPoint, 18.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);


        userLocationLayer = mapview.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);

        return v;
    }


}