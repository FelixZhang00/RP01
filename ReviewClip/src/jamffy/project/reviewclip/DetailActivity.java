package jamffy.project.reviewclip;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 单击条目后，显示详细的摘录信息
 * 
 * @author tmac
 *
 */
public class DetailActivity extends Activity {
	private TextView tv_summary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_review);
		initView();
	}

	private void initView() {
		tv_summary = (TextView) findViewById(R.id.tv_summary);
		int id = getIntent().getIntExtra("ID", 0);
		if (id != 0) {
			ClipDbHelper dbHelper = new ClipDbHelper(this);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.query(ClipDbHelper.DATABASE_TABLE,
					new String[] { ClipDbHelper.KEY_CLIPTEXT_COLUMN },
					ClipDbHelper.KEY_ID + "=" + id, null, null, null, null);
			if (cursor.moveToNext()) {

				tv_summary.setText(cursor.getString(cursor
						.getColumnIndex(ClipDbHelper.KEY_CLIPTEXT_COLUMN)));
			}

		}
	}

}
