package com.jhson.view.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager = null;
    private RollingAdapter mAdapter = null;
    private IndicatorView mIndicatorView = null;
    private AutoRollingManager mAutoRollingManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mIndicatorView = (IndicatorView)findViewById(R.id.indicator_view);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        mAdapter = new RollingAdapter(MainActivity.this, getData(), new RollingAdapter.OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RollingModel object, int position) {
                Toast.makeText(MainActivity.this, position + " items click!", Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mIndicatorView.setViewPager(mViewPager, true);
        mAutoRollingManager = new AutoRollingManager(mViewPager, mAdapter, mIndicatorView);
        mAutoRollingManager.setRollingTime(5500);
    }

    private List<RollingModel> getData(){
        List<RollingModel> list = new ArrayList<>();

        list.add(new RollingModel("1", R.drawable.android_honeycomb));
        list.add(new RollingModel("2", R.drawable.android_icecreamsandwich));
        list.add(new RollingModel("3", R.drawable.android_jellybean));
        list.add(new RollingModel("4", R.drawable.android_kitkat));
        list.add(new RollingModel("5", R.drawable.android_lollipop));

        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAutoRollingManager.onRollingStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoRollingManager.onRollingStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAutoRollingManager.onRollingDestroy();
    }
}
