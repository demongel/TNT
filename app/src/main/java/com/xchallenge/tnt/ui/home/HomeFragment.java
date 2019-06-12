package com.xchallenge.tnt.ui.home;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xchallenge.tnt.R;
import com.xchallenge.tnt.base.BaseFragment;
import com.xchallenge.tnt.bean.HomeItemBean;
import com.xchallenge.tnt.databinding.FragmentHomeBinding;
import com.xchallenge.tnt.ui.listView.HomeListAdapter;
import com.xchallenge.tnt.ui.listView.MyListView;
import com.xchallenge.tnt.ui.main.MainActivity;
import com.xchallenge.tnt.util.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import androidx.navigation.Navigation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private static final String TAG = "HomeFragment";

    private MainActivity activity;

    ArrayList<HomeItemBean>listData=new ArrayList<>();
    HomeListAdapter homeListAdapter;
    View  contentView;
    private Banner mBanner;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

//    @Override
//    public void initListener() {
//        super.initListener();
//
//
//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!activity.isDestroyed()) {
//                    activity.navController.navigate(R.id.action_blankFragment_to_homeDetailFragment);
//                    activity.hideNav();
//                    activity.hideMain();
//                }
//            }
//        });
//    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
        Log.e(TAG, "onCreate: ");
    }

    private void setData() {
        listData.add(new HomeItemBean("Meeting",new String[]{"Initiate Meeting","Meeting Room","My Meeting","Meeting Summary"}));
        listData.add(new HomeItemBean("Device",new String[]{"Windows","MacBook","Phone","Display"}));
        listData.add(new HomeItemBean("Project",new String[]{"OPPM","Owner","Member","Release"}));
    }

    private void initBannerData() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(R.mipmap.a);
        imagePath.add(R.mipmap.b);
        imagePath.add(R.mipmap.c);
        imageTitle.add("Mobile team building(1)");
        imageTitle.add("Mobile team building(2)");
        imageTitle.add("Mobile team building(3)");
    }

    private void initBannerView() {
        mBanner = contentView.findViewById(R.id.mbanner);
        initBannerData();
        for (int i=0;i<imagePath.size();i++) {
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(contentView).load(path).into(imageView);
                }
            });
            mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
            mBanner.setBannerTitles(imageTitle);
            mBanner.setDelayTime(3000);
            mBanner.isAutoPlay(true);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setImages(imagePath).start().startAutoPlay();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG","onCreateView");
        contentView=inflater.inflate(R.layout.fragment_home,container,false);
        homeListAdapter = new HomeListAdapter(getContext(),listData);
        MyListView listView = contentView.findViewById(R.id.id_listview_fragment);
        listView.setAdapter(homeListAdapter);
        TextView tv1 = contentView.findViewById(R.id.textview1);
        tv1.setSelected(true);
        initBannerView();
        return contentView;
    }


    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.stopAutoPlay();
        Log.e(TAG, "onResume: ");
    }
}
