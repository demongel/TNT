package com.xchallenge.tnt.ui.listView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xchallenge.tnt.R;
import com.xchallenge.tnt.bean.HomeItemBean;

import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {

    ArrayList<HomeItemBean> list;

    Drawable icon;

    private Context context = null;
    public HomeListAdapter(Context context,ArrayList<HomeItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public View getView(int position, View converView, ViewGroup parent) {
        if(converView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            converView = inflater.inflate(R.layout.home_item, null, true);
        }
        HomeItemBean homeItemBean= (HomeItemBean) getItem(position);
        Button button = converView.findViewById(R.id.button1);
        button.setText(homeItemBean.itemName);
        if (homeItemBean.itemName == "Meeting") {
            button.setBackgroundResource(R.drawable.btn_selector_purple);
        }
        if (homeItemBean.itemName == "Device") {
            button.setBackgroundResource(R.drawable.btn_selector_red);
        }

        Drawable ic_next = context.getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp);
        ic_next.setBounds(0, 0, ic_next.getMinimumWidth(), ic_next.getMinimumHeight());

        Button button2 = converView.findViewById(R.id.button2);
        button2.setText(homeItemBean.items[0]);
        if (homeItemBean.items[0] == "Initiate Meeting") {
            icon = context.getResources().getDrawable(R.drawable.meeting);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button2.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[0] == "Windows") {
            icon = context.getResources().getDrawable(R.drawable.windows);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button2.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[0] == "OPPM") {
            icon = context.getResources().getDrawable(R.drawable.oppm);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button2.setCompoundDrawables(icon, null, ic_next, null);
        }

        Button button3 = converView.findViewById(R.id.button3);
        button3.setText(homeItemBean.items[1]);
        if (homeItemBean.items[1] == "Meeting Room") {
            icon = context.getResources().getDrawable(R.drawable.meetingroom);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button3.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[1] == "MacBook") {
            icon = context.getResources().getDrawable(R.drawable.mac);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button3.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[1] == "Owner") {
            icon = context.getResources().getDrawable(R.drawable.owner);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button3.setCompoundDrawables(icon, null, ic_next, null);
        }

        Button button4 = converView.findViewById(R.id.button4);
        button4.setText(homeItemBean.items[2]);
        if (homeItemBean.items[2] == "My Meeting") {
            icon = context.getResources().getDrawable(R.drawable.mymeeting);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button4.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[2] == "Phone") {
            icon = context.getResources().getDrawable(R.drawable.mobile);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button4.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[2] == "Member") {
            icon = context.getResources().getDrawable(R.drawable.member);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button4.setCompoundDrawables(icon, null, ic_next, null);
        }

        Button button5 = converView.findViewById(R.id.button5);
        button5.setText(homeItemBean.items[3]);
        if (homeItemBean.items[3] == "Meeting Summary") {
            icon = context.getResources().getDrawable(R.drawable.meetingsummary);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button5.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[3] == "Display") {
            icon = context.getResources().getDrawable(R.drawable.computer);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button5.setCompoundDrawables(icon, null, ic_next, null);
        }
        if (homeItemBean.items[3] == "Release") {
            icon = context.getResources().getDrawable(R.drawable.release);
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
            button5.setCompoundDrawables(icon, null, ic_next, null);
        }

        return converView;
    }
}
