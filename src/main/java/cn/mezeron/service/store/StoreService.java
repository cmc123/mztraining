package cn.mezeron.service.store;

import java.util.List;
import java.util.Map;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.store.model.Store;

public interface StoreService {

	/**
	 * 获取所有门店
	 * @return
	 */
	public RestEntity<List> getStoreList(Map<String,Object> params);
	
	/**
	 * 根据id获取门店信息
	 * @param customerId
	 * @return
	 */
	public RestEntity<Store> getById(String id);
	/**
	 * 根据门店名字获取门店信息
	 * @param name 门店名字
	 * @return
	 */
	public RestEntity<Store> getByName(String name);
}
