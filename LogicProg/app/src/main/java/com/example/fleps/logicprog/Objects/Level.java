package com.example.fleps.logicprog.Objects;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.fleps.logicprog.DBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Fleps_000 on 03.05.2015.
 */
public class Level {
    private Cell[][] cells;

    private ArrayList<ArrayList<Cell>> rightWays = new ArrayList<>();
    private String name;
    private String type = "";
    private String tip = "";
    public static final int STR_COUNT = 7, COLUMN_COUNT = 6;
    private String answer = "";
    private String id;

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

    public String getName() {return name;}

    public void loadLevel(SQLiteDatabase db, DBHelper dbHelper) {
        String selection = "type = ? and name = ?";
        String[] selectionArgs = new String[]{type, name};
        Cursor c = db.query("levels", new String[]{"id", "tip", "description", "rightway", "answer"}, selection, selectionArgs, null, null, null);
        String description = "";
        String rightWayStr = "";
        if (c.moveToFirst()) {
            id = c.getString(c.getColumnIndex("id"));
            tip = c.getString(c.getColumnIndex("tip"));
            description = c.getString(c.getColumnIndex("description"));
            rightWayStr = c.getString(c.getColumnIndex("rightway"));
            answer = c.getString(c.getColumnIndex("answer"));
        }
        fillCells(description);
        fillRightWays(rightWayStr);
    }

    public String getId() {
        return id;
    }

    private void fillCells(String description) {

        description = description.replaceAll("\n" +
                "        ","");
        String[] desc = description.split(",");
        for (int i = 0; i < STR_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (desc[i * COLUMN_COUNT + j].equals("nv"))
                    cells[i][j].getButton().setVisibility(View.INVISIBLE);
                else cells[i][j].getButton().setText(desc[i * COLUMN_COUNT + j]);
            }
        }
    }

    private void fillRightWays(String strRW) {
        String[] rws = strRW.split("/");
        for(String str : rws) {
            rightWays.add(fillRightWay(str));
        }
    }

    private ArrayList<Cell> fillRightWay(String rightWayStr) {
        ArrayList<Cell> rightWay = new ArrayList<Cell>();
        rightWayStr = rightWayStr.replaceAll(" ", "");
        String[] rw = rightWayStr.split(",");
        for (int i = 0; i < rw.length; i++) {
            String[] temp = rw[i].split("-");
            int a = Integer.parseInt(temp[0]) - 1;
            int b = Integer.parseInt(temp[1]) - 1;
            rightWay.add(cells[a][b]);
        }
        return rightWay;
    }

    public Cell getCellById(int id) {
        for (int i = 0; i < STR_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++)
                if (id == cells[i][j].getButton().getId())
                    return cells[i][j];
        }
        return new Cell();
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<Cell> getAvalibleToStepButtons(int id) {
        int i = 0, j = 0;
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
        if (checkAvaliable(i - 1, j - 1)) avaliableCells.add(cells[i - 1][j - 1]);
        if (checkAvaliable(i - 1, j)) avaliableCells.add(cells[i - 1][j]);
        if (checkAvaliable(i - 1, j + 1)) avaliableCells.add(cells[i - 1][j + 1]);
        if (checkAvaliable(i, j - 1)) avaliableCells.add(cells[i][j - 1]);
        if (checkAvaliable(i, j + 1)) avaliableCells.add(cells[i][j + 1]);
        if (checkAvaliable(i + 1, j - 1)) avaliableCells.add(cells[i + 1][j - 1]);
        if (checkAvaliable(i + 1, j)) avaliableCells.add(cells[i + 1][j]);
        if (checkAvaliable(i + 1, j + 1)) avaliableCells.add(cells[i + 1][j + 1]);
        return avaliableCells;
    }

    private boolean checkAvaliable(int a, int b) {
        if (a < 0 || a >= STR_COUNT || b < 0 || b >= COLUMN_COUNT) return false;
        else if (cells[a][b].isChoosen()) {
            Log.d("mylogs", a + " " + b + " choosen ");
            return false;
        } else return true;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getWinButton() {
        return rightWays.get(0).get(rightWays.get(0).size() - 1);
    }

    public Cell getFirstRightStep() {
        return rightWays.get(0).get(0);
    }

    public Cell getCell(int line, int column) {
        return cells[line][column];
    }

    public boolean CheckWin(ArrayList<Cell> way) {
        for (ArrayList<Cell> rightWay : rightWays) {
            boolean checkWin = true;
            if (way.size() == rightWay.size()) {
                for (int i = 0; i < way.size(); i++) {
                    if (!way.get(i).equals(rightWay.get(i))) {
                        checkWin = false;
                        break;
                    }
                }
                if (checkWin) return true;
            }
        }
        return false;
    }
}
