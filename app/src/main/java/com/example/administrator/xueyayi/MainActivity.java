package com.example.administrator.xueyayi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private List<Reservation_Record> recordList = new ArrayList<>();
    RecordAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    TextView record_count;
    SwipeRefreshLayout swipeRefreshLayout;
    BmobUser bmobUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Bmob
        Bmob.initialize(this, "4d2a726d5d0cea00f31b49cefb0c7a89");

        initRecord();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecord();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //为悬浮按钮设置点击事件
        findViewById(R.id.fab).setOnClickListener(this);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //为头像和头像下的两行文字设置点击事件，点击跳转到登录界面
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View drawview = navigationView.inflateHeaderView(R.layout.nav_header_main);
        drawview.findViewById(R.id.imageView).setOnClickListener(this);
        drawview.findViewById(R.id.user_account).setOnClickListener(this);
        drawview.findViewById(R.id.textView).setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initRecord(){
        record_count = findViewById(R.id.record_count);
        BmobQuery<Reservation_Record> query = new BmobQuery<>();
        query.order("-Time_Start");
        bmobUser = BmobUser.getCurrentUser();
        query.addWhereEqualTo("UserId",bmobUser.getUsername());
        query.findObjects(new FindListener<Reservation_Record>() {
            @Override
            public void done(List<Reservation_Record> list, BmobException e) {
                if(e == null){
                    recordList.clear();
                    recordList.addAll(list);
                    //Collections.sort(recordList);//默认排序(从小到大)
                    recyclerView = findViewById(R.id.recycler_view);
                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new RecordAdapter(recordList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    
                    record_count.setText(String.valueOf(adapter.getItemCount()));
                }else {
                    //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    Toast.makeText(getApplicationContext(),"查询失败"+e.getMessage()+","+e.getErrorCode(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    Intent intent = null;


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:
            case R.id.textView:
            case R.id.user_account:
                bmobUser = BmobUser.getCurrentUser();
                if (bmobUser != null) {
                    // 允许用户使用应用
                    intent = new Intent(MainActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fab:
                bmobUser = BmobUser.getCurrentUser();
                if (bmobUser != null) {
                    // 允许用户使用应用
                    intent = new Intent(MainActivity.this, Bespeak_Activity.class);
                    startActivity(intent);
                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            BmobUser.logOut();   //清除缓存用户对象
            BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}