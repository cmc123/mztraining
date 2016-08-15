package cn.mezeron.domain.comment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.employee.model.Employee;

@Entity
@Table(name = "jz_comment")
@DynamicUpdate(true)
public class Comment extends BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	private Employee employee;
	
	private Employee replyEmployee;
	
	@JsonIgnore
	private Circle circle;
	
	@Column(name="message",nullable=false,length=100)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
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
	@ManyToOne(targetEntity=Employee.class)
	public Employee getReplyEmployee() {
		return replyEmployee;
	}
	public void setReplyEmployee(Employee replyEmployee) {
		this.replyEmployee = replyEmployee;
	}
	
}
