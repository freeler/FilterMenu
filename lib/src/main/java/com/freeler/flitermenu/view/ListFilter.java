package com.freeler.flitermenu.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeler.flitermenu.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public class ListFilter<T> extends Filter<T> {

    private List<T> data = new ArrayList<>();
    private boolean isNeedAll = false;

    public ListFilter(Context context) {
        super(context);
    }

    public ListFilter<T> setOptions(@NonNull List<T> data) {
        this.data = data;
        return this;
    }

    public ListFilter<T> setNeedAll(boolean isNeedAll) {
        this.isNeedAll = isNeedAll;
        return this;
    }


    @Override
    public void onOpen() {

    }


    @Override
    public View getView() {
        RecyclerView recyclerView = getRecyclerView();
        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private RecyclerView getRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        return recyclerView;
    }

    @SuppressWarnings("unchecked")
    private RecyclerView.Adapter getAdapter() {
        if (isNeedAll) data.add(0, null);
        ListFilterAdapter adapter = new ListFilterAdapter(getContext(), data, getDisplayConvert());
        adapter.setOnItemClickListener(new ListFilterAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                changeValueWithHide(data.get(position));
            }
        });
        return adapter;
    }


}
