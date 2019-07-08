package by.bsu.slabko.vladislav.pharmhelper.AsyncTasks;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.transport.masstransit.MasstransitOptions;
import com.yandex.mapkit.transport.masstransit.MasstransitRouter;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;


public class AsyncCreateRoute extends AsyncTask<String, Void, Void>
        implements DrivingSession.DrivingRouteListener{
    Point startPoint;
    Point endPoint;
    List<Point> routePoints;

    @Override
    protected Void doInBackground(String... points) {
        if(points.length == 2){
            if(points[0].equals("current")){
//                MapKitFactory.initialize(HomeActivity.homeContext);
                MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new LocationListener() {
                    @Override
                    public void onLocationUpdated(@NonNull Location location) {
                        startPoint = location.getPosition();
                    }
                    @Override
                    public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {}
                });
            }
            else {
                startPoint = getCoordinatesByAddress(points[0]);
            }
            endPoint = getCoordinatesByAddress(points[1]);

            if(startPoint != null && endPoint != null) {
                getRoutePoints(startPoint, endPoint);
                int counter = 0;
                while (counter < 10000000){
                    if(routePoints.size() > 0)
                        break;
                }
                if(routePoints.size() > 0) {
                    findNearestPharmacies();
                }
            }
        }
        return null;
    }

    private Point getCoordinatesByAddress(String address) {
        Point result = null;
        try {
            Geocoder geocoder = new Geocoder(HomeActivity.homeContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(address, 1,
                    Constants.LOW_LEFT_LAT, Constants.LOW_LEFT_LON,
                    Constants.UP_RIGHT_LAT, Constants.UP_RIGHT_LON);
            if(addresses.size() > 0)
                result = new Point(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void getRoutePoints(Point startPoint, Point endPoint){
        List<RequestPoint> points = new ArrayList<RequestPoint>();
        points.add(new RequestPoint(startPoint, RequestPointType.WAYPOINT, null));
        points.add(new RequestPoint(endPoint, RequestPointType.WAYPOINT, null));
        DrivingOptions drivingOptions = new DrivingOptions();
        DrivingRouter drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        drivingRouter.requestRoutes(points, drivingOptions, this);
    }

    private void findNearestPharmacies(){
        //TODO algorithm of searching for nearest pharmacies
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> routes) {
        if (routes.size() > 0) {
            Polyline polyline = routes.get(0).getGeometry();
            routePoints = polyline.getPoints();
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {

    }
}