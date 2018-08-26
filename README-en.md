# SearchLayout

>[中文文档](README.md)
- Original Author：Carson_Ho
- Modifier: LiYing

## 1. Introduction
A simple & useful  Android DIY View  for Searching function
>[LiYing's Github：SearchLayout](https://github.com/liying2008/Search_Layout)，welcome  Star ！

![Demo](screenshots/search_view_demo.gif)

## 2. Application Scenarios
Searching  & High Diy style

## 3. Feature
- Fresh & concise style
- Easy to use
- Secondary Programming costs are low

## 4. Usage

##### Step 1：Import Library

There are two ways to  import Library：

- For Gradle

*build.gradle*

```gradle
dependencies {
    implementation 'cc.duduhuo:search-view:1.1.0'
}
```

- For Maven

*pom.xml*

```xml
<dependency>
  <groupId>c.duduhuo</groupId>
  <artifactId>search-view</artifactId>
  <version>1.1.0</version>
  <type>pom</type>
</dependency>
```


##### Step 2：Set Animation Attributes

- Attributes Description：

| Attribute | Description | Format | Default Value |
| :------:| :------: | :------: |:------: |
| searchTextSize | 搜索字体大小 | dimension | 12sp |
| searchTextColor | 搜索字体颜色 | color | #9B9B9B |
| searchTextHint | 搜索框编辑框提示内容 | string |输入查询关键字 |
| searchTextBackground | 搜索编辑框背景 | reference | 0 |
| searchBlockColor | 搜索控件背景颜色 | color | #FFFFFF |
| searchBlockBackground | 搜索控件背景 | reference | - |
| searchBlockHeight | 搜索控件高度 | dimension |wrap_content |
| searchButtonText | 搜索按钮文字 | string |搜索 |
| searchButtonBackground | 搜索按钮背景 | reference |0 |
| searchButtonTextColor | 搜索按钮文字颜色 | color |pressed: #888888<br>normal: #606060|
| searchButtonTextSize | 搜索按钮文字大小 | dimension |12sp |
| searchButtonWidth | 搜索按钮宽度 | dimension |60dp |
| searchButtonVisible | 搜索按钮是否可见 | boolean |false |
| iconColor | 所有图标的颜色 | color |#535353 |
| backIconColor | 返回图标的颜色 | color |#535353 |
| searchIconColor | 搜索图标的颜色 | color |#535353 |
| deleteIconColor | 删除图标的颜色 | color | #535353|
| searchIconVisible | 搜索图标是否可见 | boolean |true |
| clearHistoryText | 清除历史记录文字 | string |清除搜索历史 |
| clearHistoryTextColor | 清除历史记录文字颜色 | color |#606060 |
| clearHistoryTextSize | 清除历史记录文字大小 | dimension | 12sp|
| clearHistoryTextBackground | 清除历史记录文字背景 | reference |pressed: #ececec<br>normal: #e2e2e2 |


- Use examples

In layout xml file.

```xml
<scut.carson_ho.searchview.SearchView
    android:id="@+id/search_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:clearHistoryTextSize="14sp"
    app:iconColor="#888888"
    app:searchBlockColor="#ffffff"
    app:searchButtonVisible="false"
    app:searchIconColor="#b9a48e"
    app:searchIconVisible="false"
    app:searchTextBackground="@null"
    app:searchTextColor="@color/colorPrimary"
    app:searchTextHint="输入查询关键字"
    app:searchTextSize="14sp" />
```


### Step 3：Setting actions when clicking search & back button

```java
private SearchView searchView;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    // 搜索框组件
    searchView = (SearchView) findViewById(R.id.search_view);
    // 设置点击搜索按键后的操作（通过回调接口）
    // 参数 = 搜索框输入的内容
    searchView.setOnSearchListener(new OnSearchListener() {
        @Override
        public void onSearch(String keyword) {
            Toast.makeText(SearchDemo.this, "搜索关键词：" + keyword, Toast.LENGTH_SHORT).show();
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
```

## 5. Complete Demo

[LiYing's Github：SearchLayout](https://github.com/liying2008/Search_Layout)


## 6.  LICENSE

[MIT](LICENSE)

## 7. Contribute

Before you open an issue or create a pull request, please read [Contributing Guide](CONTRIBUTING.md) first.

## 8. Release
2018-08-26 v1.1.0 : Feature enhancements, bug fixes;  
2017-08-11 v1.0.0 : Add base function.



# About Original Author
- ID：Carson_Ho
- 简介：CSDN签约作者、简书推荐作者、稀土掘金专栏作者
- E - mail：[carson.ho@foxmail.com](mailto:carson.ho@foxmail.com)
- Github：[https://github.com/Carson-Ho](https://github.com/Carson-Ho)
- CSDN：[http://blog.csdn.net/carson_ho](http://blog.csdn.net/carson_ho)
- 简书：[http://www.jianshu.com/u/383970bef0a0](http://www.jianshu.com/u/383970bef0a0)
- 稀土掘金：[https://juejin.im/user/58d4d9781b69e6006ba65edc](https://juejin.im/user/58d4d9781b69e6006ba65edc)


# About Modifier
- ID：liying2008
- E - mail：[liruoer2008@yeah.net](mailto:liruoer2008@yeah.net)
- Github：[https://github.com/liying2008](https://github.com/liying2008)
- CSDN：[https://blog.csdn.net/u012939909](https://blog.csdn.net/u012939909)
- 简书：[https://www.jianshu.com/u/14ab91761183](https://www.jianshu.com/u/14ab91761183)
