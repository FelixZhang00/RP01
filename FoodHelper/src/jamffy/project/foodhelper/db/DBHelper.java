package jamffy.project.foodhelper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;

public class DBHelper extends SQLiteOpenHelper {
	private static String DB_NAME = "foodhelper.db";
	private static int DB_CURRENT_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_CURRENT_VERSION);
	}

	public static final String TABLE_ID_COL = "_ID";

	/**
	 * 菜谱表
	 */
	public static final String TABLE_FOOD = "food";
	public static final String TABLE_FOOD_ID = "id"; // 每道菜谱的编号
	public static final String TABLE_FOOD_TITLE_COL = "TITLE";
	public static final String TABLE_FOOD_SUMMARY_COL = "SUMMARY";
	public static final String TABLE_FOOD_LINK_COL = "LINK";
	public static final String TABLE_FOOD_MATERIAL = "MATERIAL";
	public static final String TABLE_FOOD_AUTHOR_COL = "AUTHOR";
	public static final String TABLE_FOOD_SOURCE_COL = "SOURCE";
	public static final String TABLE_FOOD_IMGURL_COL = "IMGURL";

	@Override
	public void onCreate(SQLiteDatabase db) {

		// 第一安装执行
		// 注意空格
		db.execSQL("CREATE TABLE " + TABLE_FOOD + " (" + TABLE_ID_COL
				+ " integer primary key autoincrement, " + TABLE_FOOD_ID
				+ " integer not null, " + TABLE_FOOD_TITLE_COL
				+ " varchar(50), " + TABLE_FOOD_SUMMARY_COL + " varchar(200), "
				+ TABLE_FOOD_MATERIAL + " varchar(200), " + TABLE_FOOD_LINK_COL
				+ " varchar(200), " + TABLE_FOOD_AUTHOR_COL + " varchar(200), "
				+ TABLE_FOOD_SOURCE_COL + " varchar(200), "
				+ TABLE_FOOD_IMGURL_COL + " varchar(200))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 应用数据库升级时执行

	}

}
