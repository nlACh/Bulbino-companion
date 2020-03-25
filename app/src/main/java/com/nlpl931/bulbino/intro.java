package com.nlpl931.bulbino;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class intro extends Fragment {
    private String BASE_URL = "http://";
    private TextView tv;
    private EditText et;
    private boolean once = true;

    public intro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rv = inflater.inflate(R.layout.fragment_intro, container, false);
        et = rv.findViewById(R.id.et);
        tv = rv.findViewById(R.id.tv);
        Button connect = rv.findViewById(R.id.connect);
        Button off = rv.findViewById(R.id.off);
        Button rainbow = rv.findViewById(R.id.rainbow);
        SeekBar seekBar = rv.findViewById(R.id.seekBar);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(once){
                    BASE_URL += et.getText();
                }
                tv.setText(BASE_URL);
                new httpReq(getActivity()).requestData(BASE_URL+"/");
                once = false;
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpReq(getActivity()).requestData(BASE_URL+"/off");
            }
        });
        rainbow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpReq(getActivity()).requestData(BASE_URL+"/rainbow");
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //tv.setText(String.valueOf(progress));
                new httpReq(getActivity()).postRequest(BASE_URL+"/post", "b", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rv;
    }
}
