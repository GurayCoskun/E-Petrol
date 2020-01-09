package com.example.e_petrol;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;



import java.util.ArrayList;

public class ListStation extends AppCompatActivity {
    ArrayList<jsonPost> list=new ArrayList<>();
    RecyclerView recyclerView;
    Context context=this;
    Button Maps;


    String Sehir,marka,Semt;



    private RequestQueue myQueue;
    Button b;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myQueue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_station);
       jsonParse();



    }
    private void jsonParse(){
        String url="https://api.myjson.com/bins/6vjzs";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("result");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject result =jsonArray.getJSONObject(i);
                        Sehir=result.getString("Sehir");
                        Semt=result.getString("Semt");
                        String katkili=result.getString("katkili");
                        String benzin=result.getString("benzin");
                        marka=result.getString("marka");
                        jsonPost post=new jsonPost(Sehir,Semt,marka,katkili,benzin);
                        AddList(post.sehir,post.semt,post.marka,post.katkili,post.benzin);
                    }
                }catch (Exception e){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        myQueue.add(request);
        recyclerView=findViewById(R.id.anamenü);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.canScrollVertically();
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    public void AddList(String x,String y,String v,String z,String c){
        list.add(new jsonPost(x+"/",y,v,"Katkılı "+z,"Benzin "+c));
        JsonListAdapter jsonListAdapter =new JsonListAdapter(list,context);
        recyclerView.setAdapter(jsonListAdapter);



    }
    public void myClickHandler(View v) {

        jsonPost post=new jsonPost();
// adrese göre harita noktası
        Uri location = Uri.parse("geo:0,0?q="+Sehir+" "+Semt+" "+marka);
// ya da enlem ve boylam yerine göre konum bilgisi
// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z parametresi yaklaşma seviyesini belirler
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }



}
