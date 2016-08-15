package cn.mezeron.domain.role.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.employee.model.Employee;

@Entity
@Table(name = "jz_role")
@DynamicUpdate(true)
public class Role extends BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public Role() {
		this.rolePlay="顾问";
	}
	private String rolePlay;
	
	@JsonIgnore
	private Set<Employee> employee=new HashSet<Employee>();		//一个员工有多个角色

	@Column(name="role", length=5, nullable=false)
	public String getRolePlay() {
		return rolePlay;
	}
	public void setRolePlay(String rolePlay) {
		this.rolePlay = rolePlay;
	}
	
	
	@ManyToMany(targetEntity=Employee.class,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
			mappedBy = "role", fetch = FetchType.LAZY)
	public Set<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}
}
