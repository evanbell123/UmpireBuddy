package com.umkc.evan.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
                strikeCount++;
                updateStrikeCount();
                if (strikeCount == 3) {
                    resetAll(outMessage);
                }
            }
        });

        mBallBtn = (Button) findViewById(R.id.ball_btn);
        mBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballCount++;
                updateBallCount();
                if (ballCount == 4) {
                    resetAll(walkMessage);
                }
            }
        });
    }

    private void updateStrikeCount() {
        TextView t = (TextView) findViewById(R.id.strike_cnt_lbl);
        t.setText(Integer.toString(strikeCount));
    }

    private void updateBallCount() {
        TextView t = (TextView)findViewById(R.id.ball_cnt_lbl);
        t.setText(Integer.toString(ballCount));
    }

    private void resetCounts() {
        strikeCount = 0;
        ballCount = 0;
        updateStrikeCount();
        updateBallCount();
    }

    private void resetAll(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Dialog box");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetCounts();
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

        switch ( item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.reset:
                resetCounts();
                return true;
            /*
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                startActivity(intent);
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
