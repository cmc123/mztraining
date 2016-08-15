package cn.mezeron.domain.basic.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

import cn.mezeron.common.util.query.QueryParam;

/**
 * 通用 DAO (Data Access Object) 包含了对POJO的CRUD基本操作
 * @author <a href="mailto:neo@mezeron.cn">Neo</a>
 * @param <T> Model类
 */
public interface BasicDao<T> {
	
	/**
	 * 根据主键Id获取实体对象
	 * @param id
	 * @return T
	 */
	public T get(String id);
	
	/**
	 * 保存记录
	 * @param object
	 * @return 返回合并后的实体对象记录
	 */
	public T save(T object);
	
	/**
	 * 删除实体记录
	 * @param id 对象主键Id
	 */
	public void remove(String id);

	/**
	 * 删除实体记录
	 * @param object 实体对象
	 */
	public void remove(T object);

	/**
	 * 根据查询条件返回单个对象
	 * @param param 查询条件
	 * @return 结果列表
	 */
	public T queryUniqueResultByParam(QueryParam param);
	
	/**
	 * 根据查询条件进行查询
	 * @param param 查询条件
	 * @return 结果列表
	 */
//	public List<T> queryByParam(QueryParam param);
	public List<T> queryByParam(QueryParam param);
	/**
	 * 执行原生SQL查询语句
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 返回单值对象
	 */
	public Map<String, Object> queryUniqueResultBySql(String sql, Map<String, Object> params);

	/**
	 * 执行原生SQL查询语句
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 结果以Map方式返回
	 */
	public List queryBySql(String sql, Map<String, Object> params);
	
	/**
	 * 执行原生SQL查询语句
	 * @param sql SQL语句
	 * @param params 参数
	 * @return 结果以Map方式返回
	 */
	public List queryBySql(String sql, Map<String, Object> params, int page, int pageSize);
	
}
