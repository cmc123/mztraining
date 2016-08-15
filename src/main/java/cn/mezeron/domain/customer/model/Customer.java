package cn.mezeron.domain.customer.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.employee.model.Employee;
import cn.mezeron.domain.store.model.Store;

/**
 * Customer 客户
 * @Author Neo
 * @Description
 */
@Entity
@Table(name = "jz_customer")
@DynamicUpdate(true)
public class Customer extends BasicModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 默认值
	 */
	public Customer() {
		this.status = "活动";
		this.level = 0;
	}
	
	private String name; // 客户名称
	private String phoneNumber; // 电话号码
	private String sex;// 性别
	
	@JsonIgnore
	private String comments; // 备注
	private String salesTarget; //销售目标
	private Integer level;// 会员级别
	private String cardNumber; // 会员卡号
	private Integer birthYear;// 出生年
	private Integer birthMonth;// 出生月
	private Integer birthDay;// 出生日
	private String status;// 会员状态
	
	@JsonIgnore
	private Store store;	//客户所在门店信息
	
	@JsonIgnore
	private Set<Employee> employee=new HashSet<Employee>();	//客户对应员工，一个客户最起码由一个顾问，一个美容师跟进
	
	
	
	@Column(name="name", length=50, nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="phone_number", length=30, nullable=false)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name="sex", length=5)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name="comments", length=255)
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(name="sales_target", length=30)
	public String getSalesTarget() {
		return salesTarget;
	}
	public void setSalesTarget(String salesTarget) {
		this.salesTarget = salesTarget;
	}
	
	@Column(name="level", length=5)
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name="card_number", length=30)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Column(name="birth_year", length=5)
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	
	@Column(name="birth_month", length=2)
	public Integer getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}
	
	@Column(name="birth_day", length=2)
	public Integer getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}
	
	@Column(name="status", length=20, nullable=false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@ManyToOne(targetEntity=Store.class)
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	@ManyToMany(targetEntity=Employee.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "customer", fetch = FetchType.LAZY)
	public Set<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}
	
	
}
