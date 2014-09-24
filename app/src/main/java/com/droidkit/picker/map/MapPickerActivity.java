package com.droidkit.picker.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
    private View hideButton;
    private Marker currentPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_picker);
        setUpMapIfNeeded();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        hideButton = findViewById(R.id.hide);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.getVisibility() == View.GONE) {
                    list.setVisibility(View.VISIBLE);
                }
                else {
                    list.setVisibility(View.GONE);
                }
            }
        });
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
                status.setText("Loading...");
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
        mMap.setOnMyLocationChangeListener(this);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);

        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(this);
        status = (TextView) findViewById(R.id.status);

    }

    private void fetchPlaces(String s) {
        mMap.clear();
        fetchingTask = new PlaceFetchingTask(s, 50, currentLocation.getLatitude(), currentLocation.getLongitude()) {
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),14));

        }
        this.currentLocation = location;
        Log.d("Location changed", location.toString());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        MapItem mapItem = (MapItem) adapterView.getItemAtPosition(position);


        select.setEnabled(true);
        findViewById(R.id.select_text).setEnabled(true);
        geoData = mapItem.getLatLng();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoData, 14));


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
