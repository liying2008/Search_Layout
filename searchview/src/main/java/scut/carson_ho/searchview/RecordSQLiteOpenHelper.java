package scut.carson_ho.searchview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carson_Ho on 17/8/10.
 * Modified by LiYing on 18/8.
 */

// 继承自SQLiteOpenHelper数据库类的子类
public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "scut.carson_ho.searchview.db";
    private static final Integer VERSION = 1;

    RecordSQLiteOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 打开数据库 & 建立了一个叫 records 的表，里面只有一列 name 来存储历史记录：
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
