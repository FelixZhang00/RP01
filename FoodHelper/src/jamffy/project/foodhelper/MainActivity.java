package jamffy.project.foodhelper;

import jamffy.project.foodhelper.db.InitInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_key;
	private Button bt_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		setListener();
	}

	private void init() {
		et_key = (EditText) findViewById(R.id.et_key);
		bt_search = (Button) findViewById(R.id.bt_search);
		initData();
	}

	/**
	 * 初始化数据,向数据库中插入数据
	 */
	private void initData() {
		InitInfo info = new InitInfo(this);
		info.initFoodsInfo();

	}

	private void setListener() {
		bt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String key = et_key.getText().toString().trim();
				if (!TextUtils.isEmpty(key)) {
					Intent intent = new Intent(MainActivity.this,
							ResultActivity.class);
					// 传递用户输入的搜索关键字
					intent.putExtra("key", key);
					startActivity(intent);

				} else {
					Toast.makeText(getApplicationContext(), "请先添加关键字", 0)
							.show();
				}

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
}
