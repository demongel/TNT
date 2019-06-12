package com.xchallenge.tnt.views.bean;


import com.xchallenge.tnt.views.adapter.MonthAdapter;

import java.util.List;


public class MonthInfo {


    private String title;
    private String date;


    //  current month be selected
    private boolean selected;

    private int weekStart;

    private List<DayInfo> list;

    private MonthAdapter adapter;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DayInfo> getList() {
        return list;
    }

    public void setList(List<DayInfo> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(int weekStart) {
        this.weekStart = weekStart;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MonthAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MonthAdapter adapter) {
        this.adapter = adapter;
    }
}
