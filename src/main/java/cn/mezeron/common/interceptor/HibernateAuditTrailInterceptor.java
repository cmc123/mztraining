package cn.mezeron.common.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.security.core.userdetails.User;

import cn.mezeron.domain.basic.model.BasicModel;


/**
 * Hibernate 拦截器，用于自动审批创建人、创建时间、修改人和修改时间
 * @author Neo
 */
@SuppressWarnings("serial")
public class HibernateAuditTrailInterceptor extends EmptyInterceptor{
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 清空缓存 - 同步数据库操作
	 * 更新数据时调用，但数据还没有更新到数据库
	 */
	public boolean onFlushDirty(
			Object entity, 
			Serializable id, 
			Object[] currentState, 
			Object[] previousState, 
			String[] propertyNames, 
			Type[] types) {
		
		boolean hasModified = false;
		
		try{
			if ( entity instanceof BasicModel ) {
				//获取用户Id,如果没有用户Id, 使用默认用户Id
				User user = null;
				String userId = "0-1";
				
	            for ( int i=0; i < propertyNames.length; i++ ) {
	                if ( "updatedTime".equals( propertyNames[i] ) ) {
	                    currentState[i] = new Date();
	                    hasModified = true;
	                }
	                
	                if ( "updatedBy".equals( propertyNames[i] ) ) {
	                    currentState[i] = userId;
	                    hasModified = true;
	                }
	            }
	        }
		}catch(Exception e){
			log.error(e);
		}
		return hasModified;
	}

	/**
	 * 保存新增记录操作
	 * 保存数据的时候调用，数据还没有保存到数据库
	 */
	public boolean onSave(
			Object entity, 
			Serializable id, 
			Object[] state, 
			String[] propertyNames, 
			Type[] types) {

		boolean hasModified = false;
		
		try{
			if ( entity instanceof BasicModel ) {
				//获取用户Id,如果没有用户Id, 使用默认用户Id
				User user = null;
				String userId  = "0-1";
				
	            for ( int i=0; i < propertyNames.length; i++ ) {
	                if ( "updatedTime".equals( propertyNames[i] ) ) {
	                	state[i] = new Date();
	                	hasModified = true;
	                }
	                
	                if ( "updatedBy".equals( propertyNames[i] ) ) {
		                state[i] = userId;
		                hasModified = true;
	                }
	                
	                if ( "createdTime".equals( propertyNames[i] ) ) {
	                	state[i] = new Date();
	                	hasModified = true;
	                }
	                
	                if ( "createdBy".equals( propertyNames[i] ) ) {
	                	state[i] = userId;
	                	hasModified = true;
	                }
	            }
	        }
		}catch(Exception e){
			log.error(e);
		}
		return hasModified;
	}
	
}