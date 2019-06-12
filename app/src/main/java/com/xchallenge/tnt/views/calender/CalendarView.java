package com.xchallenge.tnt.views.calender;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xchallenge.tnt.R;
import com.xchallenge.tnt.views.adapter.CalendarAdapter;
import com.xchallenge.tnt.views.bean.DayInfo;
import com.xchallenge.tnt.views.bean.MonthInfo;
import com.xchallenge.tnt.views.util.CalendarUtil;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ez44446 on 2019/5/14.
 */

public class CalendarView extends FrameLayout {

    private static final String TAG = "CalendarView";

    private List<MonthInfo> list = new ArrayList<>();

    private RecyclerView calendarRecyclerview;
    private LinearLayout titleLinearLayout;
    private LinearLayoutManager linearLayoutManager;
    private CalendarAdapter calendarAdapter;

    private SimpleDateFormat dateFormat;
    private Locale locale;

    private String currentDate;

    private int currentDayPosition = -1;
    private int currentMonthPosition = 0;

    private int multiplyEndDayPosition = -1;
    private int multiplyEndMonthPosition = -1;

    private int c_year;
    private int c_month;
    private int c_day;
    private Paint paint;
    private String multiplyEndDate;


    public enum SelectMode {
        SINGLE, MULTIPLY
    }

    //    weekdays/weekdays+Saturday/all days
    public enum DisplayMode {
        WEEKDAYS(5), WITH_SATURDAY(6), ALL(7);

        public int days;

        DisplayMode(int days) {
            this.days = days;
        }
    }

    private SelectMode currentSelectMode = SelectMode.MULTIPLY;
    private DisplayMode currentDisplayMode = DisplayMode.WEEKDAYS;

    public enum SelectState {
        ONE, TWO
    }

