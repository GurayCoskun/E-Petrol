package com.example.e_petrol;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Spinner fuel,car,favfuel;
    Button create_user;
    RadioGroup RG;
    RadioButton RB;
    TextView namesurname,phone,mail,sign_pass;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Ref = database.getReference();
    final FirebaseUser userdisplay = FirebaseAuth.getInstance().getCurrentUser();

    public void createUser(String namesurname, String phone, String mail,String fueltype, String fav, String cartype){
        Users user=new Users(namesurname,phone,mail,fueltype,fav,cartype);
        Ref.child("Users").child(namesurname).child("information").setValue(user);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();

        RG=findViewById(R.id.radiogroup);

        namesurname=findViewById(R.id.nameSurname);
        phone=findViewById(R.id.phone);
        mail=findViewById(R.id.gmail);
        sign_pass=findViewById(R.id.sign_pass);
        car=findViewById(R.id.cartype);
        favfuel=findViewById(R.id.sign_fav);
       // back=findViewById(R.id.back);
        create_user=findViewById(R.id.create_user);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.CarType,
                R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        car.setAdapter(adapter);
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(this,R.array.FuelMark,
                R.layout.color_spinner_layout);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        favfuel.setAdapter(adapter2);


      /*  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(KayitOl.this,Login.class);
                startActivity(intent);
            }
        });*/
        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID=RG.getCheckedRadioButtonId();
                RB=findViewById(selectedID);
                final String comename=namesurname.getText().toString();
                final String comephone=phone.getText().toString();
                final String comemail=mail.getText().toString();
                final String comepass=sign_pass.getText().toString();
                final String comecar=car.getSelectedItem().toString();
                final String comefav=favfuel.getSelectedItem().toString();
                if (RG.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(getApplicationContext(),"Please select radio buttons",Toast.LENGTH_SHORT).show();
                    return;

                }
                else{
                    RB=findViewById(selectedID);
                }
                final String comefuel = RB.getText().toString();
                if(TextUtils.isEmpty(comename)){
                    namesurname.setError("This field is required");
                    namesurname.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(comephone)){
                    phone.setError("This field is required");
                    phone.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(comefuel)){
                    phone.setError("This field is required");
                    phone.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(comemail)){
                    mail.setError("This field is required");
                    mail.requestFocus();
                    return;
                }else if(TextUtils.isEmpty(comepass)){
                    sign_pass.setError("This field is required");
                    sign_pass.requestFocus();
                    return;
                }
                else if(comepass.length()<6){
                    Toast.makeText(getApplicationContext(),"Password length should be greater than 6. ",Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(comemail,comepass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Email not found or bad connection",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(SignUp.this, Login.class));
                            finish();

                            createUser(comename,comephone,comemail,comefuel,comefav,comecar);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(comename)
                                    .build();
                            Toast.makeText(SignUp.this, "Sing in succesfully" +userdisplay.getDisplayName(),
                                    Toast.LENGTH_SHORT).show();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                            }
                                        }
                                    });



                        }
                    }
                });


            }
        });


    }
}
