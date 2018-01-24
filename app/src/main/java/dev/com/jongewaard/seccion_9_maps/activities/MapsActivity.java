package dev.com.jongewaard.seccion_9_maps.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dev.com.jongewaard.seccion_9_maps.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
                    //getMapAsync es para obtener el mapa y le paso la interfaz this
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* mMap.setMinZoomPreference(10.0f); //esto limita los zoom, las vistas del mapa.
        mMap.setMaxZoomPreference(15.0f);*/

        // Add a marker in Sydney and move the camera
        LatLng sevilla = new LatLng(37.40911491941731, -5.99075691250005);
        mMap.addMarker(new MarkerOptions().position(sevilla).title("Hola desde Sevilla!").draggable(true));

        CameraPosition camera = new CameraPosition.Builder()
                .target(sevilla)
                .zoom(15)           // limit -> 21
                .bearing(0)         // 0 - 365º
                .tilt(30)           // limit -> 90
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sevilla));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this, "Click on: \n" +
                        "Lat: " + latLng.latitude + "\n" +
                        "Lon: " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this, "Long Click on: \n" +
                                "Lat: " + latLng.latitude + "\n" +
                                "Lon: " + latLng.longitude
                        , Toast.LENGTH_SHORT).show();

            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override   //cuando arrastro
            public void onMarkerDragEnd(Marker marker) {
                Toast.makeText(MapsActivity.this, "Long Click on: \n" +
                                "Lat: " + marker.getPosition().latitude + "\n" +
                                "Lon: " + marker.getPosition().longitude
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
