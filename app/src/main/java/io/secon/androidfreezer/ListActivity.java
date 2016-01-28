package io.secon.androidfreezer;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // データを追加するレイアウトの指定
        LinearLayout layout = (LinearLayout) findViewById(R.id.wrapFoodList);

        // 参照するデータベースの指定
        FoodOpenHelper helper = new FoodOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query("food", new String[]{"foodName", "amount", "eatLimit", "id"}, null, null, null, null, null);

        // 全てのデータを表示
        boolean mov = c.moveToFirst();
        while(mov) {
            //TextView foodTv = new TextView(this);
            //foodTv.setText(String.format("%s : %sコ / %sまで ID: %d", c.getString(0), c.getString(1), c.getString(2), c.getInt(3)));

            // レイアウトを作成
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            itemLayout.setWeightSum(3);

            // weight の設定
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            lp.weight = 1;

            // テキストビューの作成
            TextView nameTv = new TextView(this);
            TextView amountTv = new TextView(this);
            TextView limitTv = new TextView(this);

            // 表示データのセット
            nameTv.setText(c.getString(0));
            amountTv.setText(c.getString(1));
            limitTv.setText(c.getString(2));

            itemLayout.addView(nameTv, lp);
            itemLayout.addView(amountTv, lp);
            itemLayout.addView(limitTv, lp);

            // intent に格納して更新画面に渡す用
            final String foodName = c.getString(0);
            final String amount = c.getString(1);
            final String foodLimit = c.getString(2);
            final int id = c.getInt(3);

            mov = c.moveToNext();
            //layout.addView(foodTv);
            layout.addView(itemLayout);

            // クリックで食材の更新画面を表示
            itemLayout.setOnClickListener(new View.OnClickListener() {
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

    // backキー押下
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK){
            return super.onKeyDown(keyCode, event);
        }else{
            Intent listIntent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(listIntent);
            return false;
        }
    }
}
