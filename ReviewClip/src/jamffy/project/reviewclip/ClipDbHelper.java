package jamffy.project.reviewclip;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ClipDbHelper extends SQLiteOpenHelper {

	public final static String DATABASE_NAME = "mydatabase.db";
	public final static String DATABASE_TABLE = "ClipRaw";
	public final static int DATABASE_VERSION = 1;

	public final static String KEY_ID = "_id";
	public final static String KEY_CLIPTEXT_COLUMN = "cliptext";
	public final static String KEY_DATETIME_COLUMN = "timecreate";

	// 创建表的sql语句
	private final static String TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE
			+ "(" + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
			+ KEY_CLIPTEXT_COLUMN + " text NOT NULL," + KEY_DATETIME_COLUMN
			+ " datetime" + ");";

	public ClipDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
