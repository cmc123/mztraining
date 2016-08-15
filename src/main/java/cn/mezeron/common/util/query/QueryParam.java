package cn.mezeron.common.util.query;

/*
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

public class QueryParam {
	
	private int pageSize; //分页大小
	private int page; //页号
	private List<QuerySpec> querySpecs = new ArrayList<QuerySpec>(); //查询条件
	private List<Order> orderSpec = new ArrayList<Order>(); //排序条件
	private String search; //搜索关键字
	private String [] searchFieldList = {}; //搜索字段
	private MatchMode matchMode; //搜索匹配模式
	
	public QueryParam(){
		pageSize = 0;
		page = 0;
	}
	
	/**
	 * 添加查询条件
	 * @param spec 查询条件
	 */
	public void addQuerySpec(QuerySpec spec){
		querySpecs.add(spec);
	}
	/**
	 * 添加查询条件
	 * @param fieldName 查询字段
	 * @param operator 查询操作符
	 * @param value 查询值
	 */
	public void addQuerySpec(String fieldName, int operator,Object value){
		QuerySpec qs = new QuerySpec(fieldName, operator, value);
		querySpecs.add(qs);
	}
	
	/**
	 * 添加排序
	 * @param fieldName
	 * @param order asc desc
	 */
	public void addOrderSpec(String fieldName, String order){
		if (order.toLowerCase().equals("asc")){
			orderSpec.add(Order.asc(fieldName));
		}
		else if (order.toLowerCase().equals("desc")){
			orderSpec.add(Order.desc(fieldName));
		}
		else{
			orderSpec.add(Order.asc(fieldName));
		}
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<QuerySpec> getQuerySpecs() {
		return querySpecs;
	}
	public void setQuerySpecs(List<QuerySpec> querySpecs) {
		this.querySpecs = querySpecs;
	}
	public List<Order> getOrderSpec() {
		return orderSpec;
	}
	public void setOrderSpec(List<Order> orderSpec) {
		this.orderSpec = orderSpec;
	}

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public String[] getSearchFieldList() {
		return searchFieldList;
	}
	public void setSearchFieldList(String[] searchFieldList) {
		this.searchFieldList = searchFieldList;
	}
	
	public MatchMode getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}
	
	
}
