package com.freeler.flitermenu.helper.list;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freeler.flitermenu.utils.ConvertUtils;


/**
 * 列表分隔
 *
 * @author: xuzeyang
 * @Date: 2020/3/12
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 分隔线高度
     */
    private int dividerSpace = ConvertUtils.dp2Px(0.5f);
    private int dividerMargin = ConvertUtils.dp2Px(10f);
    private Paint paint;

    public ListItemDecoration() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#EEEEEE"));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerSpace;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft() + dividerMargin;
        int right = parent.getWidth() - parent.getPaddingRight() - dividerMargin;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerSpace;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
