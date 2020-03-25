package com.nlpl931.bulbino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class scenes extends Fragment {

    public scenes() {
        // Required empty public constructor
    }
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rv =  inflater.inflate(R.layout.fragment_scenes, container, false);
        final ColorPicker picker = rv.findViewById(R.id.picker);
        SVBar svb = rv.findViewById(R.id.SVBar);
        OpacityBar opb = rv.findViewById(R.id.opacityBar);
        tv = rv.findViewById(R.id.tvColor);

        picker.addOpacityBar(opb);
        picker.addSVBar(svb);

        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                String hexColor = String.format("#%06X", (0xFFFFFF & color)); // # is just the symbol before hex.
                // %06X means a 6 digit hex. %x means string will be a hex, converted from some other number.
                tv.setText(hexColor);
                picker.setOldCenterColor(picker.getColor());
            }
        });
        return rv;
    }
}
