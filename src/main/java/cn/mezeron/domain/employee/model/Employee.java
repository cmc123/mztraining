package cn.mezeron.domain.employee.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.clickLike.model.ClickLike;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.customer.model.Customer;
import cn.mezeron.domain.role.model.Role;
import cn.mezeron.domain.store.model.Store;

@Entity
@Table(name = "jz_employee")
@DynamicUpdate(true)
public class Employee extends BasicModel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public Employee() {
		this.name="测试人员";
		this.phoneNumber="18814122529";
		this.password="123456";
		this.jobNumber="GZ"+phoneNumber.substring(6);
	}
	
	private String name;		//姓名
	private String headPicture;	//头像
	private String jobNumber;	//工号
	private String phoneNumber;	//电话
	
	@JsonIgnore
	private String password;	//密码
	
	@JsonIgnore
	private Set<ClickLike> clickLike=new HashSet<ClickLike>();
	
	@JsonIgnore
	private Set<Customer> customer=new HashSet<Customer>();	//员工负责根据客户
	
	@JsonIgnore
	private Store store;		//员工所在门店信息
	
	@JsonIgnore
	private Set<Role> role=new HashSet<Role>();		//一个员工可以分饰多个角色
	
	@JsonIgnore
	private Set<Circle> circles=new HashSet<Circle>();
	
	@JsonIgnore
	private Set<Comment> commment=new HashSet<Comment>();

	@Column(name="name", length=50, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="head_picture", length=30, nullable=true)
	public String getHeadPicture() {
		return headPicture;
	}
	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
	
	@Column(name="job_number", length=15, nullable=false)
	public String getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Column(name="phone_number", length=30, nullable=false)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name="password", length=30, nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@ManyToMany  
	@JoinTable(name = "emploee_role", 
		joinColumns=@JoinColumn(name="employee_id",referencedColumnName="id",unique=false),
		inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id",unique=false))
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	@ManyToOne(targetEntity=Store.class)
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	@ManyToMany
	@JoinTable(name = "emploee_customer", 
	joinColumns=@JoinColumn(name="employee_id",referencedColumnName="id",unique=false),
	inverseJoinColumns=@JoinColumn(name="customer_id",referencedColumnName="id",unique=false))
	public Set<Customer> getCustomer() {
		return customer;
	}
	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}
	
	@OneToMany(targetEntity=Circle.class,mappedBy="employee")
	public Set<Circle> getCircles() {
		return circles;
	}
	public void setCircles(Set<Circle> circles) {
		this.circles = circles;
	}
	
	@OneToMany(targetEntity=Comment.class,mappedBy="employee")
	public Set<Comment> getCommment() {
		return commment;
	}
	public void setCommment(Set<Comment> commment) {
		this.commment = commment;
	}
	
	@OneToMany(targetEntity=ClickLike.class,mappedBy="employee")
	public Set<ClickLike> getClickLike() {
		return clickLike;
	}
	public void setClickLike(Set<ClickLike> clickLike) {
		this.clickLike = clickLike;
	}
	
}
