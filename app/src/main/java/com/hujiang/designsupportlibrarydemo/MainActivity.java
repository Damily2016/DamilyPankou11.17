package com.hujiang.designsupportlibrarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hujiang.designsupportlibrarydemo.fragments.FirstTabFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar toolbar;
    private RadioButton main_tab_home,main_tab_catagory,mdin_tab_car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction().replace(R.id.container_content, homeFragment).commit();*/
       initView();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(MainActivity.this, R.string.navigation_drawer_close, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(MainActivity.this, R.string.navigation_drawer_open, Toast.LENGTH_SHORT).show();
            }
        };

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {

               return true;
           }
       });

      /*  final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/

        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        navigationView.setNavigationItemSelectedListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

    }

    private void initView() {
        main_tab_home= (RadioButton) findViewById(R.id.main_tab_home);
        main_tab_catagory= (RadioButton) findViewById(R.id.main_tab_catagory);
        mdin_tab_car= (RadioButton) findViewById(R.id.mdin_tab_car);
        main_tab_home.setOnClickListener(this);
        main_tab_catagory.setOnClickListener(this);
        mdin_tab_car.setOnClickListener(this);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        List<String> titles = new ArrayList<>();
        titles.add("进行中");
        titles.add("已完成");
        titles.add("未开始");
        titles.add("关注");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new FirstTabFragment());
        fragments.add(new ListFragment());
        fragments.add(new ListFragment());
        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }

/*    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }*/

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
              /*  toolbar.setTitle("首页");*/
                FragmentManager fmMenuFirst = getSupportFragmentManager();
                Fragment homeFragment = new HomeFragment();
                fmMenuFirst.beginTransaction().replace(R.id.content_main, homeFragment).commit();

                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.main_tab_home:
        break;
    case R.id.main_tab_catagory:
        Intent secondIntent=new Intent(this,SecondTabActivity.class);
       startActivity(secondIntent);
        break;
    case R.id.mdin_tab_car:
       FragmentManager fm=getSupportFragmentManager();
        Fragment FirstTa=new FirstTabFragment();
        fm.beginTransaction().replace(R.id.content_main,FirstTa,null);

        break;

}
    }
}
