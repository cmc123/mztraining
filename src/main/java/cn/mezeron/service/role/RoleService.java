package cn.mezeron.service.role;

import java.util.List;
import java.util.Map;

import cn.mezeron.domain.basic.model.RestEntity;

public interface RoleService {

	/**
	 * 获取角色列表
	 * @return
	 */
	public RestEntity<List> getRoleList(Map<String,Object> params);
	
}
