package com.xchallenge.tnt.views.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.xchallenge.tnt.R;
import com.xchallenge.tnt.views.calender.CalendarView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class MonthViewActivity extends Activity {

    private static final String TAG = "MonthViewActivity";
    private TextView displayView;
    private TextView displayView2;
    private CalendarView calendar;
    private Button changeMode;
    private Button changeWeek;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);


        calendar = findViewById(R.id.calendar);
        displayView = findViewById(R.id.display);
        displayView2 = findViewById(R.id.display2);

        changeMode = findViewById(R.id.changemode);
        changeWeek = findViewById(R.id.changeweek);

//        ctv.setCurrentState(CalendarCell.State.RIGHT);

        calendar.setLocale(Locale.ENGLISH);
//        calendar.setLocale(Locale.FRANCE);
//        calendar.setLocale(Locale.GERMAN);


        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.getCurrentSelectMode() == CalendarView.SelectMode.SINGLE) {
                    calendar.setCurrentSelectMode(CalendarView.SelectMode.MULTIPLY);
                } else {
                    calendar.setCurrentSelectMode(CalendarView.SelectMode.SINGLE);
                }
            }
        });

        changeWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;

                if (index % 3 == 0) {
                    calendar.setCurrentDisplayMode(CalendarView.DisplayMode.ALL);
                } else if (index % 3 == 2) {
                    calendar.setCurrentDisplayMode(CalendarView.DisplayMode.WITH_SATURDAY);
                } else {
                    calendar.setCurrentDisplayMode(CalendarView.DisplayMode.WEEKDAYS);
                }

            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onDateChange(String oriDate, String newDate) {
                displayView.setText("current is " + newDate);
            }
        });

        calendar.setOnMultiplyDateSelectedListener(new CalendarView.OnMultiplyDateSelectedListener() {
            @Override
            public void onMultiplySelected(String start, String end) {
                displayView2.setText("start date is " + start);
                displayView.setText("end date is " + end);
            }
        });


        // ----------------- Test Begin

//        DatePicker picker = findViewById(R.id.datePicker);

        Calendar instance = Calendar.getInstance();

        /**
         * retrurn an array with size 8  array[0] = ""
         *         array[1] begin sunday
         */
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());

        String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();

        DateFormatSymbols dateFormatSymbols2 = new DateFormatSymbols(Locale.FRANCE);

        String[] shortWeekdays2 = dateFormatSymbols2.getShortWeekdays();

        DateFormatSymbols dateFormatSymbols3 = new DateFormatSymbols(Locale.JAPAN);

        String[] shortWeekdays3 = dateFormatSymbols3.getShortWeekdays();

        Log.e(TAG, "onCreate: ");

        /**
         * 系统相关的类
         * DatePickerCalendarDelegate
         * Calendar
         */
    }
}