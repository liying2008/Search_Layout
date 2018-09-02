package scut.carson_ho.searchview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import scut.carson_ho.searchview.listener.OnBackListener;
import scut.carson_ho.searchview.listener.OnSearchListener;
import scut.carson_ho.searchview.util.InputMethodUtils;


/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/2 17:07
 * Description: 封装的搜索组件 DialogFragment
 * Remarks:
 * =======================================================
 */
public class SearchFragment extends DialogFragment {
    public static final String TAG = SearchFragment.class.getName();
    private static final String START_SEARCH_WHEN_HISTORY_ITEM_CLICK = "startSearchWhenHistoryItemClick";
    private static final String CLEAR_CURRENT_SEARCH_TEXT_WHEN_DISMISS = "clearCurrentSearchTextWhenDismiss";
    private SearchView mSearchView;
    private OnSearchListener mOnSearchListener; // 搜索按键回调接口
    private boolean mStartSearchWhenHistoryItemClick;
    private boolean mClearCurrentSearchTextWhenDismiss;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param startSearchWhenHistoryItemClick   当点击历史条目时是否启动搜索.
     * @param clearCurrentSearchTextWhenDismiss 当关闭搜索对话框时是否清除当前搜索内容.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(boolean startSearchWhenHistoryItemClick,
                                             boolean clearCurrentSearchTextWhenDismiss) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putBoolean(START_SEARCH_WHEN_HISTORY_ITEM_CLICK, startSearchWhenHistoryItemClick);
        args.putBoolean(CLEAR_CURRENT_SEARCH_TEXT_WHEN_DISMISS, clearCurrentSearchTextWhenDismiss);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        return newInstance(true, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStartSearchWhenHistoryItemClick = getArguments().getBoolean(START_SEARCH_WHEN_HISTORY_ITEM_CLICK);
            mClearCurrentSearchTextWhenDismiss = getArguments().getBoolean(CLEAR_CURRENT_SEARCH_TEXT_WHEN_DISMISS);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.SearchDialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchView = view.findViewById(R.id.search_view);
        mSearchView.startSearchWhenHistoryItemClick = mStartSearchWhenHistoryItemClick;
        mSearchView.setOnSearchListener(new OnSearchListener() {
            @Override
            public void onSearch(String keyword) {
                if (mOnSearchListener != null) {
                    mOnSearchListener.onSearch(keyword);
                }
                dismiss();
            }
        });
        mSearchView.setOnBackListener(new OnBackListener() {
            @Override
            public void onBack() {
                dismiss();
            }
        });
        mSearchView.focus();
        InputMethodUtils.openKeyboard(getContext(), mSearchView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    /**
     * 初始化 SearchFragment
     */
    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.TOP);
        // 取消Dialog淡入和淡出动画
        window.setWindowAnimations(R.style.EmptyAnimation);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        InputMethodUtils.closeKeyboard(getContext(), mSearchView);
        super.onDismiss(dialog);
        if (mClearCurrentSearchTextWhenDismiss) {
            mSearchView.clearCurrentSearchText();
        }
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     *
     * @param onSearchListener
     */
    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.mOnSearchListener = onSearchListener;
    }
}
