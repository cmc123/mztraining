package cn.mezeron.domain.employee.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.employee.model.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends BasicDaoImpl<Employee>implements EmployeeDao {

	public EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
		super(Employee.class);
	}
}
