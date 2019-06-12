package com.xchallenge.tnt.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xchallenge.tnt.R;
import com.xchallenge.tnt.views.bean.DayInfo;
import com.xchallenge.tnt.views.bean.MonthInfo;
import com.xchallenge.tnt.views.calender.CalendarView;
import com.xchallenge.tnt.views.interfaze.OnRecycleViewItemClickListener;

import java.util.List;

/**
 * Created by ez44446 on 2019/5/14.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {

    private static final String TAG = "CalendarAdapter";

    Context context;
    List<MonthInfo> list;
//    CalendarView.DisplayMode displayMode;
//    CalendarView.SelectMode selectMode;
    CalendarView calendarView;

    private RecyclerView.RecycledViewPool viewPool;

    public CalendarAdapter() {
    }

    public CalendarAdapter(Context context, CalendarView calendarView, List<MonthInfo> list) {
        this.context = context;
        this.list = list;
        this.calendarView = calendarView;
//        this.displayMode = displayMode;
//        this.selectMode = selectMode;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_month_item, parent, false);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
//        if (payloads.isEmpty()) {
//            onBindViewHolder(holder, position);
//        } else {
//
//        }
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, final int position) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, calendarView.getCurrentDisplayMode().days);
        holder.recyclerView.setLayoutManager(layoutManager);

        holder.recyclerView.setItemViewCacheSize(200);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
//        holder.recyclerView.setRecycledViewPool(viewPool);

        MonthInfo monthInfo = list.get(position);

        if (monthInfo.getAdapter() == null) {
            final MonthAdapter monthAdapter = new MonthAdapter(context, calendarView, list.get(position), position);
            holder.recyclerView.setAdapter(monthAdapter);
            monthInfo.setAdapter(monthAdapter);

            monthAdapter.setOnRecycleViewItemClickListener(new OnRecycleViewItemClickListener() {
                @Override
                public void onClick(View view, int index) {

                    DayInfo dayInfo = CalendarAdapter.this.list.get(position).getList().get(index);
                    String date = dayInfo.getDate();
                    if (!dayInfo.isEnable()) {
                        return;
                    }


                    int oriMonthPosition = calendarView.getCurrentMonthPosition();
                    int oriDayPosition = calendarView.getCurrentDayPosition();

                    if (calendarView.getCurrentSelectMode() == CalendarView.SelectMode.SINGLE) {
                        calendarView.setCurrentPosition(position, index);

                        calendarView.setCurrentDate(date, false);

//                        Log.e(TAG, "onBindViewHolder: " +
//                                " oriMonthPosition  = " + oriMonthPosition + " oriDayPosition == " + oriDayPosition + " position " + position + " sday == " +
//                                index);

                        if (oriMonthPosition == position) {
                            //  the same month , change two day
                            monthAdapter.notifyItemChanged(oriDayPosition);
                            monthAdapter.notifyItemChanged(index);
                        } else {
                            // different month
                            calendarView.notifItemChanged();
//                        calendarView.notifItemChanged(oriMonthPosition);
//                        calendarView.notifItemChanged(position);
                        }
                    } else {
                        CalendarView.SelectState state = calendarView.getCurrentSelectState();
                        calendarView.setMultiplyEndDate("", false);
                        // has select two
                        if (state == CalendarView.SelectState.TWO) {
                            // change currentDate
                            calendarView.setCurrentDate(date, false);
                            calendarView.setCurrentPosition(position, index);
                            calendarView.setMultiplyEndPosition(-1, -1);
                            calendarView.setCurrentSelectState(CalendarView.SelectState.ONE);
                            calendarView.notifItemChanged();
                        } else {
                            // the same
                            if (position == oriMonthPosition && index == oriDayPosition) {
                                return;
                            }

                            if (position > oriMonthPosition || (position == oriMonthPosition && index > oriDayPosition)) {
                                // select two   setendDate
                                calendarView.setCurrentSelectState(CalendarView.SelectState.TWO);
                                calendarView.setMultiplyEndDate(date, false);
                                calendarView.setMultiplyEndPosition(position, index);
                                calendarView.notifItemChanged();
                            } else {
                                // still SelectState.ONE  change currentDate
                                calendarView.setCurrentDate(date, false);
                                calendarView.setCurrentPosition(position, index);
                                calendarView.notifItemChanged();
                            }
                        }
                    }
                }
            });
        } else {
            //  mode could be changed
            monthInfo.getAdapter().checkWeekStart();
            monthInfo.getAdapter().notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class CalendarHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        CalendarHolder(View itemView) {
            super(itemView);
//            recyclerView = (RecyclerView) itemView;
            recyclerView = itemView.findViewById(R.id.rv_month);
        }
    }

//    public interface OnDaySelectListener {
//        void onDaySelect(int monthIndex, int dayIndex);
//    }
//
//    private OnDaySelectListener onDaySelectListener;
//
//    public void setOnDaySelectListener(OnDaySelectListener daySelectListener) {
//        this.onDaySelectListener = daySelectListener;
//    }


}
