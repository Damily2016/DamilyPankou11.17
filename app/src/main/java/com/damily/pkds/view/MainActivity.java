package com.damily.pkds.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.damily.pkds.R;
import com.damily.pkds.fragments.HomeFragment;
import com.damily.pkds.fragments.SecondTabFragment;
import com.damily.pkds.fragments.ThirdTabFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private RadioButton main_tab_home, main_tab_catagory, mdin_tab_me;

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
    }

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
                Fragment secondFragment=new SecondTabFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content,secondFragment).commit();

                break;
            case R.id.mdin_tab_me:
                Fragment moreFragment = new ThirdTabFragment();
                fragmentManager.beginTransaction().replace(R.id.container_content, moreFragment).commit();
                break;

        }
    }
}
