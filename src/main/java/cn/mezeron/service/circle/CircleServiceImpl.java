package cn.mezeron.service.circle;

import javax.ws.rs.core.MediaType;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.stereotype.Service;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;
import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.circle.dao.CircleDao;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.clickLike.dao.ClickDao;
import cn.mezeron.domain.clickLike.model.ClickLike;
import cn.mezeron.domain.comment.dao.CommentDao;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;

@Service("circleService")
public class CircleServiceImpl implements CircleService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private CircleDao circleDao;
	
	@Resource
	private EmployeeDao employDao;
	
	@Resource
	private ClickDao clickDao;
	
	@Resource
	private CommentDao commentDao;
	
	@POST
	@Path("circle/getCircleList")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<List> getCircleList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		Integer page = (Integer)params.get("page");
		Integer pageSize = (Integer)params.get("pageSize");
		String id=(String) params.get("id");
		if (page == null || pageSize == null) {
			logger.info("page或者pagesize为空");
			return new RestEntity<List>().fail("请提供分页参数！");
		}
		if(id==null||StringUtils.isEmpty(id)){
			return new RestEntity<List>().fail("id无效");
		}
		Employee employee=employDao.get(id);
		if(employee==null){
			return new RestEntity<List>().fail("用户不存在");
		}
		//开始赋值
		QueryParam param=new QueryParam();
		param.setPage(page);
		param.setPageSize(pageSize);
		//重写了basicdaoImpl的queryByParam，为queryspec新建一个含4个参数的构造函数，使得查询出来的顾客能与对应员工绑定
		String storeId=employee.getStore().getId();
		param.getQuerySpecs().add(new QuerySpec("store","id", QuerySpec.EQ, storeId));
		
		//按创建时间进行员工圈的排序
		String orderSpecFieldName="createdTime";
		String orderSpec="desc";
		param.addOrderSpec(orderSpecFieldName, orderSpec);
		List<Circle> list=circleDao.queryByParam(param);
		logger.info("获得list的长度"+list.size());
		return new RestEntity<List>().ok(list);
		
	}

	@GET
	@Path("circle/getById/{circleId}")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<Circle> getById(@PathParam("circleId")String circleId) {
		// TODO Auto-generated method stub
		logger.info("传送过来的circleId值为："+circleId);
		if(circleId==null||StringUtils.isEmpty(circleId)){
			return new RestEntity<Circle>().fail("id为空");
		}
		Circle circle=circleDao.get(circleId);
		if(circle==null){
			return new RestEntity<Circle>().fail("查无此id对应的说说");
		}
		return new RestEntity<Circle>().ok(circle);
	}

	@POST
	@Path("circle/createMyCircle")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<Circle> createMyCircle(Map<String, Object> params){
		String message=(String) params.get("message");
		String employeeId=(String) params.get("id");
		Employee employee=employDao.get(employeeId);
		if(message==null||StringUtils.isEmpty(message)||employeeId==null||StringUtils.isEmpty(employeeId)){
			return new RestEntity<Circle>().fail("传过来的参数为空");
		}
		Circle circle=new Circle();
		circle.setEmployee(employee);
		circle.setMessage(message);
		circle.setStore(employee.getStore());
		circleDao.save(circle);
		return new RestEntity<Circle>().ok(null);
	}
	
	@POST
	@Path("circle/addCircleLike")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<ClickLike> addCircleLike(Map<String, Object> params){
		String circleId=(String) params.get("circleId");
		String employeeId=(String) params.get("id");
		
		if(circleId==null||StringUtils.isEmpty(circleId)||employeeId==null||StringUtils.isEmpty(employeeId)){
			return new RestEntity<ClickLike>().fail("传过来的参数为空");
		}
		Employee employee=employDao.get(employeeId);
		Circle circle=circleDao.get(circleId);
		QueryParam param=new QueryParam();
		param.addQuerySpec(new QuerySpec("employee", "id", QuerySpec.EQ, employeeId));
		param.addQuerySpec(new QuerySpec("circle", "id", QuerySpec.EQ, circleId));
		List<ClickLike> list=clickDao.queryByParam(param);
		if(list.size()>0){
			return new RestEntity<ClickLike>().fail("你已经点赞过了");
		}
		ClickLike clickLike=new ClickLike();
		clickLike.setEmployee(employee);
		clickLike.setCircle(circle);
		logger.info(employee.getName()+"为员工圈："+circle.getMessage()+"点赞");
		clickDao.save(clickLike);
		return new RestEntity<ClickLike>().ok(clickLike);
	}
	@POST
	@Path("circle/addCircleComment")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<Comment> addCircleComment(Map<String, Object> params){
		String id=(String) params.get("id");
		String circleId=(String) params.get("circleId");
		String myComment=(String) params.get("myComment");
		String replyEmployeeId=(String) params.get("replyEmployeeId");
		if(id==null||circleId==null||myComment==null||myComment.equals("")){
			return new RestEntity<Comment>().fail("参数不正确");
		}
		Employee replyEmployee=null;
		if(replyEmployeeId!=null){
			replyEmployee=employDao.get(replyEmployeeId);
		}
		Circle circle=circleDao.get(circleId);
		Employee employee=employDao.get(id);
		if(circle==null||employee==null){
			return new RestEntity<Comment>().fail("找不到要求的参数");
		}
		Comment comment=new Comment();
		comment.setCircle(circle);
		comment.setEmployee(employee);
		comment.setMessage(myComment);
		if(replyEmployee!=null){
			comment.setReplyEmployee(replyEmployee);
		}
		commentDao.save(comment);
		return new RestEntity<Comment>().ok(comment);
	}
}
