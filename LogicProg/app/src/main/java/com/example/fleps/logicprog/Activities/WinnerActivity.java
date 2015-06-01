package com.example.fleps.logicprog.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fleps.logicprog.R;

/**
 * Created by Fleps_000 on 06.05.2015.
 */
public class WinnerActivity extends Activity implements View.OnClickListener {
    Button nextLvl, chooseLvl, mainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner);
        nextLvl = (Button) findViewById(R.id.nextlvl);
        chooseLvl = (Button) findViewById(R.id.chooselvl);
        mainMenu = (Button) findViewById(R.id.mainmenu);

        TextView tv = (TextView) findViewById(R.id.lvl_done_name);
        tv.setText(getIntent().getStringExtra("lvl")+ "!" );
        tv = (TextView) findViewById(R.id.right_series);
        tv.setText(getIntent().getStringExtra("answer"));

        nextLvl.setOnClickListener(this);
        chooseLvl.setOnClickListener(this);
        mainMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.nextlvl:
                intent = new Intent(this, PlayGame.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                intent.putExtra("lvl", getIntent().getStringExtra("lvl"));
                startActivity(intent);
                break;
            case R.id.chooselvl:
                intent = new Intent(this, ChooseLevel.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(intent);
                break;
            case R.id.mainmenu:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
