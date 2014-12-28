package jamffy.project.reviewclip.view.adapter;

import jamffy.project.reviewclip.ClipDbHelper;
import jamffy.project.reviewclip.R;
import jamffy.project.reviewclip.bean.ClipBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 查询数据库，填充listview
 * 
 * @author tmac
 *
 */
public class MyAdapter extends BaseAdapter {
	static List<ClipBean> clipBeans = new ArrayList<ClipBean>();
	// 采用栈，后进先出，保证listview最顶部的item是最新的。
	// static Stack<ClipBean> clipBeans = new Stack<ClipBean>();
	private Context context;
	private LayoutInflater inflater;

	private TextView tv_text;
	private TextView tv_time;

	public static List<ClipBean> getClipBeans() {
		return clipBeans;
	}

	public MyAdapter(Context context) {
		super();
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		scanClipDB();

	}

	/**
	 * 清空当前的集合
	 */
	public static void clearClipBeans() {
		clipBeans.clear();
	}

	/**
	 * 扫描存放剪切内容的数据库
	 */
	public void scanClipDB() {
		ClipDbHelper dbHelper = new ClipDbHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(ClipDbHelper.DATABASE_TABLE, null, null, null,
				null, null, ClipDbHelper.KEY_ID + " desc"); // 从旧到新的顺序排列
		while (cursor.moveToNext()) {
			ClipBean clipBean = new ClipBean();
			clipBean.set_id(cursor.getInt(0));
			clipBean.setCliptext(cursor.getString(1));
			clipBean.setTimecreate(cursor.getString(2));
			clipBeans.add(clipBean);
			clipBean = null;
		}
		cursor.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return clipBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return clipBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = inflater.inflate(R.layout.ctrl_review_item, null);
		}
		tv_text = (TextView) view.findViewById(R.id.tv_text);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_text.setText(clipBeans.get(position).getCliptext());
		tv_time.setText(clipBeans.get(position).getTimecreate());

		return view;
	}

}
