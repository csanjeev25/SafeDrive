package com.insomniac.safedrive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private SessionManager mSessionManager;
    private String name,number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new OneFragment(), "Emergency !");
        viewPagerAdapter.addFragment(new TwoFragment(), "BAC");
        viewPagerAdapter.addFragment(new ThreeFragment(), "Search Ride");
        mViewPager.setAdapter(viewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        HashMap<String, String> user = mSessionManager.getUserDetails();
        name = user.get(SessionManager.KEY_NAME);
        number = user.get(SessionManager.KEY_EMAIL);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String title){
            mFragmentTitleList.add(title);
            mFragmentList.add(fragment);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout) {
            mSessionManager.logoutUser();
        }
        if(id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }
}

