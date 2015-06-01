package com.example.fleps.logicprog.Objects;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fleps.logicprog.DBHelper;
import com.example.fleps.logicprog.R;

import java.util.ArrayList;

/**
 * Created by Fleps_000 on 29.05.2015.
 */
public class GameBoard {
    Level lvl;
    DBHelper dbHelper;
    SQLiteDatabase db;
    ArrayList<Cell> steppable;
    ArrayList<Cell> userMoves;
    Cell winCell;
    Intent intent;
    Resources resources;
    public GameBoard(Cell[][] cells, Intent intent, DBHelper dbHelper, SQLiteDatabase db, Resources resources) {
        this.intent = intent;
        this.dbHelper = dbHelper;
        this.db = db;
        this.resources = resources;
        steppable = new ArrayList<>();
        userMoves = new ArrayList<>();
        lvl = new Level(cells, intent.getStringExtra("lvl"), intent.getStringExtra("type"));
        lvl.loadLevel(db, dbHelper);
        winCell = lvl.getWinButton();
        restart();
        steppable = lvl.getAvalibleToStepButtons(userMoves.get(0).getButton().getId());
    }

    public String getTip(){
        return lvl.getTip();
    }

    public String getLvlName() {
        return lvl.getName();
    }

    public String getAnswer() {
        return lvl.getAnswer();
    }

    public void restartPressed() {
        restart();
        paint();
    }

    public boolean isItWin(int id) {
        Cell cell = lvl.getCellById(id);
        boolean isItWin = false;
        if (steppable.contains(cell)) {
            userMoves.add(cell);
            cell.setCurrent(true);
            cell.setChoosen(true);
            if (cell.equals(winCell)) {
                if (lvl.CheckWin(userMoves)) {
                    Log.d("mylog", "You won!!");
                    isItWin = true;
                    changeStatus();
                } else {
                    userMoves.remove(cell);
                    cell.setChoosen(false);
                    cell.setCurrent(false);
                }
            }
            clearSteppable();
            steppable = lvl.getAvalibleToStepButtons(userMoves.get(userMoves.size() - 1).getButton().getId());
            userMoves.get(userMoves.size() - 2).setCurrent(false);
            paint();
        }
        return isItWin;
    }

    private void changeStatus() {
        ContentValues values = new ContentValues();
        values.put("status", true);
        db.update("levels", values, "id = ?", new String[]{lvl.getId()});
    }

    private void clearSteppable() {
        for (Cell cell : steppable) {
            cell.getButton().setBackground(resources.getDrawable(R.drawable.cell_bg));
        }
    }

    private void paint() {
        for (Cell cell : steppable) {
            cell.getButton().setBackground(resources.getDrawable(R.drawable.cell_bg_lightblue));
        }
        for (int i = 0; i < userMoves.size(); i++) {
            userMoves.get(i).getButton().setBackground(resources.getDrawable(R.drawable.cell_bg_blue));
        }
        winCell.getButton().setBackground(resources.getDrawable(R.drawable.cell_bg_red));
        userMoves.get(userMoves.size() - 1).getButton().setBackground(resources.getDrawable(R.drawable.cell_bg_blue_cur));
    }

    private void restart() {
        for (int i = 0; i < userMoves.size(); i++) {
            userMoves.get(i).getButton().setBackground(resources.getDrawable(R.drawable.cell_bg));
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


}
