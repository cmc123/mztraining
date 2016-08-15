package cn.mezeron.domain.store.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.customer.model.Customer;
import cn.mezeron.domain.employee.model.Employee;

@Entity
@Table(name = "jz_store")
@DynamicUpdate(true)
public class Store extends BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String address;
	private String name;
	
	public Store() {
		// TODO Auto-generated constructor stub
		this.address="广州市番禺区";
		this.name="番禺店";
	}
	@JsonIgnore
	private Set<Customer> customers=new HashSet<Customer>();
	@JsonIgnore
	private Set<Employee> employees=new HashSet<Employee>();

	@JsonIgnore
	private Set<Circle> circles=new HashSet<Circle>();
	
	@Column(name="address", length=20, nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="name", length=20, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(targetEntity=Customer.class,mappedBy="store")
	public Set<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
	
	@OneToMany(targetEntity=Employee.class,mappedBy="store")
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
	@OneToMany(targetEntity=Circle.class,mappedBy="store")
	public Set<Circle> getCircles() {
		return circles;
	}
	public void setCircles(Set<Circle> circles) {
		this.circles = circles;
	}

}
