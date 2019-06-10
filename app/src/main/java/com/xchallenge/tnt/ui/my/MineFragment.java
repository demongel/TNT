package com.xchallenge.tnt.ui.my;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.squareup.haha.perflib.Main;
import com.xchallenge.tnt.R;
import com.xchallenge.tnt.base.BaseFragment;
import com.xchallenge.tnt.databinding.FragmentMineBinding;
import com.xchallenge.tnt.ui.login.LoginFragment;
import com.xchallenge.tnt.ui.main.MainActivity;
import com.xchallenge.tnt.util.AvatarGenerator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment<FragmentMineBinding> {


    private MainActivity activity;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
//        binding.imageView.setImageURI(Uri.fromFile(new File(file)));


        AvatarGenerator generator = new AvatarGenerator();
        Bitmap avatar = generator.getARandomAvatar();

        binding.imageView.setImageBitmap(avatar);

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                fragment.show()
//
                activity.checkLogin();

            }
        });

    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (getView() != null) {
            getView().setVisibility(menuVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
