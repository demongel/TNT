package com.xchallenge.tnt.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xchallenge.tnt.R;
import com.xchallenge.tnt.base.BaseFragment;
import com.xchallenge.tnt.databinding.FragmentHomeBinding;
import com.xchallenge.tnt.databinding.FragmentHomeDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeDetailFragment extends BaseFragment<FragmentHomeDetailBinding> {


    public HomeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_detail;
    }
}
