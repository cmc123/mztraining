package cn.mezeron.domain.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import cn.mezeron.common.util.query.QueryParam;
import cn.mezeron.common.util.query.QuerySpec;

public class BasicDaoImpl<T> {
	
	//日志对象
    protected final Log logger = LogFactory.getLog(getClass());
	
    @SuppressWarnings("unchecked")
    private Class<T> persistentClass = ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    @Resource
    private SessionFactory sessionFactory;
    
    /**
     * 构造函数，确定实体对象类型
     * 在子类中调用该构造函数
     *
     * @param persistentClass 需要持久化的类
     */
    public BasicDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
    
    public BasicDaoImpl() {
    }
    
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }


    @SuppressWarnings("unchecked")
    public T get(String id) {
        Session sess = getCurrentSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        return entity;
    }

    /**
     * {@inheritDoc}}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getCurrentSession();
//        Object newObject = sess.merge(object);
        Object newObject=sess.save(object);
        sess.flush();
        //sess.clear();
        return (T) newObject;
    }

    /**
     * {@inheritDoc}}
     */
    public void remove(T object) {
        Session sess = getCurrentSession();
        sess.delete(object);
        sess.flush();
        sess.clear();
    }

    /**
     * {@inheritDoc}}
     */
    @SuppressWarnings("unchecked")
    public void remove(String id) {
        Session sess = getCurrentSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
        sess.flush();
        sess.clear();
    }


    /**
     * {@inheritDoc}}
     */
    @SuppressWarnings("unchecked")
    public T queryUniqueResultByParam(QueryParam param) {

    	Criteria criteria = getCurrentSession().createCriteria(persistentClass);
        //遍历查询条件
        for (QuerySpec querySpec : param.getQuerySpecs()) {
            switch (querySpec.getOperator()) {
                case QuerySpec.EQ:
                    criteria.add(Restrictions.eq(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GE:
                    criteria.add(Restrictions.ge(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GT:
                    criteria.add(Restrictions.gt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LE:
                    criteria.add(Restrictions.le(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LT:
                    criteria.add(Restrictions.lt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LIKE:
                    criteria.add(Restrictions.like(querySpec.getFieldName(), (String) querySpec.getValue(), MatchMode.ANYWHERE));
                    break;
                case QuerySpec.IN:
                    criteria.add(Restrictions.in(querySpec.getFieldName(), (Object[]) querySpec.getValue()));
                    break;
                case QuerySpec.ISNULL:
                    criteria.add(Restrictions.isNull(querySpec.getFieldName()));
                    break;
                case QuerySpec.ISNOTNULL:
                    criteria.add(Restrictions.isNotNull(querySpec.getFieldName()));
                    break;
            }
        }
        return (T) criteria.uniqueResult();
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> queryByParam(QueryParam param) {

        Criteria criteria = getCurrentSession().createCriteria(persistentClass);
        //如果分页
        if (param.getPageSize() > 0) {
            logger.debug("setFirstResult" + ":" + (param.getPage() - 1) * param.getPageSize());
            logger.debug("setMaxResults" + ":" + param.getPageSize());
            criteria.setFirstResult((param.getPage() - 1) * param.getPageSize());
            criteria.setMaxResults(param.getPageSize());
        }
        //遍历查询条件
        for (QuerySpec querySpec : param.getQuerySpecs()) {
            logger.debug(querySpec.getFieldName() + ":" + querySpec.getValue());
            if(querySpec.getAnotherClass()!=null){
            	logger.info("跳过本次循环！进行外连接查询");
            	continue;
            }
            switch (querySpec.getOperator()) {
                case QuerySpec.EQ:
                    criteria.add(Restrictions.eq(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GE:
                    criteria.add(Restrictions.ge(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GT:
                    criteria.add(Restrictions.gt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LE:
                    criteria.add(Restrictions.le(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LT:
                    criteria.add(Restrictions.lt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LIKE:
                    criteria.add(Restrictions.like(querySpec.getFieldName(), (String) querySpec.getValue(), MatchMode.ANYWHERE));
                    break;
                case QuerySpec.IN:
                    criteria.add(Restrictions.in(querySpec.getFieldName(), (Object[]) querySpec.getValue()));
                    break;
                case QuerySpec.OR:
                    criteria.add(Restrictions.or(Restrictions.eq(querySpec.getFieldName(), querySpec.getValue())));
                    break;
                case QuerySpec.NE:
                    criteria.add(Restrictions.ne(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.ISNULL:
                    criteria.add(Restrictions.isNull(querySpec.getFieldName()));
                    break;
                case QuerySpec.ISNOTNULL:
                    criteria.add(Restrictions.isNotNull(querySpec.getFieldName()));
                    break;
            }
        }
        //遍历排序条件
        for (Order orderSpec : param.getOrderSpec()) {
            criteria.addOrder(orderSpec);
        }
        //搜索
        if (param.getSearch() != null && !param.getSearch().isEmpty()) {
            String keyword = param.getSearch();
            Criterion searchCriteria = null;
            MatchMode matchMode = MatchMode.START;
            if (param.getMatchMode() != null)
                matchMode = param.getMatchMode();
            else if (keyword.indexOf("*") == 0) {
                matchMode = MatchMode.ANYWHERE;
                keyword = keyword.substring(1, keyword.length());
            }
            //遍历搜索字段，构造OR条件
            for (String c : param.getSearchFieldList()) {
                if (searchCriteria == null)
                    searchCriteria = Restrictions.like(c, keyword, matchMode);
                else
                    searchCriteria = Restrictions.or(searchCriteria,
                            Restrictions.like(c, keyword, matchMode));
            }
            criteria.add(searchCriteria);
        }
        for (QuerySpec querySpec : param.getQuerySpecs()){
        	if(querySpec.getAnotherClass()!=null){
        		String classname=querySpec.getAnotherClass();
        		System.out.println("basicdaoImpl类方法执行:"+classname);
        		switch (querySpec.getOperator()) {
                case QuerySpec.EQ:
                	criteria.createCriteria(classname).add(Restrictions.eq(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GE:
                	criteria.createCriteria(classname).add(Restrictions.ge(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.GT:
                	criteria.createCriteria(classname).add(Restrictions.gt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LE:
                	criteria.createCriteria(classname).add(Restrictions.le(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LT:
                	criteria.createCriteria(classname).add(Restrictions.lt(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.LIKE:
                	criteria.createCriteria(classname).add(Restrictions.like(querySpec.getFieldName(), (String) querySpec.getValue(), MatchMode.ANYWHERE));
                    break;
                case QuerySpec.IN:
                	criteria.createCriteria(classname).add(Restrictions.in(querySpec.getFieldName(), (Object[]) querySpec.getValue()));
                    break;
                case QuerySpec.OR:
                	criteria.createCriteria(classname).add(Restrictions.or(Restrictions.eq(querySpec.getFieldName(), querySpec.getValue())));
                    break;
                case QuerySpec.NE:
                	criteria.createCriteria(classname).add(Restrictions.ne(querySpec.getFieldName(), querySpec.getValue()));
                    break;
                case QuerySpec.ISNULL:
                	criteria.createCriteria(classname).add(Restrictions.isNull(querySpec.getFieldName()));
                    break;
                case QuerySpec.ISNOTNULL:
                	criteria.createCriteria(classname).add(Restrictions.isNotNull(querySpec.getFieldName()));
                    break;
        		}
        	}
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> queryUniqueResultBySql(String sql, Map<String, Object> params) {
        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        //设置参数
        for (String key : params.keySet())
            query.setParameter(key, params.get(key));
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>) query.uniqueResult();
    }

    @SuppressWarnings("rawtypes")
    public List queryBySql(String sql, Map<String, Object> params) {
        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        //设置参数
        if (params != null) {
            for (String key : params.keySet())
                query.setParameter(key, params.get(key));
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    /**
     * 执行原生SQL查询语句
     *
     * @param sql    SQL语句
     * @param params 参数
     * @return 结果以Map方式返回
     */
    @SuppressWarnings("rawtypes")
    public List queryBySql(String sql, Map<String, Object> params, int page, int pageSize) {
        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        //设置参数
        if (params != null) {
            for (String key : params.keySet())
                query.setParameter(key, params.get(key));
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (pageSize > 0) {
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return query.list();
    }
}
