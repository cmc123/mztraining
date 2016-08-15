package cn.mezeron.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;
import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.customer.dao.CustomerDao;
import cn.mezeron.domain.customer.model.Customer;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;
import cn.mezeron.domain.role.dao.RoleDao;
import cn.mezeron.domain.role.model.Role;
import cn.mezeron.service.customer.CustomerService;
import cn.mezeron.service.customer.CustomerServiceImpl;
import cn.mezeron.service.employee.EmployeeService;

@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class CustomerTest {
	
	@Resource
	CustomerDao customerDao;
	
	@Resource
	CustomerServiceImpl customerService;
	
	@Resource
	EmployeeDao employeeDao;
	
	@Resource
	EmployeeService employeeService;
	
	@Resource
	RoleDao roleDao;
	@Test
	public void testCustomer() {
		int i=5;
		while(i-->0){
		Customer c = new Customer();
		c.setBirthDay(29);
		c.setBirthMonth(2);
		c.setBirthYear(1994);
		c.setCardNumber("1234526");
		c.setComments("");
		c.setName("翁小姐");
		c.setPhoneNumber("18688332681");
		c.setSalesTarget("");
		c.setSex("女");
		customerDao.save(c);
		}
	}
	
	@Test
	public void testCustomerQuery() {
	}
	
	@Test
	public void testCustomerService() {
		Customer c=customerDao.get("nGYpfkBMQki1sbIISZSNtg");
		System.out.println(c);
	}
	
	@Test
	public void testEmployee() {
//		RestEntity<Employee> employee=employeeService.getByJobNumber("GZ22529");
//		Employee e=employee.getData();
//		System.out.println(e.getJobNumber());
//		Role role=new Role();
//		role.setRolePlay("顾问");
//		System.out.println(role.getRolePlay());
//		roleDao.save(role);
//		e.getRole().add(role);
//		employeeDao.save(e);
//		Role role=new Role();
//		roleDao.save(role);
		Role role=roleDao.get("UyBjkS7uQ3mzZ7KQ74dOHw");
		System.out.println(role.getId());
		Employee employee=employeeDao.get("BNAP1oupT86_-IYakw2zNg");
		System.out.println(employee.getHeadPicture());
		employee.getRole().add(role);
		role.getEmployee().add(employee);
		employeeDao.save(employee);
		roleDao.save(role);
		
	}
	
	@Test
	public void testEmployeeService(){
		RestEntity<Employee> employee=employeeService.getByJobNumber("GZ22529");
		Employee e=employee.getData();
		System.out.println("工号:"+e.getJobNumber()+
							"\n员工姓名:"+e.getName()+
							"\n员工手机号码:"+e.getPhoneNumber()+
							"\n员工密码:"+e.getPassword());
		e.setName("测试人员1号");
		employeeDao.save(e);
	}
	
	@Test
	public void testGetCustomerListByParams(){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("username", "GZ22529");
		params.put("page", 0);
		params.put("pageSize", 8);
		params.put("orderSpec", "asc");
		params.put("orderSpecFieldName", "会员级别");
//		params.put("birthQuery", "下月生日");
		RestEntity<List> restEntity=customerService.getCustomerListByParams(params, null);
		Iterator<Customer> iterator=restEntity.getData().iterator();
		while (iterator.hasNext()) {
			Customer customer = (Customer) iterator.next();
			System.out.println(customer.getName()+": "+customer.getLevel()+": "+customer.getBirthYear()+"年"+
								customer.getBirthMonth()+"月"+customer.getBirthDay()+"日"+
								": "+customer.getCardNumber()+": "+customer.getPhoneNumber()+" "+
								customer.getSex());
//			Iterator<Employee> iterator2=customer.getEmployee().iterator();
//			while (iterator2.hasNext()) {
//				Employee employee = (Employee) iterator2.next();
//				System.out.println("       对应的员工工号为"+employee.getJobNumber()+",名字："+employee.getName());
//			}
		}
	}
	
	@Test
	public void testManyToMany(){
		Employee employee =employeeDao.get("BNAP1oupT86_-IYakw2zNg");
		System.out.println(employee.getName());
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("page", 1);
		map.put("pageSize", 15);
		RestEntity<List> restEntity=customerService.getCustomerListByParams(map, null);
		System.out.println(restEntity.getData());
		Iterator<Customer> iterator=restEntity.getData().iterator();
		while (iterator.hasNext()) {
			Customer customer = (Customer) iterator.next();
			System.out.println(customer.getName());
			employee.getCustomer().add(customer);
		}
		
//		c.getEmployee().add(employee);
		
		employeeDao.save(employee);
		
		System.out.println("插入成功");
	}
	
	@Test
	public void testQueryM2M(){
		Customer c=new Customer();
		c.setName("聂小姐");
		c.setBirthYear(1990);
		c.setBirthDay(30);
		c.setBirthMonth(7);
		c.setPhoneNumber("18814122301");
		System.out.println(c==null);
		customerDao.save(c);
		Employee employee=employeeDao.get("2RiL-d2vTFyVm5Ox_Pufqg");
		employee.getCustomer().add(c);
		employeeDao.save(employee);
	}
	
	@Test
	public void testRoleM2M(){
		RestEntity<Employee> restEntity=employeeService.getByJobNumber("GZ22529");
		Employee employee=restEntity.getData();
		
		
		QueryParam param=new QueryParam();
		param.addQuerySpec("id", QuerySpec.EQ, "FzcuCZX6TBW_uotHuusAEQ");
		Customer customer=customerDao.queryUniqueResultByParam(param);
		System.out.println("-----"+customer==null);
		System.out.println(customer.getCardNumber());
//		employee.getCustomer().add(e);
	}
	
}
