package com.xchallenge.tnt.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import com.xchallenge.tnt.api.RetrofitHelper;

/**
 * created by  shakespace
 * 2019/6/9  22:19
 */
public class LoginViewModel extends ViewModel {

    MutableLiveData<Boolean> loginBtnEnable = new MutableLiveData<>();

//    MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
//    MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    private String account;
    private String password;

    void updateAccount(String account) {
        this.account = account;
        checkLogin(account, password);
    }

    void updatePassword(String password) {
        this.password = password;
        checkLogin(account, password);
    }

    private void checkLogin(String acc, String pwd) {
        if (TextUtils.isEmpty(acc) || TextUtils.isEmpty(pwd)) {
            loginBtnEnable.postValue(false);
        } else {
            loginBtnEnable.postValue(true);
        }
    }


    public void login() {
        //
        //  call  retrofit  for mock  login
//        RetrofitHelper.create
    }
}
