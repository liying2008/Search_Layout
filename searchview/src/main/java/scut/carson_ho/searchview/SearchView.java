package scut.carson_ho.searchview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.listener.OnBackListener;
import scut.carson_ho.searchview.listener.OnSearchListener;

/**
 * Created by Carson_Ho on 17/8/10.
 * Modified by LiYing on 18/8.
 */
public class SearchView extends LinearLayout {

    private Context context;

    // 搜索框组件
    private ClearEditText etSearch; // 搜索按键
    private TextView tvClear;  // 删除搜索记录按键
    private LinearLayout searchBlock; // 搜索框布局
    private ImageView ivBack; // 返回按键
    private Button btnSearch;   // 搜索按钮


    // ListView列表 & 适配器
    private SearchListView listView;
    private BaseAdapter adapter;

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    // 回调接口
    private OnSearchListener mOnSearchListener;// 搜索按键回调接口
    private OnBackListener mOnBackListener; // 返回按键回调接口

    // 自定义属性设置
    // 1. 搜索字体属性设置：大小、颜色 & 默认提示
    private float searchTextSize;
    private ColorStateList searchTextColor;
    private String searchTextHint;
    private ColorStateList iconColor;
    private ColorStateList backIconColor;
    private ColorStateList searchIconColor;
    private ColorStateList deleteIconColor;

    private boolean searchIconVisible;

    // 搜索框设置
    @ColorInt
    private int searchBlockColor;
    @DrawableRes
    private int searchBlockBackground;
    private float searchBlockHeight;
    // EditText 背景
    @DrawableRes
    private int searchTextBackground;

    // 清除搜索历史设置
    private String clearHistoryText;
    private ColorStateList clearHistoryTextColor;
    private float clearHistoryTextSize;
    @DrawableRes
    private int clearHistoryTextBackground;

    // 搜索按钮设置
    private String searchButtonText;
    @DrawableRes
    private int searchButtonBackground;
    private ColorStateList searchButtonTextColor;
    private float searchButtonTextSize;
    private float searchButtonWidth;
    private boolean searchButtonVisible;

    // 所有历史记录
    private List<String> records = new ArrayList<>();

