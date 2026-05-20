# 保留 SearchView 及其公共 API
-keep class cc.duduhuo.searchview.SearchView {
    public *;
}

# 保留自定义 View 及其构造函数（供布局文件反射使用）
-keep class cc.duduhuo.searchview.ClearEditText {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class cc.duduhuo.searchview.DrawableCenterTextView {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留回调接口
-keep interface cc.duduhuo.searchview.listener.** { *; }

# 保留 SearchFragment
-keep class cc.duduhuo.searchview.SearchFragment {
    public *;
}

# 数据库相关类如果通过反射访问也需要保留（可选，视具体实现而定）
-keep class cc.duduhuo.searchview.persistence.** { *; }
