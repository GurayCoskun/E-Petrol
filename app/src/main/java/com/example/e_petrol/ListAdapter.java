package com.example.e_petrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    ArrayList<Posts> post=new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;
    public ListAdapter(ArrayList<Posts> post, Context context) {

        this.post = post;
        layoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //her bir satır için temsil edici arayüz seçilir
        View v=layoutInflater.inflate(R.layout.row_list,parent,false);
        RecyclerView.ViewHolder vh=new ViewHolder(v);
        return (ViewHolder) vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //her bir görünümün içeriği
        Posts selectedProduct = post.get(position);
        holder.setData(selectedProduct, position);
    }

    @Override
    public int getItemCount() {
        return post.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDate,txtMark,txtLiter,txtPrice;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) { //performansı arttırır
            super(itemView);
            txtDate=(TextView) itemView.findViewById(R.id.txtDate);
            txtMark=(TextView)itemView.findViewById(R.id.txtMark);
            txtLiter=(TextView)itemView.findViewById(R.id.txtLiter);
            txtPrice=(TextView)itemView.findViewById(R.id.txtprice);
            linearLayout=itemView.findViewById(R.id.linear);


        }
        public void setData(Posts selectedProduct, int position) {

            this.txtDate.setText(selectedProduct.getDate());
            this.txtLiter.setText(selectedProduct.getLiter());
            this.txtMark.setText(selectedProduct.getMark());
            this.txtPrice.setText(selectedProduct.getMany());


        }

    }


}
