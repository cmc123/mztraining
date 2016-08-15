package cn.mezeron.service.store;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Service;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;
import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.store.dao.StoreDao;
import cn.mezeron.domain.store.model.Store;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	@Resource
	private StoreDao storeDao;
	
	@POST
	@Path("store/getAll")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<List> getStoreList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Integer page = (Integer)params.get("page");
		Integer pageSize = (Integer)params.get("pageSize");
		if (page == null || pageSize == null) {
			return new RestEntity<List>().fail("请提供分页参数！");
		}
		QueryParam param=new QueryParam();
		param.setPage(page);
		param.setPageSize(pageSize);
		List<Store> list=storeDao.queryByParam(param);
		return new RestEntity<List>().ok(list);
	}

	@GET
	@Path("store/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Store> getById(@PathParam("id")String id) {
		// TODO Auto-generated method stub
		if(id==null){
			return new RestEntity<Store>().fail("请提供有用的id");
		}
		Store store=storeDao.get(id);
		if(store==null){
			return new RestEntity<Store>().fail("查无此门店");
		}
		return new RestEntity<Store>().ok(store);
	}

	@GET
	@Path("store/getByName/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Store> getByName(@PathParam("name")String name) {
		// TODO Auto-generated method stub
		if(name==null){
			return new RestEntity<Store>().fail("请提供有用的门店名字");
		}
		QueryParam param=new QueryParam();
		param.addQuerySpec("name", QuerySpec.EQ, name);
		Store store=storeDao.queryUniqueResultByParam(param);
		if(store==null){
			return new RestEntity<Store>().fail("查无此门店");
		}
		return new RestEntity<Store>().ok(store);
	}

}
