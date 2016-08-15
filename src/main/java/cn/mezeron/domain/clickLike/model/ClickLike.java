package cn.mezeron.domain.clickLike.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.employee.model.Employee;

@Entity
@Table(name = "jz_clickLike")
@DynamicUpdate(true)
public class ClickLike extends BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;

//	@JsonIgnore
	private Employee employee;
	@JsonIgnore
	private Circle circle;

	@ManyToOne(targetEntity=Employee.class)
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@ManyToOne(targetEntity=Circle.class)
	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	
	

}
