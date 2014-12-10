package jamffy.project.foodhelper.db;

import java.io.Serializable;
import java.util.List;

/**
 * 对实体操作的封装
 * 
 * @author Administrator
 * 
 * @param <M>
 *            实体
 */
public interface Dao<M> {
	/**
	 * 添加
	 * 
	 * @param m
	 * @return
	 */

	Serializable save(M m);

	/**
	 * 修改
	 * 
	 * @param m
	 */
	void update(M m);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(Serializable id);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<M> findAll();
	
	
	
	/******测试使用*******/
	String getTableName(M m);
}
