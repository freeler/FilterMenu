package com.freeler.flitermenu.helper.grid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 网格列表分隔
 *
 * @author: xuzeyang
 * @Date: 2020/3/12
 */
public class GridVerticalItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 一行最多显示的数量
     */
    private int spanCount;
    /**
     * 列表项垂直间距
     */
    private int itemVerticalSpace;
    /**
     * 列表项水平间距
     */
    private int itemHorizontalSpace;

    public GridVerticalItemDecoration(int spanCount, int itemVerticalSpace, int itemHorizontalSpace) {
        this.spanCount = spanCount;
        this.itemVerticalSpace = itemVerticalSpace;
        this.itemHorizontalSpace = itemHorizontalSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        boolean isLineLast = (parent.getChildLayoutPosition(view) + 1) % spanCount == 0;
        outRect.right = isLineLast ? 0 : itemHorizontalSpace;
        if (parent.getChildLayoutPosition(view) >= spanCount) {
            outRect.top = itemVerticalSpace;
        } else {
            outRect.top = 0;
        }
    }
}
