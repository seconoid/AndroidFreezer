package io.secon.androidfreezer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText foodNameTv = (EditText) findViewById(R.id.updateFoodName);
        final EditText foodAmountTv = (EditText) findViewById(R.id.updateFoodAmount);
        final TextView foodLimitTv = (TextView) findViewById(R.id.updateFoodLimit);

        // 一覧画面からデータを取得
        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String amount = intent.getStringExtra("amount");
            String limit = intent.getStringExtra("limit");

            foodNameTv.setText(name);
            foodAmountTv.setText(amount);
            foodLimitTv.setText(limit);
        }

        // 更新ボタン
        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = foodNameTv.getText().toString();
                String amount = foodAmountTv.getText().toString();
                String limit = foodLimitTv.getText().toString();

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

}
