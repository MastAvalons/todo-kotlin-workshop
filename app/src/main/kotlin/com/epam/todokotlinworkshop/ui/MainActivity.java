package com.epam.todokotlinworkshop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.epam.todokotlinworkshop.R;
import com.epam.todokotlinworkshop.ui.tasklist.FragmentTaskList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            showFragment(FragmentTaskList.newInstance())
//        }
    }
}

//class MainActivity extends AppCompatActivity() {
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        if (savedInstanceState == null) {
//            showFragment(FragmentTaskList.newInstance())
//        }
//    }
//
//}
