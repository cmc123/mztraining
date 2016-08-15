package cn.mezeron.domain.store.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.store.model.Store;

@Repository("storeDao")
public class StoreDaoImpl extends BasicDaoImpl<Store>implements StoreDao {

	public StoreDaoImpl() {
		// TODO Auto-generated constructor stub
		super(Store.class);
	}

}
