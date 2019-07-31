package com.example.dateandtimepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Fragment2 extends Fragment {
    public static final int REQUEST_CODE = 11;
    EditText editText;
    String selectedTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment2_activity,container,false);
        editText = rootview.findViewById(R.id.time_edit);

        final FragmentManager fm = getActivity().getSupportFragmentManager();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(Fragment2.this,REQUEST_CODE);
                timeFragment.show(fm,"timePicker");
            }
        });

        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get Time from string
            selectedTime = data.getStringExtra("selectedTime");
            // set the value of the editText
            editText.setText(selectedTime);
        }
    }
}
