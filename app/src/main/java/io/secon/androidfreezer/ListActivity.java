package io.secon.androidfreezer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // レイアウトの設定
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);

        // 参照するデータベースの指定
        FoodOpenHelper helper = new FoodOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query("food", new String[]{"foodName", "amount", "eatLimit", "id"}, null, null, null, null, null);

        // 全てのデータを表示
        boolean mov = c.moveToFirst();
        while(mov) {
            TextView foodTv = new TextView(this);
            foodTv.setText(String.format("%s : %sコ / %sまで ID: %d", c.getString(0), c.getString(1), c.getString(2), c.getInt(3)));

            final String foodName = c.getString(0);
            final String amount = c.getString(1);
            final String foodLimit = c.getString(2);
            final int id = c.getInt(3);

            mov = c.moveToNext();
            layout.addView(foodTv);

            // クリックで食材の更新画面を表示
            foodTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListActivity.this, UpdateActivity.class);

                    // 更新画面に食材データを渡す
                    intent.putExtra("name", foodName);
                    intent.putExtra("amount", amount);
                    intent.putExtra("limit", foodLimit);
                    intent.putExtra("id", id);

                    startActivity(intent);
                }
            });
        }
        c.close();
        db.close();
    }
}
