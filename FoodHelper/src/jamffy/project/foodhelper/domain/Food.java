package jamffy.project.foodhelper.domain;

import java.util.List;

public class Food {

	private int id; // 编号
	private String title; // 菜名
	private String material; // 原料 以逗号分隔
	private String extra; // 附加的数据

	private String Summary;
	private String Link;
	private String ImgUrl;

	public Food() {
		super();
	}

	public Food(int id, String title, String materials, String extra) {
		super();
		this.id = id;
		this.title = title;
		this.material = materials;
		this.extra = extra;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String summary) {
		Summary = summary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String materials) {
		this.material = materials;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", title=" + title + ", materials="
				+ material + ", extra=" + extra + "]";
	}

}
