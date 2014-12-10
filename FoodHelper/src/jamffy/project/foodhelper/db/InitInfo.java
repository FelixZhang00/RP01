package jamffy.project.foodhelper.db;

import android.content.Context;
import jamffy.project.foodhelper.domain.Food;

public class InitInfo {

	private Context context;

	public InitInfo(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 向数据库中添加若干条数据
	 */
	public void initFoodsInfo() {
		FoodDao dao = new FoodDao(context);

		Food item = new Food();
		item.setId(1);
		item.setTitle("黑胡椒烤翅");
		item.setMaterial("鸡翅,黑胡椒粉,姜");
		item.setSummary("外皮烤的焦香，里面嫩滑多汁，允指回味呀");

		dao.insert(item);

		item = new Food();
		item.setId(2);
		item.setTitle("酱爆手撕包菜");
		item.setMaterial("卷心菜,甜面酱,白糖,蒜肉,姜");
		item.setSummary("卷心菜的水分含量高达90%，热量低，VC的含量比大白菜高出一倍左右。另外富含叶酸，所以准妈妈及贫血患者应当多吃些卷心菜。赶紧试试吧，简单易做，下饭极了。");

		dao.insert(item);

		item = new Food();
		item.setId(3);
		item.setTitle("秘制羊杂汤");
		item.setMaterial("羊骨,羊杂（肝肺肚血脑舌）,羊头肉,姜");
		item.setSummary("天气突然变冷，一碗热乎乎的羊杂汤，羊肉性温热，补气滋阴，暖中补虚是冬季平民进补佳品，自制实在又卫生！一大锅，全家人都暖暖的！");

		dao.insert(item);

	}
}
