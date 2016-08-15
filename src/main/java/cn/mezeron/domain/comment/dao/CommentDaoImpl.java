package cn.mezeron.domain.comment.dao;

import org.springframework.stereotype.Repository;

import cn.mezeron.domain.basic.dao.BasicDaoImpl;
import cn.mezeron.domain.comment.model.Comment;

@Repository("commentDao")
public class CommentDaoImpl extends BasicDaoImpl<Comment>implements CommentDao {

	public CommentDaoImpl() {
		// TODO Auto-generated constructor stub
		super(Comment.class);
	}

}
