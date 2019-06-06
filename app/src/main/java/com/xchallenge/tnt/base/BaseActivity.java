package com.xchallenge.tnt.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xchallenge.tnt.R;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    public B binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);

        binding = DataBindingUtil.setContentView(this, getLayoutId());

    }

    public abstract int getLayoutId();

}
