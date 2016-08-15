package cn.mezeron.service.customer;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.customer.model.Customer;

public interface CustomerService {
	
	/**
	 * 获取客户列表
	 * @return
	 */
	public RestEntity<List> getCustomerList(Map<String,Object> params);
	
	/**
	 * 根据id获取客户信息
	 * @param customerId
	 * @return
	 */
	public RestEntity<Customer> getById(String customerId);
	
	public RestEntity<List> getCustomerListByParams(Map<String,Object> params,HttpServletRequest request) ;
}
