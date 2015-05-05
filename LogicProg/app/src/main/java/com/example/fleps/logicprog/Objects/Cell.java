package com.example.fleps.logicprog.Objects;

import android.widget.Button;

/**
 * Created by Fleps_000 on 01.05.2015.
 */
public class Cell {
    private Button button;
    private boolean isChoosen=false;
    private boolean isCurrent=false;
    private boolean isStepable=false;
    public Cell(Button button, boolean isChoosen, boolean isCurrent, boolean isStepable) {
        this.button = button;
        this.isChoosen = isChoosen;
        this.isCurrent = isCurrent;
        this.isStepable = isStepable;
    }

    public Cell(Button button) {
        this.button = button;
        isChoosen = false;
        isCurrent = false;
        isStepable = false;
    }

    public Cell() {
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isChoosen() {
        return isChoosen;
    }

    public void setChoosen(boolean isChoosen) {
        this.isChoosen = isChoosen;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (!button.equals(cell.button)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return button.hashCode();
    }
}