    private SelectState currentSelectState = SelectState.ONE;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_calendar, this);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        locale = Locale.getDefault();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#AABBCCDD"));
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        calendarRecyclerview = findViewById(R.id.rv_calendar);
        titleLinearLayout = findViewById(R.id.linearLayout_title);

        checkDisplayMode(currentDisplayMode);

        initViews();

        initData();

    }

    private void initViews() {

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calendarRecyclerview.setLayoutManager(linearLayoutManager);

        calendarAdapter = new CalendarAdapter(getContext(), this, list);

        calendarRecyclerview.setAdapter(calendarAdapter);
        calendarRecyclerview.setItemViewCacheSize(200);
        calendarRecyclerview.setHasFixedSize(true);
        calendarRecyclerview.setNestedScrollingEnabled(false);

        calendarRecyclerview.addItemDecoration(new StickyItemDecoration(getContext(), new StickyItemDecoration.OnGroupListener() {
            @Override
            public String getGroupTitle(int position) {
                if (list != null && list.get(position) != null) {
                    return list.get(position).getTitle();
                }
                return "";
            }
        }));

    }

    /**
     * FIXME date could be set
     */
    private void initData() {

        //  set title
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();
        for (int i = 0; i < titleLinearLayout.getChildCount(); i++) {
            TextView textView = (TextView) titleLinearLayout.getChildAt(i);
            if (i != 6) {
                textView.setText(shortWeekdays[i + 2].toUpperCase());
            } else {
                textView.setText(shortWeekdays[1].toUpperCase());
            }
        }

        list.clear();
        // current date
        Calendar c = Calendar.getInstance();

        createData(c, true, 0);

        //  create next two month
        for (int i = 1; i < 3; i++) {
            // current month + 1 , not add i
            c.add(Calendar.MONTH, 1);
            createData(c, false, i);
        }

        calendarAdapter.notifyDataSetChanged();

    }

    private void createData(Calendar c, boolean isFirst, int monthIndex) {


        //  Jan is 0
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        int week = c.get(Calendar.DAY_OF_WEEK);

        Date date = c.getTime();
        if (currentDate == null) {
            currentDate = dateFormat.format(date);
            changeToYMD(currentDate);
        }


        // get max day of this month
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        MonthInfo info = new MonthInfo();
        List<DayInfo> dayList = new ArrayList<>();

        String monthInLocale = c.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
//        String displayName2 = c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
//        String displayName3 = c.getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.getDefault());
        info.setTitle((month == 1 ? monthInLocale + " " + c.get(Calendar.YEAR) : monthInLocale).toUpperCase());

        info.setDate(c.get(Calendar.YEAR) + "年" + month + "月");


        // get week for firstDay of this month
        int w = CalendarUtil.getWeekNoFormat(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-01") - 1;

        //  w -1 , because we start with monday
        if (w == 0) {
            w = 7;
        }

        //  mon =1 ,sun =7
        info.setWeekStart(w);

        int startDay = 1;
        switch (currentDisplayMode) {
            case WEEKDAYS:
                if (w == 6) {
                    startDay = 3;
                } else if (w == 7) {
                    startDay = 2;
                } else {
                    createEmptyDayInfo(dayList, w);
                }

                break;
            case WITH_SATURDAY:
                if (w == 7) {
                    startDay = 2;
                } else {
                    createEmptyDayInfo(dayList, w);
                }
                break;
            case ALL:
                createEmptyDayInfo(dayList, w);
                break;
        }


        //  set dayInfo of current month
        for (int i = startDay; i <= maxDay; i++) {

            switch (currentDisplayMode) {
                case WEEKDAYS:
                    if ((i + w - 1) % 7 == 6 || (i + w - 1) % 7 == 0) continue;
                    break;
                case WITH_SATURDAY:
                    if ((i + w - 1) % 7 == 0) continue;
                    break;
                case ALL:
                    break;
            }

            DayInfo dayInfo = new DayInfo();
            dayInfo.setDay(i + "");
            dayInfo.setDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + i);

            // if the day is currentday , record position
            if (c_year == c.get(Calendar.YEAR) && c_month == (c.get(Calendar.MONTH) + 1) && c_day == i) {
                //  FIXME
                currentMonthPosition = isFirst ? 0 : monthIndex;
                // dont use i+ startDay
                currentDayPosition = dayList.size();
                dayInfo.setSelect(true);
                info.setSelected(true);
            }
            if (isFirst) {
                if (i < day) {
                    dayInfo.setEnable(false);
                } else {
                    dayInfo.setEnable(true);
                }
            } else {
                dayInfo.setEnable(true);
            }

            dayList.add(dayInfo);
        }
        info.setList(dayList);
        list.add(info);
    }

    private void changeToYMD(String currentDate) {
//        c_year = currentDate.get(Calendar.YEAR);
//        c_month = currentDate.get(Calendar.MONTH);
//        c_day = currentDate.get(Calendar.DAY_OF_MONTH);
        c_year = Integer.parseInt(currentDate.split("-")[0]);
        c_month = Integer.parseInt(currentDate.split("-")[1]);
        c_day = Integer.parseInt(currentDate.split("-")[2]);
    }

    // before w, set enable false
    private void createEmptyDayInfo(List<DayInfo> dayList, int w) {
        for (int t = 0; t < w - 1; t++) {
            DayInfo dayInfo = new DayInfo();
            dayInfo.setDay("");
            dayInfo.setEnable(false);
            dayInfo.setDate("");
            dayList.add(dayInfo);
        }
    }

    public void checkDisplayMode(DisplayMode mode) {
        if (titleLinearLayout == null) {
            return;
        }

        TextView satTv = titleLinearLayout.findViewById(R.id.tv_sat);
        TextView sunTv = titleLinearLayout.findViewById(R.id.tv_sun);

        switch (mode) {
            case WEEKDAYS:
                satTv.setVisibility(GONE);
                sunTv.setVisibility(GONE);
                break;
            case WITH_SATURDAY:
                satTv.setVisibility(VISIBLE);
                sunTv.setVisibility(GONE);
                break;
            case ALL:
                satTv.setVisibility(VISIBLE);
                sunTv.setVisibility(VISIBLE);
                break;
        }
    }


    /**
     * TODO  modified
     *
     * @param currentDisplayMode
     */
    public void setCurrentDisplayMode(DisplayMode currentDisplayMode) {
        this.currentDisplayMode = currentDisplayMode;
        checkDisplayMode(this.currentDisplayMode);
        multiplyEndDayPosition = -1;
        multiplyEndMonthPosition = -1;
        currentSelectState = SelectState.ONE;
        initData();
    }

    public DisplayMode getCurrentDisplayMode() {
        return currentDisplayMode;
    }

    public SelectMode getCurrentSelectMode() {
        return currentSelectMode;
    }

    public void setCurrentSelectMode(SelectMode currentSelectMode) {
        this.currentSelectMode = currentSelectMode;
        if (currentSelectMode == SelectMode.SINGLE) {
            currentSelectState = SelectState.ONE;
            multiplyEndDayPosition = -1;
            multiplyEndMonthPosition = -1;
        }
        notifItemChanged();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        initData();
    }


    public void setCurrentDate(String currentDate) {
        setCurrentDate(currentDate, true);
    }

    public void setCurrentDate(String currentDate, boolean needNotify) {
        if (onDateChangeListener != null && currentSelectMode == SelectMode.SINGLE) {
            onDateChangeListener.onDateChange(this.currentDate, currentDate);
        }
        this.currentDate = currentDate;
        if (needNotify) {
            initData();
        }
    }

    public void setMultiplyEndDate(String date, boolean needNotiy) {
        multiplyEndDate = date;
        if (needNotiy) {
            calendarAdapter.notifyDataSetChanged();
        }
    }


    public int getCurrentDayPosition() {
        return currentDayPosition;
    }

    public int getCurrentMonthPosition() {
        return currentMonthPosition;
    }

    public int getMultiplyEndDayPosition() {
        return multiplyEndDayPosition;
    }

    public int getMultiplyEndMonthPosition() {
        return multiplyEndMonthPosition;
    }

    public SelectState getCurrentSelectState() {
        return currentSelectState;
    }


    public void setMultiplyEndPosition(int multiplyEndMonthPosition, int multiplyEndDayPosition) {
        this.multiplyEndMonthPosition = multiplyEndMonthPosition;
        this.multiplyEndDayPosition = multiplyEndDayPosition;
        if (onMultiplyDateSelectedListener != null) {
            onMultiplyDateSelectedListener.onMultiplySelected(currentDate, multiplyEndDate);
        }
    }

    public void setCurrentSelectState(SelectState currentSelectState) {
        this.currentSelectState = currentSelectState;
    }

    public void setCurrentPosition(int currentMonthPosition, int currentDayPosition) {
        this.currentMonthPosition = currentMonthPosition;
        this.currentDayPosition = currentDayPosition;
    }

    public void notifItemChanged() {
        if (calendarAdapter != null) {
            calendarAdapter.notifyDataSetChanged();
        }
    }

    public void notifItemChanged(int position) {
        if (calendarAdapter != null) {
            calendarAdapter.notifyItemChanged(position);
        }
    }

    public interface OnDateChangeListener {
        void onDateChange(String oriDate, String newDate);
    }

    private OnDateChangeListener onDateChangeListener;

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    public interface OnMultiplyDateSelectedListener {
        void onMultiplySelected(String start, String end);
    }

    private OnMultiplyDateSelectedListener onMultiplyDateSelectedListener;

    public void setOnMultiplyDateSelectedListener(OnMultiplyDateSelectedListener onMultiplyDateSelectedListener) {
        this.onMultiplyDateSelectedListener = onMultiplyDateSelectedListener;
    }

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
