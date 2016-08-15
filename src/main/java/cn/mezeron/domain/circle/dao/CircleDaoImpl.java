package cn.mezeron.domain.circle.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.circle.model.Circle;

@Repository("circleDao")
public class CircleDaoImpl extends BasicDaoImpl<Circle>implements CircleDao {

	public CircleDaoImpl() {
		// TODO Auto-generated constructor stub
		super(Circle.class);
	}
}
