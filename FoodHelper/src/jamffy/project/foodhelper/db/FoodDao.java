package jamffy.project.foodhelper.db;

import java.util.ArrayList;
import java.util.List;

import jamffy.project.foodhelper.domain.Food;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class FoodDao {

	private DBHelper dbHelper;

	public FoodDao(Context context) {
		this.dbHelper = new DBHelper(context);
	}

	public long insert(Food food) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cur = db.query(DBHelper.TABLE_FOOD, null, null, null, null,
				null, null);
		long count = cur.getCount();
		cur.close();
		// @leaveit 避免数据库中插入重复的数据 的权益之计
		if (count == 3) {
			System.out.println("数据表暂时保存3条数据");
			db.close();
			return -1;
		}

		ContentValues values = new ContentValues();
		if (food != null) {
			values.put(DBHelper.TABLE_FOOD_ID, food.getId());
			values.put(DBHelper.TABLE_FOOD_TITLE_COL, food.getTitle());
			values.put(DBHelper.TABLE_FOOD_MATERIAL, food.getMaterial()
					.toString());
			String summary = food.getSummary();
			if (summary != null && !TextUtils.isEmpty(summary)) {
				values.put(DBHelper.TABLE_FOOD_SUMMARY_COL, summary);
			}
			long result = db.insert(DBHelper.TABLE_FOOD, null, values);
			db.close();
			return result;

		}
		db.close();
		return -1;

	}

	/**
	 * 在原料materials按关键词查找
	 * 
	 * @param key
	 * @return
	 */
	public List<Food> queryByKey(String key) {
		List<Food> foods = new ArrayList<Food>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		StringBuilder str = new StringBuilder();
		str.append("select * from food where material LIKE '%");
		str.append(key);
		str.append("%'");
		Cursor cursor = db.rawQuery(str.toString(), null);

		// @leaveit 添加的数据不全
		while (cursor.moveToNext()) {
			Food food = new Food();
			food.setTitle(cursor.getString(2));
			food.setSummary(cursor.getString(3));
			food.setMaterial(cursor.getString(4));
			foods.add(food);
		}

		cursor.close();
		db.close();
		return foods;
	}

}
