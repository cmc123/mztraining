package cn.mezeron.service.circle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.circle.model.Circle;

public interface CircleService {

	public RestEntity<List> getCircleList(Map<String,Object> params);
	
	/**
	 * 根据id获取客户信息
	 * @param customerId
	 * @return
	 */
	public RestEntity<Circle> getById(String circleId);
}
