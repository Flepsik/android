package com.example.fleps.logicprog.Activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fleps.logicprog.R;

import org.xmlpull.v1.XmlPullParser;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button playBtn, helpBtn, bestScoresBtn;
    final String LOG_TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        playBtn = (Button) findViewById(R.id.new_game_button);
        helpBtn = (Button) findViewById(R.id.how_to_play_btn);
        bestScoresBtn = (Button) findViewById(R.id.best_scores_btn);

        playBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        bestScoresBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_game_button:
                Intent chooseModeIntennt = new Intent(this, ChooseMode.class);
                startActivity(chooseModeIntennt);
                Log.d(LOG_TAG, "main + onclick");
                break;
            case R.id.how_to_play_btn:
                Intent helpIntent = new Intent(this, HowToPlay.class);
                this.startActivity(helpIntent);
                break;
            case R.id.best_scores_btn:
                Intent bestScoresIntent = new Intent(this, BestScores.class);
                this.startActivity(bestScoresIntent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

