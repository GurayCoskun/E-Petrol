package com.example.e_petrol;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ZoomControls;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ZoomControls zoom=(ZoomControls)findViewById(R.id.zoom);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        final Button btn_MapType=(Button) findViewById(R.id.btn_Sat);
        btn_MapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    btn_MapType.setText("NORMAL");
                }else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    btn_MapType.setText("UYDU");
                }
            }
        });

        Button btnGo=(Button) findViewById(R.id.btn_Go);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etLocation=(EditText)findViewById(R.id.et_location);
                String location=etLocation.getText().toString();
                if(location!=null && !location.equals("")){
                    List<Address> adressList=null;
                    Geocoder geocoder=new Geocoder(MapsActivity.this);
                    try {


                        adressList=geocoder.getFromLocationName(location,1);


                    } catch (IOException e) {
                        System.out.println("Hata mesajı");
                        e.printStackTrace();
                    }
                    Address address=adressList.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Burası "+location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



    }


}