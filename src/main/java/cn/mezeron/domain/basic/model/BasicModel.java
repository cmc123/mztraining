package cn.mezeron.domain.basic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@MappedSuperclass
public abstract class BasicModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id; //主键Id
	
	@JsonIgnore
	private String createdBy; //创建人Id
	
	@JsonIgnore
	private String updatedBy; //更新人Id
	
	private Date createdTime; //创建时间
	
	@JsonIgnore
	private Date updatedTime;// 更新时间
	
	@JsonIgnore
	private Integer version; //版本号
	
	@Id
	@Column(length=40)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.mezeron.common.util.uuid.Base64UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 40)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(nullable = false, length = 40)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length=20)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length=20)
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	@Version
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
