package com.example.e_petrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

//Database boş olduğundan hata veriyor.!!!!!!!!!!!!!!!!!!!!!
public class History extends AppCompatActivity {
    ArrayList<Posts> list=new ArrayList<>();
    Thread thread;
    BottomNavigationView navigation ;
    RecyclerView recyclerView;
    Context context=this;
    Button new_history,general,findingRoute;
    TextView hello,favfuel,avarage;
    EditText liter,price;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference read=database.getReference("Users").child(user.getDisplayName()).child("information");
    DatabaseReference his=database.getReference("Users").child(user.getDisplayName());
    DatabaseReference myRef = database.getReference();


    Date date=new Date();
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    String a=df.format(date);
    Spinner favMark;
    int avrg=0;

    public void CreateHistory(String mark,String fuel,String date,String day,String month,String year,String many){
        list.clear();
        String key=myRef.push().getKey();
        Posts post=new Posts(mark,fuel,date,day,month,year,many);
        myRef.child("Users").child(user.getDisplayName()).child("History").child(key).setValue(post);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        navigation = findViewById(R.id.bottomNavigationView);
        hello=findViewById(R.id.hello);
        favfuel=findViewById(R.id.favfuel);
        avarage=findViewById(R.id.avarage);
        favMark=findViewById(R.id.which_mark);
        liter=findViewById(R.id.liter_howmuch);
        price=findViewById(R.id.history_price);
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(this,R.array.FuelMark,
                android.R.layout.simple_spinner_item);
        favMark.setAdapter(adapter2);
        new_history=findViewById(R.id.push);


        hello.setText("Hello "+user.getDisplayName());
        avarage.setText("Avarage Fuel(per month) = "+avrg);

        read.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Users i=new Users();
                    i=dataSnapshot.getValue(Users.class);
                    favfuel.setText("Favourite Brand= "+i.getFavmark());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final Thread thread=new Thread(){

            public void run(){

                try{
                    his.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("History")){
                                DatabaseReference Ref=database.getReference("Users").child(user.getDisplayName()).child("History");

                                Ref.addListenerForSingleValueEvent(new ValueEventListener() {


                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for(DataSnapshot data:dataSnapshot.getChildren()){
                                            Posts i=new Posts();
                                            i=data.getValue(Posts.class);
                                            AddList(i.mark,i.date,i.liter,i.many);
                                            calculateAvarage();
                                            //calculateAvarage(i.month);

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
                catch (Exception e){

                }
            }
        };
        thread.start();
        thread.interrupt();


        new_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String litre=liter.getText().toString();
                String favMarka=favMark.getSelectedItem().toString();
                String many=price.getText().toString();
                final Calendar c = Calendar.getInstance();
                String dates;
                int a=c.get(Calendar.MONTH)+1;
                dates=c.get(Calendar.DAY_OF_MONTH)+"."+a+"."+c.get(Calendar.YEAR);
                String day=c.get(Calendar.DAY_OF_MONTH)+"";
                String month=c.get(Calendar.MONTH)+"";
                String year=c.get(Calendar.YEAR)+"";
                CreateHistory(favMarka,litre,dates,day,month,year,many);
                thread.start();



            }
        });





        recyclerView=findViewById(R.id.anamenü);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.canScrollVertically();
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



    }

    public void AddList(String x,String y,String v,String z){
        list.add(new Posts(x,v,y,z));
        ListAdapter listAdapter=new ListAdapter(list,context);
        recyclerView.setAdapter(listAdapter);



    }
    public void calculateAvarage(){
        DatabaseReference Ref=database.getReference("Users").child(user.getDisplayName()).child("History");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
        int total = 0;
        int count = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Posts i=new Posts();
                    i=data.getValue(Posts.class);
                    total +=Integer.parseInt(i.liter);
                    count++;

                }
                int average = total/count;
                avarage.setText(String.valueOf("Average= "+average+ " per/month"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
}
