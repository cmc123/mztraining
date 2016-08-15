package cn.mezeron.circle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.circle.dao.CircleDao;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.clickLike.dao.ClickDao;
import cn.mezeron.domain.clickLike.model.ClickLike;
import cn.mezeron.domain.comment.dao.CommentDao;
import cn.mezeron.domain.comment.model.Comment;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;
import cn.mezeron.service.circle.CircleService;

@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-hibernate.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@Transactional
public class CircleTest {

	@Resource
	private CircleDao circleDao;
	@Resource
	private EmployeeDao employeeDao;
	@Resource
	private CommentDao commentDao;
	@Resource
	private CircleService circleService;
	@Resource
	private ClickDao clickDao;
	@Test
	public void testInsertCircle(){
		Employee employee=employeeDao.get("2RiL-d2vTFyVm5Ox_Pufqg");
		Circle circle=new Circle();
		circle.setMessage("第三条朋友圈");
		circle.setEmployee(employee);
		circle.setStore(employee.getStore());
		circleDao.save(circle);
		Comment comment=new Comment();
		comment.setMessage("今天是周日");
		comment.setCircle(circle);
		comment.setEmployee(employee);
		circle.setNumOfComment(1+circle.getNumOfComment());
		commentDao.save(comment);
	}
	@Test
	public void testUpdateCircle(){
		Circle circle=circleDao.get("JPh10Ht8SjCOnPs4OjKDKw");
		circle.setMessage("测试朋友圈");
		circleDao.save(circle);
	}
	@Test
	public void insertComment(){
		Employee employee=employeeDao.get("2RiL-d2vTFyVm5Ox_Pufqg");
		Circle circle=circleDao.get("JPh10Ht8SjCOnPs4OjKDKw");
		Comment comment=new Comment();
		comment.setMessage("第二条评论");
		comment.setCircle(circle);
		comment.setEmployee(employee);
		commentDao.save(comment);
		}
	@Test
	public void loadCircle(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("page", 0);
		params.put("pageSize", 3);
		params.put("id", "BNAP1oupT86_-IYakw2zNg");
		RestEntity<List> restEntity=circleService.getCircleList(params);
		List<Circle> circles=restEntity.getData();
		Iterator<Circle> iterator=circles.iterator();
		while (iterator.hasNext()) {
			Circle circle = (Circle) iterator.next();
			System.out.println(circle.getEmployee().getName()+":"+circle.getMessage());
		}
	}
		
	@Test
	public void clickLikes(){
		Employee employee=employeeDao.get("2RiL-d2vTFyVm5Ox_Pufqg");
		Circle circle=circleDao.get("JPh10Ht8SjCOnPs4OjKDKw");
		ClickLike clickLike=new ClickLike();
		clickLike.setCircle(circle);
		clickLike.setEmployee(employee);
		clickDao.save(clickLike);
	}
	}

