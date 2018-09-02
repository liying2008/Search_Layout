package scut.carson_ho.search_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import scut.carson_ho.searchview.SearchFragment;
import scut.carson_ho.searchview.listener.OnSearchListener;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/2 16:37
 * Description:
 * Remarks:
 * =======================================================
 */
public class SearchFragmentDemoActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fragment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SearchFragment");   //标题
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            searchFragment.setOnSearchListener(new OnSearchListener() {
                @Override
                public void onSearch(String keyword) {
                    Toast.makeText(SearchFragmentDemoActivity.this, "搜索关键词：" + keyword, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                break;
        }
        return true;
    }
}