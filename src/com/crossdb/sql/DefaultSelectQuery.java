package com.crossdb.sql;


/**
 * Implementación base de {@link SelectQuery}, cualquier implementación especifica
 * para una BD concreta debiera heredar de esta clase. 
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 * Date: Jun 27, 2002
 * Time: 6:34:51 PM
 * 
 */
public abstract class DefaultSelectQuery implements SelectQuery {

	protected StringBuilder columns; // = new ArrayList(); // SELECT columns
	protected String table;// = new ArrayList(); // FROM tables
	protected SelectQuery tableFromSelect;
	protected String aliasForTableFromSelect;
	protected String orderByExpresion; // ORDER BY order_by
	protected String groupByExpresion; // GROUP BY order_by
	protected String havingExpresion;
    //protected String limit;
	protected WhereClause wclause;
    protected SelectQuery union;
    protected boolean isUnionAll;
    protected boolean isDistinct;
    protected Join join;
    protected int rowlimit;
    protected int rowoffset;
    protected Formatter formatter;
    

    public DefaultSelectQuery(Formatter formatter){
        //query1 = "";
        table = null;
        tableFromSelect = null;
        aliasForTableFromSelect  = null;
        columns = new StringBuilder();
        //where_clauses = new ArrayList();
        orderByExpresion = null;
        groupByExpresion = null;
        join = null;
        wclause = null;
        rowlimit = -1;
        rowoffset = -1;
        this.formatter = formatter;
    }

    public void setDistinct(boolean distinct) {
        this.isDistinct = distinct;
    }
    
    public boolean isDistinct() {
        return isDistinct;
    }
    
    public void addColumn(String column){
    	if (columns.length() != 0) columns.append(", ");
		columns.append(column);
	}

    public void addColumn(String table, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(table).append(".").append(column);
    }
    
    public void addColumn(String table, String column, String alias) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(table).append(".").append(column);
        columns.append(" AS ").append(alias);
    }
    
    
    public void addFunctionColumn(String function, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(function).append("( ").append(column).append(" )");
    }
    
    public void addFunctionColumn(String functionId, String column, String alias) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(functionId).append("( ").append(column).append(" )");
        columns.append(" AS ").append(alias);
    }

    public void sumColumn(String column) {
        addFunctionColumn("SUM", column);

    }

    public void sumColumn(String table, String column) {
        addFunctionColumn("SUM", table, column);
    }

    public void countColumn(String column) {
        addFunctionColumn("COUNT", column);
    }

    public void countColumn(String table, String column) {
        addFunctionColumn("COUNT", table, column);

    }

    public void averageColumn(String column) {
        addFunctionColumn("AVG", column);

    }

    public void averageColumn(String table, String column) {
        addFunctionColumn("AVG", table, column);

    }

    public void minColumn(String column) {
        addFunctionColumn("MIN", column);
    }

    public void minColumn(String table, String column) {
        addFunctionColumn("MIN", table, column);

    }

    public void maxColumn(String column) {
        addFunctionColumn("MAX", column);

    }

    public void maxColumn(String table, String column) {
        addFunctionColumn("MAX", table, column);

    }
    
	public void addTable(String table){
		this.table = table;
		this.tableFromSelect = null;
	}

	public void addTable(SelectQuery selectSource, String alias){
		this.tableFromSelect = selectSource;
		this.aliasForTableFromSelect = alias;
		this.table = null;
	}
	
	public Join addJoin(){
		if (join == null) join = new Join();
		return join;
	}
	
	public WhereClause addWhere(){
		if (wclause == null) wclause = new WhereClause(formatter);
		return wclause;
	}

	public void addGroupBy(String group_by){

		this.groupByExpresion = group_by;
	}
	
	public void addHaving(String havingExpresion){
		this.havingExpresion = havingExpresion;
	}
	
	public void union(SelectQuery sq) {
        this.union = sq;
    }
	
	public void unionAll(SelectQuery sq) {
        this.isUnionAll = true;
		this.union = sq;
    }
	
    public void addOrderBy(String order_by){
		this.orderByExpresion = order_by;
	}

	public void setLimit(int count){
		this.rowlimit = count;
//		if(limit == null){
//			limit = new int[1];
//		}
//		limit[0] = count;
	}

	public void setLimit(int offset, int count){
		this.rowlimit = count;
		this.rowoffset = offset;
//		if(limit == null){
//			limit = new int[2];
//		}
//		limit[0] = offset;
//		limit[1] = count;
	}


	public abstract String sentenceAsSQL();
	
	public String toString(){
		if (table == null && tableFromSelect == null) throw new RuntimeException("La sentencia no tiene tablas definidas," +
				" la sentencia no se puede construir");
		else return sentenceAsSQL();
	}

    
}
