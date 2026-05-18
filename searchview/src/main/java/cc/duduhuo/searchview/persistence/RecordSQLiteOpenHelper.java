package cc.duduhuo.searchview.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson_Ho on 17/8/10.
 * Modified by LiYing on 18/8.
 */

// 继承自SQLiteOpenHelper数据库类的子类
public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    // 保持数据库文件名不变，为了向后兼容
    private static final String NAME = "scut.carson_ho.searchview.db";
    private static final Integer VERSION = 1;

    private final String tableName;

    /**
     * 构造 RecordSQLiteOpenHelper 对象
     *
     * @param context Context
     * @param tag     使用 tag（标签）实现搜索记录的隔离，可实现不同 SearchView 的搜索记录互不影响
     */
    public RecordSQLiteOpenHelper(Context context, @Nullable String tag) {
        super(context, NAME, null, VERSION);
        if (tag == null || tag.isEmpty()) {
            this.tableName = "records";
        } else {
            this.tableName = "records_" + tag;
        }
    }

    private void createTableIfNotExist(SQLiteDatabase db) {
        // 使用 "create table if not exists" 语句
        db.execSQL("create table if not exists " + tableName + "(id integer primary key autoincrement, name varchar(200))");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 新建历史记录表，使用 name 列存储历史记录
        // 第一次创建数据库时调用，确保当前表被创建
        createTableIfNotExist(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 每次打开数据库时调用。
        // 因为 tag 是动态的，我们需要在这里确保对应 tag 的表存在（如果不存在则创建）
        createTableIfNotExist(db);
    }

    /**
     * 查询全部历史记录
     */
    public List<String> queryAll() {
        List<String> result = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(
                "select id as _id, name from " + tableName + " order by id desc",
                new String[]{});

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("name");
            result.add(cursor.getString(nameIndex));
        }
        cursor.close();
        return result;
    }

    /**
     * 模糊查询数据 & 显示到ListView列表上
     */
    public List<String> queryByName(String name) {
        List<String> result = new ArrayList<>();
        // 1. 模糊搜索
        Cursor cursor = getReadableDatabase().rawQuery(
                "select id as _id, name from " + tableName + " where name like ? order by id desc",
                new String[]{"%" + name + "%"});

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("name");
            result.add(cursor.getString(nameIndex));
        }
        cursor.close();
        return result;
    }

    /**
     * 清空数据库
     */
    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + tableName);
        db.close();
    }

    /**
     * 删除一条数据
     *
     * @param name 要删除的数据
     */
    public void deleteByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + tableName + " where name=?", new String[]{name});
        db.close();
    }

    /**
     * 检查数据库中是否已经有该搜索记录
     */
    public boolean has(String name) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "select id as _id, name from " + tableName + " where name =?", new String[]{name});
        //  判断是否有下一个
        boolean hasNext = cursor.moveToNext();
        cursor.close();
        return hasNext;
    }

    /**
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    public void insert(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into " + tableName + "(name) values(?)", new String[]{name});
        db.close();
    }
}
