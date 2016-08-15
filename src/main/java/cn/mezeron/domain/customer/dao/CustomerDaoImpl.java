package cn.mezeron.domain.customer.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.customer.model.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends BasicDaoImpl<Customer> implements CustomerDao {
	
	public CustomerDaoImpl() {
		super(Customer.class);
	}
	
}