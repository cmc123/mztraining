package cn.mezeron.domain.circle.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.mezeron.domain.basic.model.BasicModel;
import cn.mezeron.domain.clickLike.model.ClickLike;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.employee.model.Employee;
import cn.mezeron.domain.store.model.Store;

@Entity
@Table(name = "jz_circle")
@DynamicUpdate(true)
public class Circle extends BasicModel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public Circle() {
		// TODO Auto-generated constructor stub
		this.message="空";
		this.numOfLike=0;
		this.numOfComment=0;
	}
	
	private String message;
	
	private Employee employee;
	
	private Set<Comment> comment=new HashSet<Comment>();
	
	private Set<ClickLike> clickLikes=new HashSet<ClickLike>();
	
	private Integer numOfComment=comment.size();
	
	private Integer numOfLike=clickLikes.size();
	
	@JsonIgnore
	private Store store;
	
	@Column(name="num_of_comment",length=4,nullable=true)
	public Integer getNumOfComment() {
		return numOfComment;
	}
	public void setNumOfComment(Integer numOfComment) {
		this.numOfComment = numOfComment;
	}
	
	@Column(name="num_of_like",length=4,nullable=true)
	public Integer getNumOfLike() {
		return numOfLike;
	}
	public void setNumOfLike(Integer numOfLike) {
		this.numOfLike = numOfLike;
	}
	@Column(name="message", length=100, nullable=false)
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
	
	@OneToMany(targetEntity=Comment.class,mappedBy="circle")
	public Set<Comment> getComment() {
		return comment;
	}
	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
	
	@ManyToOne(targetEntity=Store.class)
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	@OneToMany(targetEntity=ClickLike.class,mappedBy="circle")
	public Set<ClickLike> getClickLikes() {
		return clickLikes;
	}
	public void setClickLikes(Set<ClickLike> clickLikes) {
		this.clickLikes = clickLikes;
	}
	
}
