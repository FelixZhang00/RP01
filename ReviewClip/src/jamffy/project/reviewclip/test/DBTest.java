package jamffy.project.reviewclip.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import jamffy.project.reviewclip.ClipDbHelper;
import jamffy.project.reviewclip.bean.ClipBean;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase {

	public void testDBCreate() {
		ClipDbHelper helper = new ClipDbHelper(getContext());
		helper.getWritableDatabase();
	}

	public void save() {
		ClipDbHelper helper = new ClipDbHelper(getContext());
		ContentValues values = new ContentValues();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		values.put(ClipDbHelper.KEY_CLIPTEXT_COLUMN, "text3");
		values.put(ClipDbHelper.KEY_DATETIME_COLUMN, time);

		SQLiteDatabase db = helper.getWritableDatabase();
		db.insert(ClipDbHelper.DATABASE_TABLE, null, values);
	}

	public void query() {
		ClipDbHelper dbHelper = new ClipDbHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(ClipDbHelper.DATABASE_TABLE, null, null, null,
				null, null, ClipDbHelper.KEY_ID + " desc");
		System.out.println("游标的数量：" + cursor.getCount());
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String text = cursor.getString(1);
			System.out.println(id + text);
		}

	}

	public void query2() {
		List<ClipBean> clipBeans = new ArrayList<ClipBean>();
		ClipDbHelper dbHelper = new ClipDbHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(ClipDbHelper.DATABASE_TABLE, null, null, null,
				null, null, ClipDbHelper.KEY_ID + " desc");
		while (cursor.moveToNext()) {
			ClipBean clipBean = new ClipBean();
			clipBean.set_id(cursor.getInt(0));
			clipBean.setCliptext(cursor.getString(1));
			clipBean.setTimecreate(cursor.getString(2));
			clipBeans.add(clipBean);
			clipBean = null;
		}
		cursor.close();
		System.out.println(clipBeans.toString());
	}

}
