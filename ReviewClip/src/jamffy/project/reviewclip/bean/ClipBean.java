package jamffy.project.reviewclip.bean;

/**
 * 复制实体
 * 
 * @author tmac
 *
 */
public class ClipBean {
	private int _id;
	private String cliptext;
	private String timecreate;

	public ClipBean(int _id, String cliptext, String timecreate) {
		super();
		this._id = _id;
		this.cliptext = cliptext;
		this.timecreate = timecreate;
	}

	public ClipBean() {
		super();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getCliptext() {
		return cliptext;
	}

	public void setCliptext(String cliptext) {
		this.cliptext = cliptext;
	}

	public String getTimecreate() {
		return timecreate;
	}

	public void setTimecreate(String timecreate) {
		this.timecreate = timecreate;
	}

	@Override
	public String toString() {
		return "ClipBean [_id=" + _id + ", cliptext=" + cliptext
				+ ", timecreate=" + timecreate + "]";
	}

}
