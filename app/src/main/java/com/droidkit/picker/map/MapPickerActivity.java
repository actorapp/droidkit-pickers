package com.droidkit.picker.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.droidkit.file.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapPickerActivity extends Activity implements GoogleMap.OnMyLocationChangeListener, AdapterView.OnItemClickListener, GoogleMap.OnMapLongClickListener {

    private static final String LOG_TAG = "MapPickerActivity";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng geoData;

    View select;
    private Location currentLocation;
    private ListView list;
    private TextView status;
    private PlaceFetchingTask fetchingTask;
    private SearchView searchBox;
    private View fullSizeButton;
    private Marker currentPick;
    private View listHolder;
    private View defineMyLocationButton;
    private TextView accuranceView;
    private View pickCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(this);
        status = (TextView) findViewById(R.id.status);
        listHolder = findViewById(R.id.listNearbyHolder);
        accuranceView = (TextView) findViewById(R.id.accurance);

        setUpMapIfNeeded();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        fullSizeButton = findViewById(R.id.full);
        fullSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listHolder.getVisibility() == View.GONE) {
                    listHolder.setVisibility(View.VISIBLE);
                } else {
                    listHolder.setVisibility(View.GONE);
                }
            }
        });

        defineMyLocationButton = findViewById(R.id.define_my_location);
        defineMyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = mMap.getMyLocation();

                if (location != null) {

                    LatLng target = new LatLng(location.getLatitude(), location.getLongitude());

                    CameraPosition.Builder builder = new CameraPosition.Builder();
                    builder.zoom(17);
                    builder.target(target);

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));

                }else{
                    Toast.makeText(getBaseContext(), R.string.picker_map_pick_my_wait, Toast.LENGTH_SHORT).show();

                }
            }
        });

        pickCurrent = findViewById(R.id.pick_current);
        pickCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Hey!", Toast.LENGTH_SHORT).show();
            }
        });


        // todo do we need these buttons?
        select = findViewById(R.id.select);
        select.setEnabled(false);
        findViewById(R.id.select_text).setEnabled(false);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("latitude", geoData.latitude);
                returnIntent.putExtra("longitude", geoData.longitude);

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        View cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        searchBox = (SearchView) menu.getItem(0).getActionView();
        searchBox.setIconified(true);
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchPlaces(s);
                searchBox.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return true;
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        for (String provider : locationManager.getAllProviders()) {
            currentLocation = locationManager.getLastKnownLocation(provider);
            if (currentLocation != null) {
                break;
            }
        }

        if(currentLocation!=null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 14));
            fetchPlaces(null);
        }
        mMap.setOnMyLocationChangeListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);


    }

    private void fetchPlaces(String query) {

        mMap.clear();
        if(currentLocation==null){
            Toast.makeText(this, R.string.picker_map_sory_notdefined, Toast.LENGTH_SHORT).show();
            return;
        }
        status.setText("Loading");
        fetchingTask = new PlaceFetchingTask(query, 50, currentLocation.getLatitude(), currentLocation.getLongitude()) {
            @Override
            protected void onPostExecute(Object o) {
                Log.i(LOG_TAG, o.toString());
                if(o instanceof ArrayList){
                    list.setVisibility(View.VISIBLE);
                    status.setText("Places nearby");
                    ArrayList<MapItem> array = (ArrayList<MapItem>) o;
                    if(array.isEmpty()){
                        status.setText("No places");
                    }else {
                        list.setAdapter(new PlacesAdapter(MapPickerActivity.this, array));
                        showItemsOnTheMap(array);
                    }
                }else {

                    list.setAdapter(null);
                    status.setText(o.toString());
                    Toast.makeText(MapPickerActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        fetchingTask.execute();
    }

    private void showItemsOnTheMap(ArrayList<MapItem> array) {

        for (MapItem mapItem : array) {

            mMap.addMarker(new MarkerOptions()
                    .position(mapItem.getLatLng())
                           .title(mapItem.name)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.conv_attach_location))
            );
        }
    }

    @Override
    public void onMyLocationChange(Location location) {

        if(currentLocation==null){
            // do we need to attach our location on the start?
            this.currentLocation = location;
            fetchPlaces(null);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));


        }
        this.currentLocation = location;;
        accuranceView.setText(getString(R.string.picker_map_pick_my_accuracy, (int) currentLocation.getAccuracy()));
        Log.d("Location changed", location.toString());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        MapItem mapItem = (MapItem) adapterView.getItemAtPosition(position);


        select.setEnabled(true);
        findViewById(R.id.select_text).setEnabled(true);
        geoData = mapItem.getLatLng();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoData, 16));


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        select.setEnabled(true);
        findViewById(R.id.select_text).setEnabled(true);
        //mMap.clear();

        geoData = latLng;
        if(currentPick==null) {
            MarkerOptions currentPickOptions = new MarkerOptions()
                    .draggable(true)
                    .position(geoData);

            currentPick = mMap.addMarker(currentPickOptions);
        }else{

            currentPick.setPosition(geoData);
        }


    }
}
