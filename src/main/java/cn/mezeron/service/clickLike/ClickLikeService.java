package cn.mezeron.service.clickLike;

import java.util.Map;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.clickLike.model.ClickLike;

public interface ClickLikeService {
	
	public RestEntity<ClickLike> clickCircle(Map<String, Object> params);
}
