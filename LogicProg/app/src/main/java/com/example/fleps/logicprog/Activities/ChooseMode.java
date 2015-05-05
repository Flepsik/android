package com.example.fleps.logicprog.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.fleps.logicprog.R;

/**
 * Created by Fleps_000 on 03.05.2015.
 */
public class ChooseMode extends Activity implements View.OnClickListener {
    Button blitzBtn, mathBtn, puzzleBtn, randomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);
        puzzleBtn = (Button) findViewById(R.id.puzzlesBtn);
        mathBtn = (Button) findViewById(R.id.mathBtn);
        blitzBtn = (Button) findViewById(R.id.blitzBtn);
        randomBtn = (Button) findViewById(R.id.randomBtn);

        bindListenerOnButton(puzzleBtn);
        bindListenerOnButton(mathBtn);
        bindListenerOnButton(blitzBtn);
        bindListenerOnButton(randomBtn);
    }

    public void bindListenerOnButton(Button button){
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ChooseLevel.class);
        switch (v.getId()) {
            case R.id.puzzlesBtn:
                intent.putExtra("type", "puzzle");
                this.startActivity(intent);
                break;
            case R.id.mathBtn:
                intent.putExtra("type", "math");
                this.startActivity(intent);
                break;
            case R.id.blitzBtn:
                intent.putExtra("type", "blitz");
                this.startActivity(intent);
                break;
            case R.id.randomBtn:
                intent.putExtra("type", "random");
                this.startActivity(intent);
                break;
        }
    }
}
