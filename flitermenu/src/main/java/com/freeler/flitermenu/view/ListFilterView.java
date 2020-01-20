package com.freeler.flitermenu.view;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeler.flitermenu.FilterView;

import java.util.List;

/**
 * @author: xuzeyang
 * @Date: 2020/1/13
 */
public class ListFilterView<T> extends FilterView<T> {

    private List<T> data;
    private boolean isNeedAll = false;

    public ListFilterView(Context context) {
        super(context);
    }

    public ListFilterView<T> setOptions(List<T> data) {
        this.data = data;
        return this;
    }

    public ListFilterView<T> isNeedAll(boolean isNeedAll) {
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
        return recyclerView;
    }

    @SuppressWarnings("unchecked")
    private RecyclerView.Adapter getAdapter() {
        if (isNeedAll) data.add(0, null);
        ListFilterAdapter adapter = new ListFilterAdapter(getContext(), data, getDisplayConvert());
        adapter.setOnItemClickListener(new ListFilterAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                getMenuHelper().hide();
                if (isValueChange(getValue(), data.get(position))) {
                    getOnFilterValueChangeListener().changed(ListFilterView.this);
                }
            }
        });
        return adapter;
    }


}
