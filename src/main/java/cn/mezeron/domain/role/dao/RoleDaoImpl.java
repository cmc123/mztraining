package cn.mezeron.domain.role.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.role.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BasicDaoImpl<Role>implements RoleDao {

	public RoleDaoImpl() {
		// TODO Auto-generated constructor stub
		super(Role.class);
	}
}
