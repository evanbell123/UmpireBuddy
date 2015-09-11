package com.umkc.evan.umpirebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UmpireActivity extends AppCompatActivity {

    public final static String EXTRA_DATA = "com.umkc.evan.umpirebuddy.ABOUTDATA";
    private final static String TAG = "Umpire Buddy";
    private static final String PREFS_NAME = "PrefsFile";

    private Button mStrikeBtn;
    private Button mBallBtn;

    private int strikeCount = 0;
    private int ballCount = 0;
    private int totalOutsCount = 0;

    private String outMessage = "Out!";
    private String walkMessage = "Walk!";

    private ActionMode mActionMode; //context menu

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.contextStrike:
                    strikeEvent();
                    mode.finish();
                    return true;
                case R.id.contextBall:
                    ballEvent();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting onCreate...");
        setContentView(R.layout.activity_umpire);

        // Get saved total outs count
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        totalOutsCount = sp.getInt("totalOutsCount", -1);
        updateTotalOutsCount();

        // get saved totalOuts, strikeCount, and ballCount form persistent storage
        if (savedInstanceState != null) {
            strikeCount = savedInstanceState.getInt("strikeCount");
            ballCount = savedInstanceState.getInt("ballCount");
            totalOutsCount = savedInstanceState.getInt("totalOutsCount");
            updateStrikeCount();
            updateBallCount();
            updateTotalOutsCount();
        }

        mStrikeBtn = (Button) findViewById(R.id.strike_btn);
        mStrikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strikeEvent();
            }
        });

        mBallBtn = (Button) findViewById(R.id.ball_btn);
        mBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballEvent();
            }
        });


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                // mActionMode is set back to null
                //    above when the context menu disappears.
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
    }

    private void strikeEvent() {
        strikeCount++;
        updateStrikeCount();
        if (strikeCount == 3) {
            totalOutsCount++;
            updateTotalOutsCount();
            resetAll(outMessage);
        }
    }

    private void ballEvent() {
        ballCount++;
        updateBallCount();
        if (ballCount == 4) {
            resetAll(walkMessage);
        }
    }

    private void updateStrikeCount() {
        TextView t = (TextView) findViewById(R.id.strike_cnt_lbl);
        t.setText(Integer.toString(strikeCount));
    }

    private void updateBallCount() {
        TextView t = (TextView)findViewById(R.id.ball_cnt_lbl);
        t.setText(Integer.toString(ballCount));
    }

    private void updateTotalOutsCount() {
        TextView t = (TextView)findViewById(R.id.total_outs_cnt_lbl);
        t.setText(Integer.toString(totalOutsCount));
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

        switch ( item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.reset:
                resetCounts();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra(EXTRA_DATA, "extra data or parameter you want to pass to activity");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        // save total outs count
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("totalOutsCount", totalOutsCount);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);

        Log.i(TAG, "onSaveInstanceState()");
        icicle.putInt("strikeCount", strikeCount);
        icicle.putInt("ballCount", ballCount);
        icicle.putInt("totalOutsCount", totalOutsCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }

}
