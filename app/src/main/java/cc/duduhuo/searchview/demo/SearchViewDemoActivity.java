package cc.duduhuo.searchview.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import cc.duduhuo.searchview.SearchView;
import cc.duduhuo.searchview.listener.OnBackListener;
import cc.duduhuo.searchview.listener.OnSearchListener;

/**
 * Created by Carson_Ho on 17/8/11.
 */

public class SearchViewDemoActivity extends AppCompatActivity {

    private Button btnThemeToggle;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnThemeToggle = findViewById(R.id.theme_toggle);

        // 获取当前主题
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        final boolean isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

        // 设置按钮文本
        if (isDarkMode) {
            btnThemeToggle.setText("切换为明亮主题");
        } else {
            btnThemeToggle.setText("切换为黑暗主题");
        }

        // 点击切换亮暗主题
        btnThemeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDarkMode) {
                    // 当前是深色模式，切换到亮色模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    // 当前是亮色模式或未指定，切换到深色模式
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                // 重新启动 Activity 以应用更改
                recreate();
            }
        });

        // 搜索框组件
        searchView = findViewById(R.id.search_view);
        // 是否在点击历史条目后启动搜索
        searchView.startSearchWhenHistoryItemClick = true;
        // 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnSearchListener(new OnSearchListener() {
            @Override
            public void onSearch(String keyword) {
                Toast.makeText(SearchViewDemoActivity.this, "搜索关键词：" + keyword, Toast.LENGTH_SHORT).show();
                // 也可通过 getSearchText() 方法获取搜索框中的内容
                String searchText = searchView.getSearchText();
                System.out.println(searchText);
                // 如果需要自定义搜索按钮，可以在按钮的click事件中调用 startSearch() 方法。
                // searchView.startSearch();
            }
        });
        // 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnBackListener(new OnBackListener() {
            @Override
            public void onBack() {
                finish();
            }
        });
    }
}