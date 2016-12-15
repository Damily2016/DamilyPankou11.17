package com.damily.pkds.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.damily.pkds.MyApplication;
import com.damily.pkds.R;
import com.damily.pkds.fragments.HomeFragment;
import com.damily.pkds.fragments.NavForthFragment;
import com.damily.pkds.fragments.NavSecondFragment;
import com.damily.pkds.fragments.NavThirdFragment;
import com.damily.pkds.fragments.SecondTabFragment;
import com.damily.pkds.fragments.ThirdTabFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private ConnectivityManager manager;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private RadioButton main_tab_home, main_tab_catagory, mdin_tab_me;
    private RadioGroup main_tab_group;
    private static final String TAG = "MainActivity";

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        initView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Toast.makeText(MainActivity.this, R.string.navigation_drawer_close, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //   Toast.makeText(MainActivity.this, R.string.navigation_drawer_open, Toast.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        main_tab_home = (RadioButton) findViewById(R.id.main_tab_home);
        main_tab_catagory = (RadioButton) findViewById(R.id.main_tab_catagory);
        mdin_tab_me = (RadioButton) findViewById(R.id.mdin_tab_me);
        main_tab_group = (RadioGroup) findViewById(R.id.main_tab_group);
        main_tab_home.setOnClickListener(this);
        main_tab_catagory.setOnClickListener(this);
        mdin_tab_me.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_content, homeFragment).commit();


    }

    @Override
    protected void onResume() { // judge network Broadcast
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(NetworkReceiver, intentFilter);
    }

    private BroadcastReceiver NetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkAvailable())
                NetworkState();
            else
                Toast.makeText(MainActivity.this, "请检查网络环境", Toast.LENGTH_SHORT).show();
        }
    };

    private void NetworkState() {
        NetworkInfo.State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            Toast.makeText(MainActivity.this, "当前网络状态为Mobile", Toast.LENGTH_SHORT).show();
        }
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            Toast.makeText(MainActivity.this, "当前网络状态为Wifi", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean NetworkAvailable() {
        try {
            manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null || networkInfo.isAvailable()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
 /* 标题不要菜单项目 titlebar without menu item
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;
          /*  case R.id.action_settings:
                finish();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Fragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, homeFragment).commit();
                mDrawerLayout.closeDrawers();
                main_tab_home.setChecked(true);
                main_tab_group.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_second:
                Fragment navSecondFragment = new NavSecondFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, navSecondFragment).commit();
                mDrawerLayout.closeDrawers();
                break;
          case R.id.nav_third:
                Fragment navThirdFragment = new NavThirdFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, navThirdFragment).commit();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.nav_forth:
                Fragment navForthFragment = new NavForthFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, navForthFragment).commit();
                mDrawerLayout.closeDrawers();
                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tab_home:
                Fragment homeFragment = new HomeFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, homeFragment).commit();
                break;
            case R.id.main_tab_catagory:
                Fragment secondFragment = new SecondTabFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, secondFragment).commit();

                break;
            case R.id.mdin_tab_me:
                Fragment moreFragment = new ThirdTabFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, moreFragment).commit();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(NetworkReceiver);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("确定要退出吗?");
            builder.setTitle("提示");
            builder.setPositiveButton("确定", listener);
            builder.setNegativeButton("取消", listener);
            builder.create().show();
        }
        return false;
       /* if (keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
             long exitTime=0;
            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else
            {
               finish();
               System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);*/
    }
/*    public boolean dispatchKeyEvent(KeyEvent event){
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK){
            if (event.getAction()==KeyEvent.ACTION_DOWN && event.getRepeatCount()==0){
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exitApp() {
        long exitTime=0;
        if ((System.currentTimeMillis()-exitTime)>2000){
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }else
        {
            finish();

        }
    }*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    MyApplication.getInstance().exit();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    dialog.dismiss();
                break;
            }
        }
    };

}
