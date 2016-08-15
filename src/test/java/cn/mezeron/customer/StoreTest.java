package cn.mezeron.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.role.dao.RoleDao;
import cn.mezeron.domain.role.model.Role;
import cn.mezeron.domain.store.dao.StoreDao;
import cn.mezeron.domain.store.model.Store;
import cn.mezeron.service.store.StoreService;

@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class StoreTest {

	@Resource
	private StoreDao storeDao;
	
	@Resource
	private StoreService storeService;
	@Resource
	private RoleDao roleDao;
	@Test
	public void testGetStoreList(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("page", 0);
		params.put("pageSize", 4);
		RestEntity<List> list=storeService.getStoreList(params);
		Iterator<Store> iterator=list.getData().iterator();
		int i=0;
		while (iterator.hasNext()) {
			Store store = (Store) iterator.next();
			System.out.println(++i+":"+store.getName());
		}
	}
	@Test
	public void testInsertStore(){
		int i=3;
		while(i-->0){
			Store store=new Store();
			store.setName("番禺店"+i+"号");
			storeDao.save(store);
		}
	}
	
	@Test
	public void testRole(){
		Role role =new Role();
		role.setRolePlay("美容师");
		roleDao.save(role);
	}
}
