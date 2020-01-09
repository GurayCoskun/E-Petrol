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

import java.util.ArrayList;

public class JsonListAdapter extends RecyclerView.Adapter<JsonListAdapter.ViewHolder> {
    Button btn;
    Context context;

    ArrayList<jsonPost> post = new ArrayList<>();
    LayoutInflater layoutInflater;

    public JsonListAdapter(ArrayList<jsonPost> post, Context context) {

        this.post = post;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //her bir satır için temsil edici arayüz seçilir
        View v = layoutInflater.inflate(R.layout.json_row_list, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(v);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //her bir görünümün içeriği
        jsonPost selectedProduct = post.get(position);
        holder.setData(selectedProduct, position);
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSehir, txtSemt, txtKatkili, txtBenzin, txtMarka;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) { //performansı arttırır
            super(itemView);
            txtSehir = (TextView) itemView.findViewById(R.id.txtSehir);
            txtSemt = (TextView) itemView.findViewById(R.id.txtSemt);
            txtKatkili = (TextView) itemView.findViewById(R.id.txtKatkili);
            txtBenzin = (TextView) itemView.findViewById(R.id.txtBenzin);
            txtMarka = (TextView) itemView.findViewById(R.id.txtMarka);
            linearLayout = itemView.findViewById(R.id.linear);


        }

        public void setData(jsonPost selectedProduct, int position) {

            this.txtSehir.setText(selectedProduct.getSehir());
            this.txtSemt.setText(selectedProduct.getSemt());
            this.txtMarka.setText(selectedProduct.getMarka());
            this.txtKatkili.setText(selectedProduct.getKatkili());
            this.txtBenzin.setText(selectedProduct.getBenzin());


        }

    }


}