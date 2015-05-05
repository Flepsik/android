package com.example.fleps.logicprog.Objects;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fleps.logicprog.DBHelper;
import com.example.fleps.logicprog.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Fleps_000 on 03.05.2015.
 */
public class Level {
    private Cell[][] cells;
    private ArrayList<Cell> rightWay = new ArrayList<Cell>();
    String name;
    String type="";
    String tip="";
    public static final int STR_COUNT = 7, COLUMN_COUNT = 6;

    public Level(Cell[][] cells, String name, String type) {
        this.cells = cells;
        this.name = name;
        this.type = type;
    }

    public String getTip() {
        return tip;
    }

    public String getType() {
        return type;
    }

    public void loadLevel(SQLiteDatabase db, DBHelper dbHelper) {
        String selection = "type = ? and name = ?";
        String[] selectionArgs = new String[] { type, name};
        Cursor c = db.query("levels", new String[] {"tip","description","rightway"}, selection , selectionArgs, null, null, null);
        String description = "";
        String rightWayStr = "";
        if (c.moveToFirst()) {
                tip = c.getString(c.getColumnIndex("tip"));
                description = c.getString(c.getColumnIndex("description"));
                rightWayStr = c.getString(c.getColumnIndex("rightway"));
        }
        fillCells(description);
        fillRightWay(rightWayStr);

    }

    private void fillCells(String description) {
        String[]  desc = description.split(",");
        for(int i = 0; i < STR_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if(desc[i*COLUMN_COUNT+j].equals("nv")) cells[i][j].getButton().setVisibility(View.INVISIBLE);
                else cells[i][j].getButton().setText(desc[i * COLUMN_COUNT + j]);
            }
        }
    }

    private void fillRightWay(String rightWay) {
        rightWay = rightWay.replaceAll(" ", "");
        String[] rw = rightWay.split(",");
        for(int i = 0; i < rw.length; i++) {
            String[] temp = rw[i].split("-");
            int a = Integer.parseInt(temp[0]) - 1;
            int b = Integer.parseInt(temp[1]) - 1;
            this.rightWay.add(cells[a][b]);
        }
    }

    public Cell getCellById(int id) {
        for (int i = 0; i < STR_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++)
                if (id == cells[i][j].getButton().getId())
                    return cells[i][j];
        }
        return new Cell();
    }

    public ArrayList<Cell> getAvalibleToStepButtons(int id) {
        int i=0,j=0;
        boolean isFound = false;
        Cell cell = null;
        for (i = 0; i < STR_COUNT & !isFound; i++) {
            for (j = 0; j < COLUMN_COUNT; j++) {
                if (id == cells[i][j].getButton().getId()) {
                    isFound = true;
                    break;
                }
            }
        }
        i--;
        ArrayList<Cell> avaliableCells = new ArrayList<>();
        if (checkAvaliable(i-1,j-1)) avaliableCells.add(cells[i-1][j-1]);
        if (checkAvaliable(i-1,j)) avaliableCells.add(cells[i-1][j]);
        if (checkAvaliable(i-1,j+1)) avaliableCells.add(cells[i-1][j+1]);
        if (checkAvaliable(i,j-1)) avaliableCells.add(cells[i][j-1]);
        if (checkAvaliable(i,j+1)) avaliableCells.add(cells[i][j+1]);
        if (checkAvaliable(i+1,j-1)) avaliableCells.add(cells[i+1][j-1]);
        if (checkAvaliable(i+1,j)) avaliableCells.add(cells[i+1][j]);
        if (checkAvaliable(i+1,j+1)) avaliableCells.add(cells[i+1][j+1]);
        return avaliableCells;
    }

    private boolean checkAvaliable(int a, int b) {
        if(a<0 || a>=STR_COUNT || b<0 || b>=COLUMN_COUNT) return false;
        else if (cells[a][b].isChoosen())   {
            Log.d("mylogs",  a + " " + b + " choosen ");
            return false;
        }
            else return true;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getWinButton() {
        return rightWay.get(rightWay.size()-1);
    }

    public Cell getFirstRightStep () {
        return rightWay.get(0);
    }

    public Cell getCell(int line, int column) {
        return cells[line][column];
    }

    public boolean CheckWin(ArrayList<Cell> way) {
        if (way.size()==rightWay.size()) {
            for (int i = 0; i < way.size(); i++) {
                if(!way.get(i).equals(rightWay.get(i))) return false;
            }
            return true;
        } else return false;
    }
}
