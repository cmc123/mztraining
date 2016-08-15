package cn.mezeron.service.employee;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.employee.model.Employee;

public interface EmployeeService {
	
	/**
	 * 根据id查询员工信息
	 * @param customerId
	 * @return
	 */
	public RestEntity<Employee> getById(String id);
	
	/**
	 * 根据工号查询员工信息
	 * @param jobNumber
	 * @return
	 */
	
	public RestEntity<Employee> login(Map<String,Object> params,HttpServletRequest request);
	
	public RestEntity<Employee> getByJobNumber(String jobNumber);
	
	public RestEntity<Employee> changePassword(Map<String,Object> params,HttpServletRequest request);
}
