package cn.mezeron.common.util.query;

public class QuerySpec {
	
	/*
	Restrictions.eq	等於
	Restrictions.allEq	使用Map，使用key/value進行多個等於的比對
	Restrictions.gt	大於 >
	Restrictions.ge	大於等於 >=
	Restrictions.lt	小於 <
	Restrictions.le	小於等於 <=
	Restrictions.between	對應SQL的BETWEEN子句
	Restrictions.like	對應SQL的LIKE子句
	Restrictions.in	對應SQL的in子句
	Restrictions.and	and關係
	Restrictions.or	or關係
	Restrictions.sqlRestriction	SQL限定查詢
	Restrictions.ne 不等于
	*/
	
	public static final int EQ = 1; 	 //Restrictions.eq	等於
	public static final int ALLEQ = 2;   //Restrictions.allEq	使用Map，使用key/value進行多個等於的比對
	public static final int GT = 3; 	 //Restrictions.gt	大於 >
	public static final int GE = 4; 	 //Restrictions.ge	大於等於 >=
	public static final int LT = 5; 	 //Restrictions.lt	小於 <
	public static final int LE = 6; 	 //Restrictions.le	小於等於 <=
	public static final int BETWEEN = 7; //Restrictions.between	對應SQL的BETWEEN子句
	public static final int LIKE = 8;  	 //Restrictions.like	對應SQL的LIKE子句
	public static final int IN = 9;  	 //Restrictions.in	對應SQL的in子句
	public static final int AND = 10; 	 //Restrictions.and	and關係
	public static final int OR = 11; 	 //Restrictions.or	or關係
	public static final int SQL = 12; 	 //Restrictions.sqlRestriction	SQL限定查詢
	public static final int NE = 13; 	 //Restrictions.ne 不等于
	public static final int ISNULL = 14; //Restrictions.isnull 为null
	public static final int ISNOTNULL = 15; //Restrictions.isnull 不为null
	
	private String fieldName; //字段名
	private int operator; //操作符
	private Object value; //值

	private String anotherClass;
	public QuerySpec(String fieldName, int operator,Object value){
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
	}
	public QuerySpec(String anotherClass,String fieldName, int operator,Object value){
		this.anotherClass=anotherClass;
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
	}

	public String getAnotherClass() {
		return anotherClass;
	}

	public void setAnotherClass(String anotherClass) {
		this.anotherClass = anotherClass;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
	
}
