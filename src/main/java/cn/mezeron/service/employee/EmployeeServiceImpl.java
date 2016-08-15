package cn.mezeron.service.employee;


import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.jws.WebParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;
import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.customer.dao.CustomerDao;
import cn.mezeron.domain.customer.model.Customer;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	protected final Log logger = LogFactory.getLog(getClass());
	@Resource
	private EmployeeDao employeeDao;
	@Resource
	private CustomerDao customerDao;
	@GET
	@Path("employee/getById/{Id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Employee> getById(@PathParam("Id")String id) {
		logger.info("发送过来的id"+id);
		// TODO Auto-generated method stub
		if(id==null||id.isEmpty()||id.equals("")){
			return new RestEntity<Employee>().fail("id无效");
		}
		Employee employee=employeeDao.get(id);
		return new RestEntity<Employee>().ok(employee);
	}

	@GET
	@Path("employee/getByJobNumber/{jobNumber}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public RestEntity<Employee> getByJobNumber(@PathParam("jobNumber")String jobNumber) {
		logger.info("发送过来的jobNumber:"+jobNumber);
		// TODO Auto-generated method stub
		QueryParam param=new QueryParam();
		param.addQuerySpec("jobNumber", QuerySpec.EQ, jobNumber);
		Employee employee=employeeDao.queryUniqueResultByParam(param);
		return new RestEntity<Employee>().ok(employee);
	}
	
	/**
	 * 用户登录
	 * @param params
	 * @return
	 */
	@POST
	@Path("employee/login")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Employee> login(Map<String,Object> params,HttpServletRequest request){
		request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		String username=(String) params.get("username");
		String password=(String) params.get("password");
		logger.info("username和password:"+username+password);
		if(username==null||password==null||username.equals("")||password.equals("")){
			return new RestEntity<Employee>().fail("用户无效");
		}
		QueryParam param=new QueryParam();
		param.addQuerySpec("jobNumber", QuerySpec.EQ, username);
		Employee employee=employeeDao.queryUniqueResultByParam(param);
		if(employee.getPassword().equals(password)){
			request.getSession().setAttribute("jobNumber", username);
			logger.info("用户已登录:"+request.getSession().getAttribute("jobNumber").toString());
			return new RestEntity<Employee>().ok(null);
		}
		return new RestEntity<Employee>().fail("用户不存在或密码错误");
	}
	/**
	 * 
	 */
	@POST
	@Path("employee/changePassword")
	@Produces({ MediaType.APPLICATION_JSON })
	public RestEntity<Employee> changePassword(Map<String,Object> params,HttpServletRequest request) {
		request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		logger.info("session得到的信息"+(String)request.getAttribute("jobNumber"));
		String jobNumber=(String) params.get("jobNumber");
		String newPassword=(String) params.get("password");
		String oldPassword=(String)params.get("oldPassword");
		logger.info(newPassword+":"+oldPassword);
		logger.info("jobNUmber的值:"+jobNumber);
		if(newPassword.isEmpty()||newPassword==null||newPassword.equals("")){
			return new RestEntity<Employee>().fail("请给予有效密码");
		}
		QueryParam param=new QueryParam();
		param.addQuerySpec("jobNumber", QuerySpec.EQ, jobNumber);
		Employee employee=employeeDao.queryUniqueResultByParam(param);
		if(!employee.getPassword().equals(oldPassword)){
			logger.info("密码错误");
			return new RestEntity<Employee>().fail("登录密码错误");
		}
		employee.setPassword(newPassword);
		employeeDao.save(employee);
		return new RestEntity<Employee>().ok(null);
	}

	@POST
	@Path("employee/createNewCustomer")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public RestEntity<Employee> createNewCustomer(Map<String, Object> params){
		
		
		String jobNumber=(String) params.get("jobNumber");
		if(jobNumber==null||jobNumber.equals("")){
			return new RestEntity<Employee>().fail("登录无效，重新登录");
		}
		
		Customer customer=new Customer();
		logger.info("生日:"+params.get("birthDay"));
		customer.setBirthDay((Integer)params.get("birthDay"));
		customer.setBirthMonth((Integer)params.get("birthMonth"));
		customer.setBirthYear((Integer)params.get("birthYear"));
		customer.setName((String)params.get("name"));
		customer.setSex((String)params.get("sex"));
		customer.setPhoneNumber((String)params.get("phoneNumber"));
		
		RestEntity<Employee> restEntity=this.getByJobNumber(jobNumber);
		Employee employee=restEntity.getData();
		if(employee==null){
			return new RestEntity<Employee>().fail("用户不存在");
		}
		System.out.println(customer.getName()+":"+customer.getPhoneNumber());
		customerDao.save(customer);
		employee.getCustomer().add(customer);
		employeeDao.save(employee);
		return new RestEntity<Employee>().ok(null);
	}
	
	@POST
	@Path("employee/getEmployeeList")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<List> showEmployeeList(Map<String, Object> params){
		
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
		String roleName=(String)params.get("roleName"); 
		String birthQuery=(String) params.get("birthQuery");	
		String search=(String)params.get("search");
		
		//开始赋值
		QueryParam param=new QueryParam();
		param.setPage(page);
		param.setPageSize(pageSize);

		RestEntity<Employee> restEntity=this.getByJobNumber(jobNumber);
		Employee employee=restEntity.getData();
		
		param.getQuerySpecs().add(new QuerySpec("store","id", QuerySpec.EQ, employee.getStore().getId()));
		
		//获取当前月份
		Integer currentMonth=new Date().getMonth()+1;
		logger.info("现在月份："+currentMonth.toString());
		birthQuery=birthQuery==null?"所有人":birthQuery;
		
		//打印全部传送过来的信息
		
		
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
		if(roleName.equals("店长")){
			roleName="店长";
		}else if(roleName.equals("美容师")){
			roleName="美容师";
		}else if(roleName.equals("顾问")){
			roleName="顾问";	//默认是姓名
		}else{
			roleName="所有人";
		}
		
		//排序是升序还是降序
		if(orderSpec.equals("降序")){
			orderSpec="desc";
		}else{
			orderSpec="asc";	//默认是升序
		}
		//最后将排序字段和排序规则放到param中
		if(!roleName.equals("所有人")){
			param.addQuerySpec(new QuerySpec("role", "rolePlay", QuerySpec.EQ, roleName));
		}
		if(search!=null){
			logger.info("搜索"+search);
			param.setSearch(search);
			String[] searchFieldList={"phoneNumber","name"};
			param.setSearchFieldList(searchFieldList);
		}
		logger.info("获取员工列表:"+"页码:"+page+",页面大小："+pageSize+",生日月份:"+birthQuery+",字段排序:"+roleName+"，升降"+orderSpec);
		logger.info(employee.getName()+":"+employee.getStore().getName());
		List<Employee> list=employeeDao.queryByParam(param);
		
		logger.info("获得list的长度"+list.size());
		return new RestEntity<List>().ok(list);
	}
}
