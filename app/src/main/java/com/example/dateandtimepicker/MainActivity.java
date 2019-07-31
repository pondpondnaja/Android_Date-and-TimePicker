package com.example.dateandtimepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        btn1= findViewById(R.id.open1);
    }

    public void openfr1(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
    }

    public void openfr2(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment2()).addToBackStack(null).commit();
    }
}
