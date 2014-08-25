package jp.wishmatch.stopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView timeTextView;
    private long startTime;
    int second, miliSecond;
    private StopWatch stopWatch = new StopWatch();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTextView = (TextView)findViewById(R.id.stopWatch);

        Button startButton = (Button)findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                stopWatch.start();
                timeTextView = (TextView)findViewById(R.id.stopWatch);
            }
        });

        Button stopButton = (Button)findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWatch.stop();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void update() {
        second = (int)(((System.currentTimeMillis() - startTime)/1000));
        miliSecond = (int)(((System.currentTimeMillis() - startTime)/100) % 10);
        timeTextView.setText(String.format("%d", second) + "." + String.format("%d", miliSecond));
    }

    class StopWatch extends Handler {
        private boolean isUpdate = false;

        public void start() {
            this.isUpdate = true;
            handleMessage(new Message());
        }

        public void stop() {
            this.isUpdate = false;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg); // 既存のメッセージは削除
            this.removeMessages(0);
            if (this.isUpdate) {
                MainActivity.this.update(); // 地震が発したメッセージ
                sendMessageDelayed(obtainMessage(0), 100); // 100ミリ秒後にメッセージを出力
            }
        }
    }
}
