package com.umkc.evan.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private Button mStrikeBtn;
    private Button mBallBtn;

    private int strikeCount = 0;
    private int ballCount = 0;

    private String outMessage = "Out!";
    private String walkMessage = "Walk!";

    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
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
                    mode.finish(); // Action picked, so close the Contextual Action Bar(CAB)
                    return true;
                case R.id.contextBall:
                    ballEvent();
                    mode.finish(); // Action picked, so close the CAB
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

        if (savedInstanceState != null) {
            strikeCount = savedInstanceState.getInt("strikeCount");
            ballCount = savedInstanceState.getInt("ballCount");
            updateStrikeCount();
            updateBallCount();
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }

}
