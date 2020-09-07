package com.example.smartparkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

import android.view.KeyEvent;
import android.widget.Toast;

// classes needed to initialize map
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

// classes needed to add the location component
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;

import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

public class Navigation extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {
// variables for adding location layer
private MapView mapView;
private MapboxMap mapboxMap;
// variables for adding location layer
private PermissionsManager permissionsManager;
private LocationComponent locationComponent;
// variables for calculating and drawing a route
private DirectionsRoute currentRoute;
private static final String TAG = "DirectionsActivity";
private NavigationMapRoute navigationMapRoute;
// variables needed to initialize navigation
private Button button;
        SharedPreferences readData;
        String MapBoxGetnearBanklatitude;
        String MapBoxGetnearBanklogitude;
        String MapBoxGetnearResturantlatitude;
        String MapBoxGetnearResturantlogitude;
        Point destinationPoint;
        String readDestinationlatitude;
        String readDestinationlongitude;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_navigation);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Intent intent = getIntent();
        String place_name = intent.getStringExtra("Parent_Activity");
        readData = getSharedPreferences("PinParking", MODE_PRIVATE);
        if (place_name.equals("ParkingPin")) {

        readDestinationlatitude = readData.getString("Parkinglatitude", "");
        readDestinationlongitude = readData.getString("Parkinglongitude", "");
        } else
        if (place_name.equals("Booking")) {
        String a = intent.getStringExtra("longi");
        String b = intent.getStringExtra("lati");
        readDestinationlatitude = a;
        readDestinationlongitude = b;
                Toast.makeText(Navigation.this,""+a,
                        Toast.LENGTH_LONG).show();
        Toast.makeText(this, b, Toast.LENGTH_LONG).show();

        }

        }

public void getnavigation(View view) {
        destinationPoint = Point.fromLngLat(Double.parseDouble(readDestinationlongitude), Double.parseDouble(readDestinationlatitude));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
        }
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        getRoute(originPoint, destinationPoint);

        }
@Override
public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
@Override
public void onStyleLoaded(@NonNull Style style) {
        enableLocationComponent(style);

        addDestinationIconSymbolLayer(style);

        mapboxMap.addOnMapClickListener(Navigation.this);

        }
        });

        }

private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
        BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
        iconImage("destination-icon-id"),
        iconAllowOverlap(true),
        iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
        }

@SuppressWarnings( {"MissingPermission"})



@Override
public boolean onMapClick(@NonNull LatLng point) {




        boolean simulateRoute = true;
        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                .directionsRoute(currentRoute)
                .shouldSimulateRoute(simulateRoute)
                .build();
        // Call this method with Context from within an Activity
        NavigationLauncher.startNavigation(Navigation.this, options);

        return true;
}

private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
        .accessToken(Mapbox.getAccessToken())
        .origin(origin)
        .profile(DirectionsCriteria.PROFILE_DRIVING_TRAFFIC)
        .destination(destination)
        .build()
        .getRoute(new Callback<DirectionsResponse>() {
@Override
public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        // You can get the generic HTTP info about the response
        Log.d(TAG, "Response code: " + response.code());
        if (response.body() == null) {
        Log.e(TAG, "No routes found, make sure you set the right user and access token.");
        return;
        } else if (response.body().routes().size() < 1) {
        Log.e(TAG, "No routes found");
        return;
        }

        currentRoute = response.body().routes().get(0);

        // Draw the route on the map
        if (navigationMapRoute != null) {
        navigationMapRoute.removeRoute();
        } else {
        navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
        }
        navigationMapRoute.addRoute(currentRoute);
        }

@Override
public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
        Log.e(TAG, "Error: " + throwable.getMessage());
        }
        });
        }

@SuppressWarnings( {"MissingPermission"})
private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
        // Activate the MapboxMap LocationComponent to show user location
        // Adding in LocationComponentOptions is also an optional parameter
        locationComponent = mapboxMap.getLocationComponent();
        locationComponent.activateLocationComponent(this, loadedMapStyle);
        locationComponent.setLocationComponentEnabled(true);
        // Set the component's camera mode
        locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
        permissionsManager = new PermissionsManager(this);
        permissionsManager.requestLocationPermissions(this);
        }
        }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

@Override
public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
        }

@Override
public void onPermissionResult(boolean granted) {
        if (granted) {
        enableLocationComponent(mapboxMap.getStyle());
        } else {
        Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        finish();
        }
        }

@Override
protected void onStart() {
        super.onStart();
        mapView.onStart();
        }

@Override
protected void onResume() {
        super.onResume();
        mapView.onResume();
        }

@Override
protected void onPause() {
        super.onPause();
        mapView.onPause();
        }

@Override
protected void onStop() {
        super.onStop();
        mapView.onStop();
        }

@Override
protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        }

@Override
protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        }

@Override
public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        }
        }