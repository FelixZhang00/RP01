package jamffy.project.foodhelper;

import java.util.ArrayList;
import java.util.List;

import jamffy.project.foodhelper.db.FoodDao;
import jamffy.project.foodhelper.db.InitInfo;
import jamffy.project.foodhelper.domain.Food;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {

	/**
	 * 显示所有符合条件的搜索结果的控件
	 */
	private ListView lv;
	private MyAdapter adapter;

	private Context context;
	private String key; // 查询数据库的关键字

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		context = this;
		key = (String) getIntent().getExtras().get("key");
		Toast.makeText(getApplicationContext(), key, 0).show();
		init();
		setListener();
	}

	private void init() {
		lv = (ListView) findViewById(R.id.lv_result);
		adapter = new MyAdapter();
		lv.setAdapter(adapter);

	}

	private void setListener() {

	}

	public class MyAdapter extends BaseAdapter {

		private List<Food> resultFoods = new ArrayList<Food>();

		public MyAdapter() {
			super();
			initData();
		}

		private void initData() {
			FoodDao dao = new FoodDao(context);
			resultFoods = dao.queryByKey(key);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return resultFoods.size();
		}

		@Override
		public Object getItem(int position) {
			return resultFoods.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.food_item, null);
			}
			TextView tv_title = (TextView) convertView
					.findViewById(R.id.food_title);
			TextView tv_desc = (TextView) convertView
					.findViewById(R.id.food_description);
			TextView tv_summy = (TextView) convertView
					.findViewById(R.id.tv_food_summ);

			String title = resultFoods.get(position).getTitle();
			String desc = resultFoods.get(position).getMaterial();
			String summary = resultFoods.get(position).getMaterial();
			tv_title.setText(title);
			tv_desc.setText(desc);
			tv_summy.setText(summary);

			return convertView;
		}

	}

}
