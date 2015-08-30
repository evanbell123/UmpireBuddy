package com.umkc.evan.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UmpireActivity extends AppCompatActivity {

    private Button mStrikeBtn;
    private Button mBallBtn;

    private int strikeCount = 0;
    private int ballCount = 0;

    private String outMessage = "Out!";
    private String walkMessage = "Walk!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire);

        mStrikeBtn = (Button) findViewById(R.id.strike_btn);
        mStrikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strikeCount < 3) {
                    strikeCount++;
                    updateStrikeCount();
                }
                else {
                    resetAll(outMessage);
                }
            }
        });

        mBallBtn = (Button) findViewById(R.id.ball_btn);
        mBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ballCount < 4) {
                    ballCount++;
                    updateBallCount();
                }
                else {
                    resetAll(walkMessage);
                }
            }
        });
    }

    private void updateStrikeCount() {
        TextView t = (TextView)findViewById(R.id.strike_cnt_lbl);
        t.setText(Integer.toString(strikeCount));
    }

    private void updateBallCount() {
        TextView t = (TextView)findViewById(R.id.ball_cnt_lbl);
        t.setText(Integer.toString(ballCount));
    }

    private void resetAll(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Dialog box");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                strikeCount = 0;
                ballCount = 0;
                // Note, you have to call update count here because.
                //   the call builder.show() below is non blocking.
                updateStrikeCount();
                updateBallCount();
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_umpire, menu);
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
