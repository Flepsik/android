package com.example.fleps.logicprog.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fleps.logicprog.DBHelper;
import com.example.fleps.logicprog.Objects.Cell;
import com.example.fleps.logicprog.Objects.GameBoard;
import com.example.fleps.logicprog.R;

/**
 * Created by Fleps_000 on 01.05.2015.
 */
public class PlayGame extends Activity implements View.OnTouchListener {
    DBHelper dbHelper;
    SQLiteDatabase db;
    GameBoard gameBoard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        initDB();
        gameBoard = new GameBoard(bindId(), getIntent(), dbHelper, db, getResources());
        ((TextView) findViewById(R.id.level_text_view)).setText(gameBoard.getLvlName());
        ((TextView) findViewById(R.id.tip_text_view)).setText(gameBoard.getTip());
    }

    void initDB () {
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId() == R.id.restart_btn) {
                gameBoard.restartPressed();
            } else {
                if (gameBoard.isItWin(v.getId())) {
                    Intent winIntent = new Intent(this, WinnerActivity.class);
                    winIntent.putExtra("answer", gameBoard.getAnswer());
                    winIntent.putExtra("lvl", getIntent().getStringExtra("lvl"));
                    winIntent.putExtra("type", getIntent().getStringExtra("type"));
                    winIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(winIntent);
                }
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        db.close();
        dbHelper.close();
        super.onDestroy();
    }

    private Cell[][] bindId() {
        Cell[][] cells = new Cell[7][6];
        cells[0][0] = new Cell((Button) findViewById(R.id.button1_1));
        cells[0][1] = new Cell((Button) findViewById(R.id.button1_2));
        cells[0][2] = new Cell((Button) findViewById(R.id.button1_3));
        cells[0][3] = new Cell((Button) findViewById(R.id.button1_4));
        cells[0][4] = new Cell((Button) findViewById(R.id.button1_5));
        cells[0][5] = new Cell((Button) findViewById(R.id.button1_6));

        cells[1][0] = new Cell((Button) findViewById(R.id.button2_1));
        cells[1][1] = new Cell((Button) findViewById(R.id.button2_2));
        cells[1][2] = new Cell((Button) findViewById(R.id.button2_3));
        cells[1][3] = new Cell((Button) findViewById(R.id.button2_4));
        cells[1][4] = new Cell((Button) findViewById(R.id.button2_5));
        cells[1][5] = new Cell((Button) findViewById(R.id.button2_6));

        cells[2][0] = new Cell((Button) findViewById(R.id.button3_1));
        cells[2][1] = new Cell((Button) findViewById(R.id.button3_2));
        cells[2][2] = new Cell((Button) findViewById(R.id.button3_3));
        cells[2][3] = new Cell((Button) findViewById(R.id.button3_4));
        cells[2][4] = new Cell((Button) findViewById(R.id.button3_5));
        cells[2][5] = new Cell((Button) findViewById(R.id.button3_6));

        cells[3][0] = new Cell((Button) findViewById(R.id.button4_1));
        cells[3][1] = new Cell((Button) findViewById(R.id.button4_2));
        cells[3][2] = new Cell((Button) findViewById(R.id.button4_3));
        cells[3][3] = new Cell((Button) findViewById(R.id.button4_4));
        cells[3][4] = new Cell((Button) findViewById(R.id.button4_5));
        cells[3][5] = new Cell((Button) findViewById(R.id.button4_6));

        cells[4][0] = new Cell((Button) findViewById(R.id.button5_1));
        cells[4][1] = new Cell((Button) findViewById(R.id.button5_2));
        cells[4][2] = new Cell((Button) findViewById(R.id.button5_3));
        cells[4][3] = new Cell((Button) findViewById(R.id.button5_4));
        cells[4][4] = new Cell((Button) findViewById(R.id.button5_5));
        cells[4][5] = new Cell((Button) findViewById(R.id.button5_6));

        cells[5][0] = new Cell((Button) findViewById(R.id.button6_1));
        cells[5][1] = new Cell((Button) findViewById(R.id.button6_2));
        cells[5][2] = new Cell((Button) findViewById(R.id.button6_3));
        cells[5][3] = new Cell((Button) findViewById(R.id.button6_4));
        cells[5][4] = new Cell((Button) findViewById(R.id.button6_5));
        cells[5][5] = new Cell((Button) findViewById(R.id.button6_6));

        cells[6][0] = new Cell((Button) findViewById(R.id.button7_1));
        cells[6][1] = new Cell((Button) findViewById(R.id.button7_2));
        cells[6][2] = new Cell((Button) findViewById(R.id.button7_3));
        cells[6][3] = new Cell((Button) findViewById(R.id.button7_4));
        cells[6][4] = new Cell((Button) findViewById(R.id.button7_5));
        cells[6][5] = new Cell((Button) findViewById(R.id.button7_6));

        findViewById(R.id.restart_btn).setOnTouchListener(this);
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 6; j++)
                bindListenerOnButton(cells[i][j].getButton());
        return cells;
    }

    private void bindListenerOnButton(Button button) {
        button.setOnTouchListener(this);
    }
}

