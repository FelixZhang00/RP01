package jamffy.project.reviewclip;

import jamffy.project.reviewclip.bean.ClipBean;
import jamffy.project.reviewclip.util.Logger;
import jamffy.project.reviewclip.view.adapter.MyAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 单独保存每次复制的文字至数据库
 * 
 * @author tmac
 *
 */
public class SaveClipService extends Service {

	protected static final String TAG = "SaveClipService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.i(TAG, "service oncreate");
		final ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {
			@Override
			public void onPrimaryClipChanged() {
				ClipData data = cm.getPrimaryClip();
				Item item = data.getItemAt(0);

				Logger.i(TAG, "复制的内容：" + item.getText().toString());
				// 存到数据库中
				try {
					ClipDbHelper helper = new ClipDbHelper(
							getApplicationContext());
					ContentValues values = new ContentValues();

					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					String time = format.format(new Date());
					values.put(ClipDbHelper.KEY_CLIPTEXT_COLUMN, item.getText()
							.toString());
					values.put(ClipDbHelper.KEY_DATETIME_COLUMN, time);

					SQLiteDatabase db = helper.getWritableDatabase();
					int id = (int) db.insert(ClipDbHelper.DATABASE_TABLE, null,
							values);
					Toast.makeText(getApplicationContext(), "摘录成功", 0).show();

					// TODO 向内存中的clipBeans增加数据
					MyAdapter.getClipBeans().add(
							new ClipBean(id, item.getText().toString(), time));
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "摘录失败", 0).show();
				}

				// Intent mIntent = new Intent();
				// mIntent.setAction("com.cybertron.dict.ClipBoardReceiver");
				// mIntent.putExtra("clipboardvalue",
				// item.getText().toString());
				// sendBroadcast(mIntent);

			}
		});

	}

}
