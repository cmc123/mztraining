package cn.mezeron.service.comment;

import java.util.Map;
import java.util.Set;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.comment.model.Comment;

public interface CommentService {

	public RestEntity<Set> getCommentList(Map<String,Object> params);
	
	/**
	 * 根据id获取评论信息
	 * @param customerId
	 * @return
	 */
	public RestEntity<Comment> getById(String commentId);
}
