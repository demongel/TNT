package com.xchallenge.tnt.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xchallenge.tnt.R;
import com.xchallenge.tnt.views.bean.DayInfo;
import com.xchallenge.tnt.views.bean.MonthInfo;
import com.xchallenge.tnt.views.calender.CalendarCell;
import com.xchallenge.tnt.views.calender.CalendarView;
import com.xchallenge.tnt.views.interfaze.OnRecycleViewItemClickListener;

import java.util.List;

/**
 * Created by ez44446 on 2019/5/14.
 */

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthHolder> {
    private static final String TAG = "MonthAdapter";

    Context context;
    List<DayInfo> list;
    //    CalendarView.DisplayMode displayMode;
//    CalendarView.SelectMode selectMode;
    MonthInfo monthInfo;
    private int monthPosition;
    private CalendarView calendarView;

    private OnRecycleViewItemClickListener listener;
    private int weekStart;
    private int maxDay;

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.listener = onRecycleViewItemClickListener;
    }

    public MonthAdapter() {
    }

    public MonthAdapter(Context context, CalendarView calendarView, MonthInfo monthInfo, int position) {
        this.context = context;
        this.calendarView = calendarView;
        this.monthInfo = monthInfo;
        this.list = monthInfo.getList();
        this.monthPosition = position;
//        this.displayMode = displayMode;
//        this.selectMode = selectMode;

        weekStart = monthInfo.getWeekStart();

        checkWeekStart();

        maxDay = monthInfo.getList().size();
    }

    public void checkWeekStart() {
        switch (calendarView.getCurrentDisplayMode()) {
            case ALL:

                break;
            case WEEKDAYS:
                if (weekStart == 6 || weekStart == 7) {
                    weekStart = 1;
                }
                break;
            case WITH_SATURDAY:
                if (weekStart == 7) {
                    weekStart = 1;
                }
                break;
        }
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_day_item, parent, false);
        return new MonthHolder(view);

    }

    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        DayInfo dayInfo = list.get(position);
        holder.textView.setText(dayInfo.getDay());

        if (dayInfo.isEnable()) {
            holder.textView.setEnabled(true);

//            Log.e(TAG, "onBindViewHolder: " +
//                    " monthPosition  = " + monthPosition + " position == " + position + " smonth " + calendarView.getCurrentMonthPosition() + " sday == " +
//                    calendarView.getCurrentDayPosition());

            if (calendarView.getCurrentSelectMode() == CalendarView.SelectMode.SINGLE) {
                holder.root.setCurrentState(CalendarCell.State.NONE);

                if (monthPosition == calendarView.getCurrentMonthPosition() &&
                        position == calendarView.getCurrentDayPosition()) {
                    holder.textView.setAlpha(1);
                    holder.textView.setTextColor(Color.WHITE);
                    holder.textView.setBackgroundResource(R.drawable.day_item_bg);
                } else {
                    if (monthPosition == calendarView.getCurrentMonthPosition()) {
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bg_bottom));
                    } else {
                        holder.textView.setAlpha(0.5f);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bg_bottom));
                    }
                    holder.textView.setBackgroundResource(0);

                }
            } else {
                // Select Range
                int currentDayPosition = calendarView.getCurrentDayPosition();
                int currentMonthPosition = calendarView.getCurrentMonthPosition();
                int multiplyEndDayPosition = calendarView.getMultiplyEndDayPosition();
                int multiplyEndMonthPosition = calendarView.getMultiplyEndMonthPosition();

                // select one
                if (calendarView.getCurrentSelectState() == CalendarView.SelectState.ONE) {
                    if ((monthPosition == currentMonthPosition &&
                            position == currentDayPosition)) {
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(Color.WHITE);
                        holder.textView.setBackgroundResource(R.drawable.day_item_bg);
                        holder.root.setCurrentState(CalendarCell.State.NONE);
                    } else {
                        holder.root.setCurrentState(CalendarCell.State.NONE);
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bg_bottom));
                        holder.textView.setBackgroundResource(0);
                    }
                } else { // select two

                    //  less than  start or  more than end
                    if ((monthPosition < currentMonthPosition) ||
                            (monthPosition == currentMonthPosition && position < currentDayPosition) ||
                            (monthPosition == multiplyEndMonthPosition && position > multiplyEndDayPosition) ||
                            (monthPosition > multiplyEndMonthPosition)
                    ) {
                        holder.root.setCurrentState(CalendarCell.State.NONE);
//                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bg_bottom));
                        holder.textView.setBackgroundResource(0);
                    }  //  start day
                    else if ((monthPosition == currentMonthPosition && position == currentDayPosition)) {
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(Color.WHITE);
                        holder.textView.setBackgroundResource(R.drawable.day_item_bg);
                        if ((position + 1) % calendarView.getCurrentDisplayMode().days == 0 || position == maxDay - 1) {
                            holder.root.setCurrentState(CalendarCell.State.NONE);
                        } else {
                            holder.root.setCurrentState(CalendarCell.State.RIGHT);
                        }
                    } // end day
                    else if (monthPosition == multiplyEndMonthPosition && position == multiplyEndDayPosition) {
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(Color.WHITE);
                        holder.textView.setBackgroundResource(R.drawable.day_item_bg);
                        // weekStart or  Monday
                        if (position == weekStart - 1 || position % calendarView.getCurrentDisplayMode().days == 0) {
                            holder.root.setCurrentState(CalendarCell.State.NONE);
                        } else {
                            holder.root.setCurrentState(CalendarCell.State.LEFT);
                        }
                    }//  day in between start and end
                    else {
                        //  in the same month
                        holder.textView.setAlpha(1);
                        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bg_bottom));
                        holder.textView.setBackgroundResource(0);

                        if (currentMonthPosition == multiplyEndMonthPosition) {
                            //  position < multiplyEndDayPosition  always true
                            if (position > currentDayPosition) {
                                if ((position + 1) % calendarView.getCurrentDisplayMode().days == 0) {
                                    holder.root.setCurrentState(CalendarCell.State.LEFT);
                                } else if (position % calendarView.getCurrentDisplayMode().days == 0) {
                                    holder.root.setCurrentState(CalendarCell.State.RIGHT);
                                } else {
                                    holder.root.setCurrentState(CalendarCell.State.ALL);
                                }
                            }
                        } else {
                            if ((monthPosition == currentMonthPosition && position > currentDayPosition) ||
                                    (monthPosition > currentMonthPosition && monthPosition < multiplyEndMonthPosition) ||
                                    (monthPosition == multiplyEndMonthPosition)) {
                                if (position == weekStart - 1) {
                                    if (position + 1 == calendarView.getCurrentDisplayMode().days) {
                                        holder.root.setCurrentState(CalendarCell.State.ELSE);
                                    } else {
                                        holder.root.setCurrentState(CalendarCell.State.RIGHT);
                                    }
                                } else if (position == maxDay - 1) {
                                    holder.root.setCurrentState(CalendarCell.State.LEFT);
                                } else if ((position + 1) % calendarView.getCurrentDisplayMode().days == 0) {
                                    holder.root.setCurrentState(CalendarCell.State.LEFT);
                                } else if (position % calendarView.getCurrentDisplayMode().days == 0) {
                                    holder.root.setCurrentState(CalendarCell.State.RIGHT);
                                } else {
                                    holder.root.setCurrentState(CalendarCell.State.ALL);
                                }
                            }
                        }

                    }

                }
//                Log.e(TAG, "onBindViewHolder: " + monthPosition + " " + position);

            }

        } else {
            holder.textView.setEnabled(false);
            holder.textView.setTextColor(Color.parseColor("#EEEEEE"));
            holder.textView.setBackgroundResource(0);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class MonthHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CalendarCell root;

        public MonthHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_day);
            root = itemView.findViewById(R.id.ll_root);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

}

