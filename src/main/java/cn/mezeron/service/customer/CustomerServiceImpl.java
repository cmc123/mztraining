package cn.mezeron.service.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;
import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.customer.dao.CustomerDao;
import cn.mezeron.domain.customer.model.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	CustomerDao customerDao;
	
	/**
	 * 按分页查询所有客户
	 */
	@POST
	@Path("customer/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<List> getCustomerList(Map<String,Object> params) {
		
		Integer page = (Integer)params.get("page");
		Integer pageSize = (Integer)params.get("pageSize");
		if (page == null || pageSize == null) {
			return new RestEntity<List>().fail("请提供分页参数！");
		}
		
		String search = (String)params.get("search");
		String orderSpec = (String)params.get("orderSpec");
		
		String sql = "select a from jz_customer as a" + 
					"where status = :status ";
		if (orderSpec != null && !orderSpec.isEmpty())
			sql += orderSpec;
		else
			sql += "order by createdTime desc";
		
		Map<String,Object> qp = new HashMap<String,Object>();
		qp.put("status", "活动");
		
		List list = customerDao.queryBySql(sql, qp, page, pageSize);
		
		return new RestEntity<List>().ok(list);
	}
	/**
	 * 获取一个客户的所有信息
	 */
	@GET
	@Path("customer/getById/{customerId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Customer> getById(@PathParam("customerId")String customerId) {
		logger.info("正在执行getbyid方法。。。。。。。。。。");
		Customer customer = customerDao.get(customerId);
		return new RestEntity<Customer>().ok(customer);
	}
	/**
	 * 按生日
	 * 和本月到店、本月消费、本月消耗、会员级别、上次到店时间进行排序
	 */
	@POST
	@Path("customer/listByEmployee")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<List> getCustomerListByParams(Map<String,Object> params,HttpServletRequest request){
 
		request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		
		//记录页码和页大小
		Integer page = (Integer)params.get("page");
		Integer pageSize = (Integer)params.get("pageSize");
		
		if (page == null || pageSize == null) {
			logger.info("page或者pagesize为空");
			return new RestEntity<List>().fail("请提供分页参数！");
		}
		
		//获取username并对其进行判断
		String jobNumber=(String) params.get("username");
		if(jobNumber==null){
			logger.info("jobNumber为空，必须重新登录");
			return new RestEntity<List>().fail("用户登录失效，重新登录");
		}

		String orderSpec = (String)params.get("orderSpec");					//指定正排序还是逆排序，默认asc
		String orderSpecFieldName=(String)params.get("orderSpecFieldName"); //指定根据哪个字段进行排序，初期默认客户名字排序
		String birthQuery=(String) params.get("birthQuery");		//指定是查询本月生日还是下月生日的客户，默认查询所有String
		String search=(String)params.get("search");
		
		//开始赋值
		QueryParam param=new QueryParam();
		param.setPage(page);
		param.setPageSize(pageSize);
		
		//重写了basicdaoImpl的queryByParam，为queryspec新建一个含4个参数的构造函数，使得查询出来的顾客能与对应员工绑定
		param.getQuerySpecs().add(new QuerySpec("employee","jobNumber", QuerySpec.EQ, jobNumber));
		
		//获取当前月份
		Integer currentMonth=new Date().getMonth()+1;
		logger.info("现在月份："+currentMonth.toString());
		birthQuery=birthQuery==null?"所有人":birthQuery;
		
		//打印全部传送过来的信息
		logger.info("页码:"+page+",页面大小："+pageSize+",生日月份:"+birthQuery+",字段排序:"+orderSpecFieldName+"，升降"+orderSpec);
		
		//如果是本月生日或者下月生日，赋值
		if(birthQuery.equals("本月生日")||birthQuery.equals("下月生日")){
			QuerySpec querySpec=null;
			if(birthQuery.equals("本月生日")){
				logger.info("执行本月生日的查询");
				querySpec=new QuerySpec("birthMonth", QuerySpec.EQ, currentMonth);  //本月生日
			}
			else {
				querySpec=new QuerySpec("birthMonth", QuerySpec.EQ, currentMonth+1); //下月生日
			}
			param.getQuerySpecs().add(querySpec);
		}
		
		//排序字段是会员级别呢还是姓名呢还是年龄
		if(orderSpecFieldName.equals("会员级别")){
			logger.info("会员级别排序");
			orderSpecFieldName="level";
		}else if(orderSpecFieldName.equals("年龄")){
			orderSpecFieldName="birthYear";
		}else{
			orderSpecFieldName="name";	//默认是姓名
		}
		
		//排序是升序还是降序
		if(orderSpec.equals("降序")){
			orderSpec="desc";
		}else{
			orderSpec="asc";	//默认是升序
		}
		//最后将排序字段和排序规则放到param中
		logger.info("进入搜索"+orderSpecFieldName+":"+orderSpec);
		param.addOrderSpec(orderSpecFieldName, orderSpec);
		if(search!=null){
			param.setSearch(search);
			String[] searchFieldList={"phoneNumber","name"};
			param.setSearchFieldList(searchFieldList);
		}
		logger.info(param);
		List<Customer> list=customerDao.queryByParam(param);
		
		logger.info("获得list的长度"+list.size());
		return new RestEntity<List>().ok(list);
	}
}
