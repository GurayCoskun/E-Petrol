package com.example.e_petrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class General extends AppCompatActivity {
    BottomNavigationView navigation ;
    private FrameLayout nMainFrame;
    private GeneralFragment generalFragment;
    private ProfilFragment profilFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);



        navigation =(BottomNavigationView) findViewById(R.id.bottomNavigationView);
        nMainFrame = (FrameLayout)findViewById(R.id.main_frame);

        generalFragment = new GeneralFragment();
        profilFragment = new ProfilFragment();


        setFragment(generalFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home :
                        startActivity(new Intent(General.this, General.class));
                        return true;


                    case R.id.profil:
                        setFragment(profilFragment);


                        return true;


                    case R.id.logout:
                        startActivity(new Intent(General.this, Login.class));

                        return true;


                        default:
                            return false;



                }
            }
        });



    }
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }

}