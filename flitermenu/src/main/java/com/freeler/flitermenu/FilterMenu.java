package com.freeler.flitermenu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选项
 *
 * @author: xuzeyang
 * @Date: 2020/1/9
 */
public class FilterMenu extends LinearLayout {

    private static final int DEFAULT_MENU_HEIGHT = dp2Px(32);
    private static final int DEFAULT_DIVIDER_HEIGHT = dp2Px(10);
    private static final int DEFAULT_DIVIDER_MARGIN = dp2Px(0);

    /**
     * 选中文字颜色
     */
    private int textSelectedColor = 0xff999999;
    /**
     * 未选中文字颜色
     */
    private int textUnselectedColor = 0xff000000;
    /**
     * 筛选menu背景色
     */
    private int menuBackgroundColor = 0xffff00ff;
    /**
     * 分割线背景色
     */
    private int underLineBackgroundColor = 0xffffff00;


    /**
     * 筛选menu高度
     */
    private int menuHeight = DEFAULT_MENU_HEIGHT;
    /**
     * 分割线高度
     */
    private int dividerLineHeight = DEFAULT_DIVIDER_HEIGHT;
    /**
     * 分割线左边距
     */
    private int dividerMarginLeft = DEFAULT_DIVIDER_MARGIN;
    /**
     * 分割线右边距
     */
    private int dividerMarginRight = DEFAULT_DIVIDER_MARGIN;


    /**
     * 筛选栏
     */
    private LinearLayout menuView;


    // tabMenuView里面选中的tab位置，-1表示未选中
    private int currentTabPosition = -1;


    public FilterMenu(Context context) {
        this(context, null, 0);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FilterMenu);
        textSelectedColor = typedArray.getColor(R.styleable.FilterMenu_textSelectedColor, textSelectedColor);
        textUnselectedColor = typedArray.getColor(R.styleable.FilterMenu_textUnSelectedColor, textUnselectedColor);
        menuBackgroundColor = typedArray.getColor(R.styleable.FilterMenu_menuBackgroundColor, menuBackgroundColor);
        underLineBackgroundColor = typedArray.getColor(R.styleable.FilterMenu_dividerBackgroundColor, underLineBackgroundColor);

        menuHeight = typedArray.getDimensionPixelSize(R.styleable.FilterMenu_menuHeight, menuHeight);
        dividerLineHeight = typedArray.getDimensionPixelSize(R.styleable.FilterMenu_dividerLineHeight, dividerLineHeight);
        dividerMarginLeft = typedArray.getDimensionPixelSize(R.styleable.FilterMenu_dividerMarginLeft, dividerMarginLeft);
        dividerMarginRight = typedArray.getDimensionPixelSize(R.styleable.FilterMenu_dividerMarginRight, dividerMarginRight);


        createMenuView(context);

        typedArray.recycle();
    }

    private void createMenuView(Context context) {
        menuView = new LinearLayout(context);
        menuView.setOrientation(HORIZONTAL);
        menuView.setBackgroundColor(menuBackgroundColor);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = menuHeight;
        menuView.setLayoutParams(layoutParams);
        addView(menuView, 0);

        // 下划线、分割线
        View dividerView = new View(context);
        dividerView.setBackgroundColor(underLineBackgroundColor);
        LayoutParams dividerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerLineHeight);
        dividerParams.setMargins(dividerMarginLeft, 0, dividerMarginRight, 0);
        dividerView.setLayoutParams(dividerParams);
        addView(dividerView, 1);
    }

    public void setMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews) {
        ArrayList<Boolean> list = new ArrayList<>();
        for (String tabText : tabTexts) {
            list.add(true);
        }
        setMenu(tabTexts, popupViews, list);
    }

    public void setMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews, @NonNull List<Boolean> isValueNoNull) {
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }
        menuView.removeAllViews();
//        menuView.addView();
    }

    private void addTab(String tab, boolean isLast) {


    }

    public void setTabText(String tabText) {
        if (currentTabPosition != -1) {
            TextView tabMenuTextView = getTabMenuTextView(currentTabPosition);
            if (tabMenuTextView != null) {
                tabMenuTextView.setText(tabText);
            }
        }
    }

    /**
     * 获取TabMenu的TextView
     *
     * @param position 第几项
     * @return TextView
     */
    private TextView getTabMenuTextView(int position) {
//        View childViewAt = tabMenuView.getChildAt(position);
//        if (childViewAt instanceof LinearLayout) {
//            View childAt = ((LinearLayout) childViewAt).getChildAt(0);
//            if (childAt instanceof TextView) {
//                return (TextView) childAt;
//            }
//        }
        return null;
    }

    public void getTabText() {
//        TextView textView = new TextView(getContext());
//        textView.setMaxLines(1);
//        textView.setEllipsize(TextUtils.TruncateAt.END);
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, );
//        textView.setTextColor();
//        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(isValueNull ? menuUnselectedIcon : menuSelectedIcon), null);
//        textView.setCompoundDrawablePadding(dp2Px(4));
//        textView.setText(tabTexts.get(i));
    }

    /**
     * DropDownMenu是否处于可见状态
     */
    public boolean isShowing() {
        return currentTabPosition != -1;
    }

    public void closeMenu() {
    }

    private OnMenuShowListener onMenuShowListener;

    public void setOnMenuShowListener(OnMenuShowListener onMenuShowListener) {
        this.onMenuShowListener = onMenuShowListener;
    }

    public interface OnMenuShowListener {
        void showAtPosition(int position);
    }

    private static int dp2Px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics()) + 0.5);
    }

    private static int sp2px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics()) + 0.5);
    }

    private Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

}
