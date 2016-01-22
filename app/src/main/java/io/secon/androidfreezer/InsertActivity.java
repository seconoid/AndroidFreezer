package io.secon.androidfreezer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by nakayama.akito on 2015/11/27.
 */
public class InsertActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FoodOpenHelper helper = new FoodOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        final EditText nameText = (EditText) findViewById(R.id.foodName);
        final EditText amountText = (EditText) findViewById(R.id.foodAmount);
        final TextView limitText = (TextView) findViewById(R.id.foodLimit);

        Button updateButton = (Button)findViewById(R.id.insertBtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String foodName = nameText.getText().toString();
                String amount = amountText.getText().toString();
                String eatLimit = limitText.getText().toString();

                // データを挿入
                ContentValues insertValues = new ContentValues();
                insertValues.put("foodName", foodName);
                insertValues.put("amount", amount);
                insertValues.put("eatLimit", eatLimit);
                long id = db.insert("food", null, insertValues);
                Toast.makeText(InsertActivity.this, "登録されました", Toast.LENGTH_LONG).show();
            }
        });

        Button topButton = (Button) findViewById(R.id.btnToMain);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView dateTv = (TextView) findViewById(R.id.foodLimit);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });
    }

    // 日付を選択
    public void datePick() {
        final TextView dateTv = (TextView) findViewById(R.id.foodLimit);

        Calendar cal = Calendar.getInstance();

        // 日付選択ダイアログ
        DatePickerDialog dpDialog = new DatePickerDialog(this,

                // イベントリスナーの指定
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateTv.setText(year + "年" + (monthOfYear + 1) + "月"
                                + dayOfMonth + "日");

                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.HOUR_OF_DAY));

        // タイトルを設定
        dpDialog.setTitle("日付を選択してください");

        // ダイアログを表示
        dpDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
