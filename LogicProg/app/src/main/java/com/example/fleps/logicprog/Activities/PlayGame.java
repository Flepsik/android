package com.example.fleps.logicprog.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fleps.logicprog.DBHelper;
import com.example.fleps.logicprog.Objects.Cell;
import com.example.fleps.logicprog.Objects.Level;
import com.example.fleps.logicprog.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Fleps_000 on 01.05.2015.
 */
public class PlayGame extends Activity implements View.OnTouchListener {
    ArrayList<Cell> userMoves = new ArrayList<Cell>();
    Button btn;
    Level lvl;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ArrayList<Cell> steppable = new ArrayList<>();
    Cell winCell = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        Intent intent = getIntent();
        btn = (Button) findViewById(R.id.button3_3);
        btn.setOnTouchListener(this);
        lvl = new Level(bindId(), intent.getStringExtra("lvl"), intent.getStringExtra("type"));
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        lvl.loadLevel(db, dbHelper);
        restart();
        winCell = lvl.getWinButton();
        steppable = lvl.getAvalibleToStepButtons(userMoves.get(0).getButton().getId());
        ((TextView) findViewById(R.id.level_text_view)).setText(intent.getStringExtra("lvl"));
        ((TextView) findViewById(R.id.tip_text_view)).setText(lvl.getTip());
        paint();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId() == R.id.restart_btn) {
                restart();
                paint();
            } else {
                Cell cell = lvl.getCellById(v.getId());
                if (steppable.contains(cell)) {
                    userMoves.add(cell);
                    if(steppable.contains(winCell)) {
                        if(lvl.CheckWin(userMoves)) {
                            Log.d("mylog", "You won!");
                        }
                    }
                    clearSteppable();
                    steppable = lvl.getAvalibleToStepButtons(userMoves.get(userMoves.size() - 1).getButton().getId());
                    cell.setCurrent(true);
                    cell.setChoosen(true);
                    userMoves.get(userMoves.size() - 2).setCurrent(false);
                    paint();
                    return true;
                }
            }
        }
        return true;
    }

    private void clearSteppable() {
        for (Cell cell : steppable) {
            cell.getButton().setBackground(getResources().getDrawable(R.drawable.cell_bg));
        }
    }

    private void paint() {
        for (Cell cell : steppable) {
            cell.getButton().setBackground(getResources().getDrawable(R.drawable.cell_bg_lightblue));
        }
        for (int i = 0; i < userMoves.size(); i++) {
            userMoves.get(i).getButton().setBackground(getResources().getDrawable(R.drawable.cell_bg_blue));
        }
        userMoves.get(userMoves.size() - 1).getButton().setBackground(getResources().getDrawable(R.drawable.cell_bg_blue_cur));
    }

    private void restart() {
        for (int i = 0; i < userMoves.size(); i++) {
            userMoves.get(i).getButton().setBackground(getResources().getDrawable(R.drawable.cell_bg));
            userMoves.get(i).setChoosen(false);
        }
        userMoves.clear();
        userMoves.add(lvl.getFirstRightStep());
        clearSteppable();
        steppable = lvl.getAvalibleToStepButtons(userMoves.get(0).getButton().getId());
        userMoves.get(0).setCurrent(true);
        userMoves.get(0).setChoosen(true);
        paint();
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

