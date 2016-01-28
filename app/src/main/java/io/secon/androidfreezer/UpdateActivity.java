package io.secon.androidfreezer;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FoodOpenHelper helper = new FoodOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        final EditText foodNameTv = (EditText) findViewById(R.id.updateFoodName);
        final EditText foodAmountTv = (EditText) findViewById(R.id.updateFoodAmount);
        final TextView foodLimitTv = (TextView) findViewById(R.id.updateFoodLimit);

        // 一覧画面からデータを取得
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 0);
        if(intent != null){
            String name = intent.getStringExtra("name");
            String amount = intent.getStringExtra("amount");
            String limit = intent.getStringExtra("limit");

            foodNameTv.setText(name);
            foodAmountTv.setText(amount);
            foodLimitTv.setText(limit);

            foodLimitTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePick(v);
                }
            });
        }



        // 更新ボタン
        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = foodNameTv.getText().toString();
                String amount = foodAmountTv.getText().toString();
                String limit = foodLimitTv.getText().toString();

                ContentValues updateValues = new ContentValues();
                updateValues.put("foodName", name);
                updateValues.put("amount", amount);
                updateValues.put("eatLimit", limit);

                db.update("food", updateValues, "id = " + id, null);
                Toast.makeText(UpdateActivity.this, "更新しました", Toast.LENGTH_LONG).show();

                Intent listIntent = new Intent(UpdateActivity.this, ListActivity.class);
                startActivity(listIntent);
            }
        });

        // 削除ボタン
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("food", "id = " + id, null);

                Toast.makeText(UpdateActivity.this, "削除しました", Toast.LENGTH_LONG).show();

                Intent listIntent = new Intent(UpdateActivity.this, ListActivity.class);
                startActivity(listIntent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // 日付を選択
    public void datePick(View v) {
        final TextView dateTv = (TextView)v;

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
}
