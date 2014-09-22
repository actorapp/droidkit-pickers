package com.droidkit.picker.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kiolt_000 on 22/09/2014.
 */
public class MapItem {
    public String id;
    public String icon;
    public Geometry geometry;
    public String name;
    public String vicinity;
    public String[] types;

    public android.location.Location getLocation(){
        return new android.location.Location("google"){{
            setLatitude(geometry.location.lat);
            setLongitude(geometry.location.lng);
        }};
    }

    public LatLng getLatLng() {
        return new LatLng(geometry.location.lat, geometry.location.lng);
    }

    public static class Geometry{
        public Location location;
    }
    public static class Location{
        public double lat;
        public double lng;
    }
}
