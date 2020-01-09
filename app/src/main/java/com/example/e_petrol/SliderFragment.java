package com.example.e_petrol;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;




/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment {
    private TextView textView;
    private Button button;
    private FrameLayout frameLayout;




    public SliderFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        button = view.findViewById(R.id.navigate_button);
        frameLayout = view.findViewById(R.id.background);
        int position = getArguments().getInt("button");

        String message = getArguments().getString("mes");
        int image = getArguments().getInt("image");


        frameLayout.setBackgroundResource(image);
        button.setText(message);



        if (position == 1){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),ListStation.class));

                }
            });
        }
        else if (position ==2){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),History.class));

                }
            });

        }
        else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri location = Uri.parse("geo:0,0?q="+"Muğla"+" Kötekli");

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    startActivity(mapIntent);

                }
            });

        }

        return view;
    }
        public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);

        fragmentTransaction.commit();

    }

}
