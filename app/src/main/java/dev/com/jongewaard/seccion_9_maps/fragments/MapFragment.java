package dev.com.jongewaard.seccion_9_maps.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import dev.com.jongewaard.seccion_9_maps.R;


public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private View rootView;
    private MapView mapView; //este captura el Layout!
    private GoogleMap gMap;

    private FloatingActionButton fab;


    public MapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return rootView;
    }


    /*
    * Aquí es donde se crean las vistas. Es donde esta el fragmento cargado
    * */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(false);



    }


    private boolean isGPSIEnable(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if(gpsSignal == 0){
                //El GPS no esta activado
                return false;
            }else{
                return true;
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Signal")
                .setMessage("You don't have GPS signal enabled. Would you like to enable the GPS signal now?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void showInfoTienesAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Signal")
                .setMessage("You have GPS signal enabled.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    /*Cuando se haga Click en el Boton fab.setOnClickListener. (el boton naranja o rosa*/
    @Override
    public void onClick(View v) {

        //si el GPS No esta habilitado
        if(!this.isGPSIEnable())
            showInfoAlert();
    }
}
