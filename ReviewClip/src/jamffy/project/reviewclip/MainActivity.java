package jamffy.project.reviewclip;

import jamffy.project.reviewclip.bean.ClipBean;
import jamffy.project.reviewclip.util.Logger;
import jamffy.project.reviewclip.view.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * 检查数据库，以listview的形式现实在界面上
 * 
 * @author tmac
 *
 */
public class MainActivity extends Activity {

	protected static final String TAG = "MainActivity";

	private ListView lv;
	private MyAdapter adapter;
	private Button btn;

	private boolean mBackKeyPressedTimes = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logger.i(TAG, "onCreate");
		setContentView(R.layout.ctrl_review);

		// 开启服务
		Intent serviceIntent = new Intent();
		serviceIntent.setClass(MainActivity.this, SaveClipService.class);
		startService(serviceIntent);
		System.out.println("开启服务后，还留在activity中");
		initView();

	}

	private void initView() {
		lv = (ListView) findViewById(R.id.lv_crtlreview);
		btn = (Button) findViewById(R.id.bt_ctrl_sg_add);
		adapter = new MyAdapter(this);
		lv.setAdapter(adapter);

		// 给listview注册上下文菜单
		registerForContextMenu(lv);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Logger.i(TAG, "模拟下拉刷新");
				// adapter.notifyDataSetChanged();
				// notifyDataChanged();
			}

		});
	}

	/**
	 * 通知更新listview中的数据（以推倒重来的方式，效率很低）
	 */
	private void notifyDataChanged() {
		MyAdapter.clearClipBeans();
		adapter.scanClipDB();
		adapter.notifyDataSetChanged();
	}

	// 创建listview的上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.review_item_menu, menu);
	}

	// 当上下文菜单被点击后执行
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int id = (int) info.id;
		ClipBean clipBean = MyAdapter.getClipBeans().get(id);
		Logger.i(TAG, "选中：" + clipBean.toString());
		switch (item.getItemId()) {
		case R.id.item_alter:
			Logger.i(TAG, "alter");
			// 切换到新的activity
			Intent intent = new Intent(MainActivity.this, DetailActivity.class);
			startActivity(intent);
			return true;
		case R.id.item_copy:
			Logger.i(TAG, "copy");
			// copyItem(MyAdapter.getClipBeans().get(id));
			return true;
		case R.id.item_delete:
			Logger.i(TAG, "delete");
			// TODO 弹出对话框，提示删除的内容
			deleteItem(id, MyAdapter.getClipBeans().get(id).get_id());
			Toast.makeText(getApplicationContext(), "删除成功", 0).show();
			return true;

		default:
			return super.onContextItemSelected(item);
		}
	}

	/**
	 * 删除内存和数据库中的所选内容
	 * 
	 * @param _id
	 *            数据库表中的唯一编号
	 * @param position
	 *            所选项在内存中（List：ClipBeans）的位置
	 */
	private void deleteItem(int position, int _id) {
		ClipDbHelper dbHelper = new ClipDbHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(ClipDbHelper.DATABASE_TABLE, ClipDbHelper.KEY_ID + "=" + _id,
				null);

		MyAdapter.getClipBeans().remove(position);
		adapter.notifyDataSetChanged();
	}

	/**
	 * 将选中条目的文字拷贝至系统剪切板
	 * 
	 * @param clipBean
	 */
	private void copyItem(ClipBean clipBean) {
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.setText("新的内容");
	}

	@Override
	protected void onResume() {
		Logger.i(TAG, "OnResume");
		if (adapter != null) {
			notifyDataChanged();
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		Logger.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Logger.i(TAG, "onDestory");
		MyAdapter.getClipBeans().clear();
		super.onDestroy();
	}

	// 按返回键后调用的方法
	@Override
	public void onBackPressed() {
		if (!mBackKeyPressedTimes) {
			Toast.makeText(getApplicationContext(), "再按一次退出应用",
					Toast.LENGTH_SHORT).show();
			mBackKeyPressedTimes = true;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						mBackKeyPressedTimes = false;
					}
					super.run();
				}
			}.start();
			return; // 在打开上面的新线程的同时，已经return了
		} else {
			// 仍然在后台驻留
			finish();
		}
		super.onBackPressed(); // 抢在调用父类方法之前
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
