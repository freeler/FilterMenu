package com.freeler.flitermenu.helper.grid;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author: xuzeyang
 * @Date: 2020/3/12
 */
public class GridVerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int itemNum;
    private int itemSpace;
    private int itemMargin;

    public GridVerticalItemDecoration(int itemNum, int itemSpace, int itemMargin) {
        this.itemNum = itemNum;
        this.itemSpace = itemSpace;
        this.itemMargin = itemMargin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        boolean isLineLast = (parent.getChildLayoutPosition(view) + 1) % itemNum == 0;
        outRect.right = isLineLast ? 0 : itemMargin;
        if (parent.getChildLayoutPosition(view) >= itemNum) {
            outRect.top = itemSpace;
        } else {
            outRect.top = 0;
        }
    }
}
