package com.example.e_petrol;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    EditText nameUp, phoneUp, mailUp;
    Spinner fuel, car;
    Button update;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference read = database.getReference("Users").child(user.getDisplayName()).child("information");
    DatabaseReference us = database.getReference("Users");


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameUp = view.findViewById(R.id.nameSurnameUp);
        phoneUp = view.findViewById(R.id.phoneUp);
        mailUp = view.findViewById(R.id.gmailUp);
        car = view.findViewById(R.id.cartypeUp);
        fuel = view.findViewById(R.id.sign_favUp);
        update = view.findViewById(R.id.update);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.CarType,
                R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        car.setAdapter(adapter);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.FuelMark,
                R.layout.color_spinner_layout);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        fuel.setAdapter(adapter2);

        read.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users i = new Users();
                    i = dataSnapshot.getValue(Users.class);
                    nameUp.setText(i.namesurname);
                    phoneUp.setText(i.phone);
                    mailUp.setText(i.mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entername = nameUp.getText().toString();
                String enterphone = phoneUp.getText().toString();
                String entermail = mailUp.getText().toString();
                updateData(entername, enterphone, entermail);
            }
        });

    }


    public void updateData(final String entername, final String enterphone, final String entermail) {
        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot edtData : dataSnapshot.getChildren()) {
                    read.getRef().child("namesurname").setValue(entername);
                    read.getRef().child("phone").setValue(enterphone);
                    read.getRef().child("mail").setValue(entermail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
