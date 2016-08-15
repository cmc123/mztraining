package cn.mezeron.service.comment;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.xml.resolver.apps.resolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.circle.dao.CircleDao;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.comment.dao.CommentDao;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private CommentDao commentDao;
	@Resource
	private CircleDao circleDao;
	@Resource
	private EmployeeDao employeeDao;
	
	@POST
	@Path("comment/getCommentList")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<Set> getCommentList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String id=(String) params.get("id");
		logger.info("传送过来的id值为："+id);
		if(id==null||StringUtils.isEmpty(id)){
			return new RestEntity<Set>().fail("id无效");
		}
		Circle circle=circleDao.get(id);
		if(circle==null){
			return new RestEntity<Set>().fail("查无此id对应的circle");
		}
		Set<Comment> comments=circle.getComment();
		logger.info("返回id为:"+id+"说说的评论");
		return new RestEntity<Set>().ok(comments);
	}

	@GET
	@Path("comment/getById/{commentId}")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<Comment> getById(@PathParam("commentId")String commentId) {
		// TODO Auto-generated method stub
		logger.info("传送过来的commentId为:"+commentId);
		if(commentId==null||StringUtils.isEmpty(commentId)){
			return new RestEntity<Comment>().fail("id为空");
		}
		Comment comment=commentDao.get(commentId);
		if(comment==null){
			return new RestEntity<Comment>().fail("查无此id对应的评论");
		}
		return new RestEntity<Comment>().ok(comment);
	}
	
	@POST
	@Path("comment/createMyCommet")
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public RestEntity<Comment> createMyComment(Map<String,Object> params){
		String employeeId=(String) params.get("employeeId");
		String circleId=(String) params.get("circleId");
		String message=(String) params.get("message");
		if(employeeId==null||StringUtils.isEmpty(employeeId)||circleId==null||StringUtils.isEmpty(circleId)
				||message==null||StringUtils.isEmpty(message)){
			logger.info("传过来的参数有的为空");
			return new RestEntity<Comment>().fail("传过来的参数有的为空");
		}
		Circle circle=circleDao.get(circleId);
		Employee employee=employeeDao.get(employeeId);
		Comment comment=new Comment();
		comment.setCircle(circle);
		comment.setEmployee(employee);
		comment.setMessage(message);
		logger.info("增加circle的评论数");
		circle.setNumOfComment(1+circle.getNumOfComment());
		logger.info("这里只用commentDao保存，circle实例这里是持久类，应该不用手动保存，立个flag");
		commentDao.save(comment);
		logger.info("发表评论成功");
		return new RestEntity<Comment>().ok(null);
	}
}
