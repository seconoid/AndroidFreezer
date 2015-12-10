package io.secon.androidfreezer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nakayama.akito on 2015/12/05.
 */
public class FoodOpenHelper extends SQLiteOpenHelper {

    public  FoodOpenHelper(Context context) { super(context, "FoodDB", null, 1); }

    // データベースを作成
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table food(" + " foodName text not null," + "amount real," + "limit date" + ");");
    }

    // バージョンチェック
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
