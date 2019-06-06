package com.xchallenge.tnt.ui.my;


import android.support.v4.app.Fragment;
import android.view.View;

import com.xchallenge.tnt.R;
import com.xchallenge.tnt.base.BaseFragment;
import com.xchallenge.tnt.databinding.FragmentMineBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<FragmentMineBinding> {


    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
