package cc.duduhuo.searchview.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.LocaleListCompat;

import cc.duduhuo.searchview.SearchFragment;
import cc.duduhuo.searchview.listener.OnSearchListener;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/2 16:37
 * Description:
 * Remarks:
 * =======================================================
 */
public class SearchFragmentDemoActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Button btnThemeToggle;
    private Button btnLanguageToggle;
    private Toolbar toolbar;

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_fragment);

        btnThemeToggle = findViewById(R.id.theme_toggle);
        btnLanguageToggle = findViewById(R.id.language_toggle);

        // 获取当前主题
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        final boolean isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

        // 设置按钮文本
        if (isDarkMode) {
            btnThemeToggle.setText(R.string.switch_to_light_theme);
        } else {
            btnThemeToggle.setText(R.string.switch_to_dark_theme);
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

        // 点击切换语言
        btnLanguageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleListCompat currentAppLocales = AppCompatDelegate.getApplicationLocales();
                if (currentAppLocales.isEmpty() || "zh".equalsIgnoreCase(currentAppLocales.get(0).getLanguage())) {
                    // 当前是中文或默认，切换到英文
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
                } else {
                    // 当前是英文，切换到中文
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("zh"));
                }
            }
        });

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
        if (item.getItemId() == R.id.action_search) {
            searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
        }
        return true;
    }
}