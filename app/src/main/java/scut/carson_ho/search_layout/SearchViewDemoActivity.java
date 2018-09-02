package scut.carson_ho.search_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import scut.carson_ho.searchview.listener.OnSearchListener;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.listener.OnBackListener;

/**
 * Created by Carson_Ho on 17/8/11.
 */

public class SearchViewDemoActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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