package cn.mezeron.service.clickLike;

import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.xml.resolver.apps.resolver;
import org.springframework.stereotype.Service;

import cn.mezeron.domain.basic.model.RestEntity;
import cn.mezeron.domain.circle.dao.CircleDao;
import cn.mezeron.domain.circle.model.Circle;
import cn.mezeron.domain.clickLike.dao.ClickDao;
import cn.mezeron.domain.clickLike.model.ClickLike;
import cn.mezeron.domain.employee.dao.EmployeeDao;
import cn.mezeron.domain.employee.model.Employee;

@Service("clickLikeService")
public class ClickLikeServiceImpl implements ClickLikeService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private EmployeeDao employeeDao;

	@Resource
	private CircleDao circleDao;

	@Resource
	private ClickDao clickDao;
	
	@POST
	@Path("clicklike/clickCircle")
	@Produces({MediaType.APPLICATION_JSON})
	public RestEntity<ClickLike> clickCircle(Map<String, Object> params) {
		// TODO Auto-generated method stub
		String circleId=(String) params.get("circleId");
		String employeeId=(String) params.get("employeeId");
		if(circleId==null||StringUtils.isEmpty(circleId)||employeeId==null||StringUtils.isEmpty(employeeId)){
			logger.info("传过来的值有的为空");
			return new RestEntity<ClickLike>().fail("传过来的值有的为空");
		}
		Circle circle=circleDao.get(circleId);
		Employee employee=employeeDao.get(employeeId);
		ClickLike clickLike=new ClickLike();
		clickLike.setCircle(circle);
		clickLike.setEmployee(employee);
		circle.setNumOfLike(1+circle.getNumOfLike());
		logger.info("这里只用clickDao保存，circle实例这里是持久类，应该不用手动保存，立个flag");
		clickDao.save(clickLike);
		return new RestEntity<ClickLike>().ok(null);
	}

}
