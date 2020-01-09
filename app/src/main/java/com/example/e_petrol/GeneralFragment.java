package com.example.e_petrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GeneralFragment extends Fragment {
    private RequestQueue myQueue;
    String benzin,diesel;
    TextView price1,price2,price3;


    private ViewPager viewPager;
    private SliderFragmentCollectionAdapter adapter;


    TextView car,last_time,last_fuel,last_liter;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference read=database.getReference("Users").child(user.getDisplayName()).child("information");
    DatabaseReference Ref=database.getReference("Users").child(user.getDisplayName());


    public GeneralFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myQueue = Volley.newRequestQueue(getContext());

        View view= inflater.inflate(R.layout.fragment_general, container, false);
        car=view.findViewById(R.id.car_name);
        price1 = view.findViewById(R.id.price1);
        price2 = view.findViewById(R.id.price2);
        price3 = view.findViewById(R.id.price3);
        last_time=view.findViewById(R.id.lastfuel_name);
        last_fuel=view.findViewById(R.id.price_name);
        last_liter=view.findViewById(R.id.liter_name);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        SliderFragment sliderFragment = new SliderFragment();
        viewPager = view.findViewById(R.id.slider);
        adapter = new SliderFragmentCollectionAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);


        //fragmentTransaction.add(R.id.slider,sliderFragment);
        //fragmentTransaction.commit();




        read.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Users i=new Users();
                    i=dataSnapshot.getValue(Users.class);
                    car.setText(i.getCartype());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Thread thread=new Thread(){
            public void run(){
                updatePriceDifferences();
                Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild("History")){
                            DatabaseReference Ref=database.getReference("Users").child(user.getDisplayName()).child("History");

                            Ref.addListenerForSingleValueEvent(new ValueEventListener() {



                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot data:dataSnapshot.getChildren()){
                                        System.out.println("giriyor");

                                        Posts i=new Posts();
                                        i=data.getValue(Posts.class);
                                        last_time.setText(i.getDate());
                                        last_fuel.setText(i.getMany()+" TL");
                                        last_liter.setText(i.getLiter());

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        };
        thread.start();
        thread.interrupt();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void updatePriceDifferences(){
        System.out.println("update price");
        final ArrayList<Double>  diselArray = new ArrayList<>();
        final ArrayList<Double> benzinArray = new ArrayList<>();

        final DecimalFormat df = new DecimalFormat("#.###");


        String url="https://api.myjson.com/bins/6vjzs";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("result");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject result =jsonArray.getJSONObject(i);

                        diesel=result.getString("katkili");
                        diselArray.add(Double.parseDouble(diesel));
                        benzin=result.getString("benzin");
                        benzinArray.add(Double.parseDouble(benzin));
                        System.out.println(benzin);




                    }
                }catch (Exception e){
                    System.out.println("HATA MESAJI AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                }

            Collections.sort(benzinArray);
            Collections.sort(diselArray);
            Double max = benzinArray.get(benzinArray.size()-1);
            Double min = benzinArray.get(0);
            Double average = max - min;
            price1.setText(String.valueOf(min));
            price2.setText(String.valueOf(df.format(average)));
            price3.setText(String.valueOf(max));
            System.out.println(max);
            System.out.println(min);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        myQueue.add(request);


    }



}