    /**
     * 构造函数
     * 作用：对搜索框进行初始化
     */
    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs); // ->>关注a
        init();// ->>关注b
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    /**
     * 关注a
     * 作用：初始化自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        // 控件资源名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView);

        // 搜索框字体大小（sp）
        float searchTextSizeDefault = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, getResources().getDisplayMetrics());
        searchTextSize = typedArray.getDimension(R.styleable.SearchView_searchTextSize, searchTextSizeDefault);

        // 搜索框字体颜色
        searchTextColor = typedArray.getColorStateList(R.styleable.SearchView_searchTextColor);
        if (searchTextColor == null) {
            searchTextColor = context.getResources().getColorStateList(R.color.colorText); // 默认颜色 = 灰色
        }

        // 搜索框提示内容（String）
        searchTextHint = typedArray.getString(R.styleable.SearchView_searchTextHint);
        if (searchTextHint == null) {
            searchTextHint = getResources().getText(R.string.please_input_keyword).toString();
        }
        // 搜索框颜色
        int searchBlockColorDefault = context.getResources().getColor(R.color.colorDefault); // 默认颜色 = 白色
        searchBlockColor = typedArray.getColor(R.styleable.SearchView_searchBlockColor, searchBlockColorDefault);
        // 搜索框背景
        searchBlockBackground = typedArray.getResourceId(R.styleable.SearchView_searchBlockBackground, 0);
        // 搜索框高度
        searchBlockHeight = typedArray.getDimension(R.styleable.SearchView_searchBlockHeight, 0);
        // EditText 背景
        searchTextBackground = typedArray.getResourceId(R.styleable.SearchView_searchTextBackground, 0);

        // 图标颜色（返回图标、搜索图标和清除图标）
        iconColor = typedArray.getColorStateList(R.styleable.SearchView_iconColor);

        // 搜索图标颜色
        searchIconColor = typedArray.getColorStateList(R.styleable.SearchView_searchIconColor);

        // 返回图标颜色
        backIconColor = typedArray.getColorStateList(R.styleable.SearchView_backIconColor);

        // 清除图标颜色
        deleteIconColor = typedArray.getColorStateList(R.styleable.SearchView_deleteIconColor);
        // 搜索图标可见性
        searchIconVisible = typedArray.getBoolean(R.styleable.SearchView_searchIconVisible, true);

        // 清除搜索历史文字
        clearHistoryText = typedArray.getString(R.styleable.SearchView_clearHistoryText);
        if (clearHistoryText == null) {
            clearHistoryText = getResources().getText(R.string.clear_history).toString();
        }
        // 清除搜索历史文字大小
        float clearHistoryTextSizeDefault = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, getResources().getDisplayMetrics());
        clearHistoryTextSize = typedArray.getDimension(R.styleable.SearchView_clearHistoryTextSize, clearHistoryTextSizeDefault);
        // 清除搜索历史文字颜色
        clearHistoryTextColor = typedArray.getColorStateList(R.styleable.SearchView_clearHistoryTextColor);
        if (clearHistoryTextColor == null) {
            clearHistoryTextColor = getResources().getColorStateList(R.color.clear_history_text_color);
        }
        // 清除搜索历史文字背景
        clearHistoryTextBackground = typedArray.getResourceId(R.styleable.SearchView_clearHistoryTextBackground, R.drawable.clear_history_background_color_default);

        // 搜索按钮是否显示
        searchButtonVisible = typedArray.getBoolean(R.styleable.SearchView_searchButtonVisible, false);
        // 搜索按钮文字
        searchButtonText = typedArray.getString(R.styleable.SearchView_searchButtonText);
        if (searchButtonText == null) {
            searchButtonText = getResources().getText(R.string.search).toString();
        }
        // 搜索按钮文字大小
        float searchButtonTextSizeDefault = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, getResources().getDisplayMetrics());
        searchButtonTextSize = typedArray.getDimension(R.styleable.SearchView_searchButtonTextSize, searchButtonTextSizeDefault);
        // 搜索按钮文字颜色
        searchButtonTextColor = typedArray.getColorStateList(R.styleable.SearchView_searchButtonTextColor);
        if (searchButtonTextColor == null) {
            searchButtonTextColor = getResources().getColorStateList(R.color.search_text_color_default);
        }
        // 搜索按钮背景颜色
        searchButtonBackground = typedArray.getResourceId(R.styleable.SearchView_searchButtonBackground, 0);
        // 搜索按钮宽度
        float searchButtonWidthDefault = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60F, getResources().getDisplayMetrics());
        searchButtonWidth = typedArray.getDimension(R.styleable.SearchView_searchButtonWidth, searchButtonWidthDefault);
        // 释放资源
        typedArray.recycle();
    }


    /**
     * 关注b
     * 作用：初始化搜索框
     */
    private void init() {
        // 1. 初始化UI组件->>关注c
        initView();

        // 2. 实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(context);

        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");

        // "清空搜索历史" 按钮
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 清空数据库->>关注2
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");
            }
        });
        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

        /*
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    startSearch();
                }
                return false;
            }
        });


        /*
         * 搜索框的文本变化实时监听
         */
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = etSearch.getText().toString();
                queryData(tempName); // ->>关注1
            }
        });


        /*
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                etSearch.setText(name);
                etSearch.setSelection(name.length());
            }
        });

        /*
         * 点击返回按键后的事件
         */
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注：由于返回需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                if (!(mOnBackListener == null)) {
                    mOnBackListener.onBack();
                }
            }
        });
    }


    /**
     * 关注c：绑定搜索框xml视图
     */
    private void initView() {

        // 1. 绑定 R.layout.search_layout 作为搜索框的xml文件
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);

        // 2. 绑定搜索框 EditText
        etSearch = (ClearEditText) findViewById(R.id.et_search);
        etSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, searchTextSize);
        etSearch.setTextColor(searchTextColor);
        etSearch.setHint(searchTextHint);
        etSearch.setBackgroundResource(searchTextBackground);
        // 搜索框获得焦点
        etSearch.requestFocus();

        // 设置搜索图标颜色
        if (searchIconColor != null) {
            etSearch.setSearchIconColor(searchIconColor);
        } else if (iconColor != null) {
            etSearch.setSearchIconColor(iconColor);
        }

        etSearch.setSearchIconVisible(searchIconVisible);

        // 设置清除图标颜色
        if (deleteIconColor != null) {
            etSearch.setClearIconColor(deleteIconColor);
        } else if (iconColor != null) {
            etSearch.setClearIconColor(iconColor);
        }

        // 3. 搜索框背景颜色
        searchBlock = (LinearLayout) findViewById(R.id.search_block);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) searchBlock.getLayoutParams();
        searchBlock.setBackgroundColor(searchBlockColor);
        if (searchBlockBackground != 0) {
            searchBlock.setBackgroundResource(searchBlockBackground);
        }
        if (searchBlockHeight != 0) {
            params.height = (int) searchBlockHeight;
        }
        searchBlock.setLayoutParams(params);

        // 4. 历史搜索记录 = ListView显示
        listView = (SearchListView) findViewById(R.id.listView);

        // 5. 删除历史搜索记录 按钮
        tvClear = (TextView) findViewById(R.id.tv_clear);
        tvClear.setVisibility(INVISIBLE);
        tvClear.setText(clearHistoryText);
        tvClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, clearHistoryTextSize);
        tvClear.setTextColor(clearHistoryTextColor);
        tvClear.setBackgroundResource(clearHistoryTextBackground);

        // 6. 返回按键
        ivBack = (ImageView) findViewById(R.id.search_back);

        // 设置返回图标颜色
        if (backIconColor != null) {
            ivBack.setColorFilter(backIconColor.getDefaultColor());
        } else if (iconColor != null) {
            ivBack.setColorFilter(iconColor.getDefaultColor());
        }

        // 搜索按钮
        btnSearch = findViewById(R.id.btn_search);
        if (searchButtonVisible) {
            LinearLayout.LayoutParams btnSearchParams = (LinearLayout.LayoutParams) btnSearch.getLayoutParams();
            btnSearch.setVisibility(VISIBLE);
            btnSearch.setText(searchButtonText);
            btnSearch.setTextColor(searchButtonTextColor);
            btnSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, searchButtonTextSize);
            btnSearch.setBackgroundResource(searchButtonBackground);
            btnSearchParams.width = (int) searchButtonWidth;
            btnSearch.setLayoutParams(btnSearchParams);
        } else {
            btnSearch.setVisibility(GONE);
        }
        // 创建适配器
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, records);
        // 设置适配器
        listView.setAdapter(adapter);

    }

    /**
     * 获取搜索关键词
     *
     * @return
     */
    public String getSearchText() {
        Editable text = etSearch.getText();
        return text != null ? text.toString() : "";
    }

    /**
     * 开始搜索
     */
    public void startSearch() {
        String searchText = etSearch.getText().toString();
        // 1. 点击搜索按键后，根据输入的搜索字段进行查询
        // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
        if (!(mOnSearchListener == null)) {
            mOnSearchListener.onSearch(searchText);
        }

        // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
        String trimmedText = searchText.trim();
        if (!trimmedText.isEmpty()) {
            boolean hasData = hasData(trimmedText);
            // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
            if (!hasData) {
                insertData(trimmedText);
            }
        }
        queryData("");
    }

    /**
     * 关注1
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {
        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id, name from records where name like ? order by id desc",
                new String[]{"%" + tempName + "%"});

        records.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            records.add(name);
        }
        cursor.close();
        adapter.notifyDataSetChanged();

        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0) {
            tvClear.setVisibility(VISIBLE);
        } else {
            tvClear.setVisibility(INVISIBLE);
        }
    }

    /**
     * 关注2：清空数据库
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tvClear.setVisibility(INVISIBLE);
    }

    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id, name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        boolean hasNext = cursor.moveToNext();
        cursor.close();
        return hasNext;
    }

    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values(?)", new String[]{tempName});
        db.close();
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     *
     * @param onSearchListener
     */
    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.mOnSearchListener = onSearchListener;
    }

    /**
     * 点击返回后的操作，用于接口回调
     *
     * @param onBackListener
     */
    public void setOnBackListener(OnBackListener onBackListener) {
        this.mOnBackListener = onBackListener;
    }
}
