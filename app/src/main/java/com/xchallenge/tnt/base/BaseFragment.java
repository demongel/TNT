package com.xchallenge.tnt.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xchallenge.tnt.R;

/**
 * created by  shakespace
 * 2019/6/6  16:40
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment {

    public B binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);

        initData();

        initListener();

        return binding.getRoot();
    }

    public void initData() {
    }

    public void initListener() {
    }

    public abstract int getLayoutId();
}
