package com.odnovolov.randomuser;

import android.os.Bundle;

import com.odnovolov.randomuser.utils.BackPressActivity;

public class MainActivity extends BackPressActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}