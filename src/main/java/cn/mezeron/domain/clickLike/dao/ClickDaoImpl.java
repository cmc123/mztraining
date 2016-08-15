package cn.mezeron.domain.clickLike.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.clickLike.model.ClickLike;

@Repository("clickDao")
public class ClickDaoImpl extends BasicDaoImpl<ClickLike>implements ClickDao {


	public ClickDaoImpl() {
		// TODO Auto-generated constructor stub
		super(ClickLike.class);
	}
}
