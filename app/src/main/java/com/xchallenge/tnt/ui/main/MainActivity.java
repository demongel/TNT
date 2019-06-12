package com.xchallenge.tnt.ui.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xchallenge.tnt.R;
import com.xchallenge.tnt.base.BaseActivity;
import com.xchallenge.tnt.databinding.ActivityMainBinding;
import com.xchallenge.tnt.ui.home.HomeFragment;
import com.xchallenge.tnt.ui.login.LoginFragment;
import com.xchallenge.tnt.ui.my.MineFragment;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = "MainActivity";

    private int index;

    public NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("main_index");
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        binding.navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switch (index) {
            case 0:
                binding.navView.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                binding.navView.setSelectedItemId(R.id.navigation_task);
                break;
            case 2:
                binding.navView.setSelectedItemId(R.id.navigation_mine);
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void hideNav() {
        binding.navView.setVisibility(View.GONE);
    }

    public void hideMain() {
        binding.container.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDestination currentDestination = navController.getCurrentDestination();

        if (currentDestination.getId() == navController.getGraph().getStartDestination()) {
            binding.navView.setVisibility(View.VISIBLE);
            binding.container.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
//                    mOpenCvCameraView.enableView();
//                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    index = 0;
                    break;
                case R.id.navigation_task:
                    index = 1;
                    break;
                case R.id.navigation_mine:
                    index = 2;
                    break;
            }
            fragment = (Fragment) mAdapter.instantiateItem(binding.container, index);
            mAdapter.setPrimaryItem(binding.container, 0, fragment);
            mAdapter.finishUpdate(binding.container);
            return true;
        }
    };

    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        Fragment mFragment = null;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    mFragment = new HomeFragment();
                    break;
                case 1:
                    mFragment = new TaskFragment();
                    break;
                case 2:
                    mFragment = new MineFragment();
                    break;
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("android:support:fragments", null);
        outState.putInt("main_index", index);
    }

    public void checkLogin() {
        //  if  not login , show login fragment
//        binding.login.setVisibility(View.VISIBLE);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out);
//        transaction.add(R.id.login, new LoginFragment(), "login");
//        transaction.commit();

        LoginFragment fragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.show(transaction, "login");

    }
}
