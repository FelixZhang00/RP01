package jamffy.project.planktimer;

import jamffy.project.planktimer.utils.Logger;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	protected static final String TAG = "PLANK TIMER";
	protected static final int TIME_OVER = 0;
	// 保存总时间
	private EditText et_tottal;
	private ToggleButton tbt;

	private int total; // 以s为单位

	// private int temp_time; // 临时保存时间
	// private boolean isOver = false; // 计时器是否完成
	// private boolean isRunning = true;

	private final static int SECOND = 1000;
	private boolean isPause = false; // 计时器是否处于暂停状态
	// 倒计时间到

	// 记录打开开关时的时间
	// private long initTime;
	private Timer timer;

	private ButtonListener buttonListener = new ButtonListener();

	private MyTimerTask countDownTask;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TIME_OVER:
				isPause = false;
				tbt.setChecked(false);
				Toast.makeText(getApplicationContext(), "时间到", 0).show();
				countDownTask.cancel();
				countDownTask = null;
				timer.cancel();
				timer = null;
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		setListener();

	}

	private void initView() {
		et_tottal = (EditText) findViewById(R.id.editText1);
		tbt = (ToggleButton) findViewById(R.id.toggleButton1);
	}

	private void setListener() {
		tbt.setOnClickListener(buttonListener);
	}

	private class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.toggleButton1:
				boolean isCheck = tbt.isChecked();
				// 获取输入框中的时间
				int edit_time = Integer
						.parseInt(et_tottal.getText().toString());
				if (isCheck) {

					if (!isPause) { // 当为暂停状态时，总倒计时不需要从输入框中获取
						total = edit_time;
					}

					if (total > 0) {
						timer = new Timer();
						countDownTask = new MyTimerTask();
						// 每隔1s执行一次
						timer.schedule(countDownTask, 0, SECOND);
					}
				} else {
					// 当按下关时检查下当前时间，看看是否满足暂停的条件
					if (total != 0) {
						isPause = true;
					}
					countDownTask.cancel();
					countDownTask = null;
					timer.cancel();
					timer = null;
				}

				break;

			default:
				break;
			}
		}
	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			// 让倒计时减1秒
			if (total > 0) {
				total--;
			}
			Logger.i(TAG, "倒计时：" + total);
			if (total == 0) {
				Message msg = Message.obtain();
				msg.what = TIME_OVER;
				handler.sendMessage(msg);
			}
		}

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
}
