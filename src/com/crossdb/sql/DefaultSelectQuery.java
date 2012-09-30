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

	// TODO ver si se pueden declarar los ArrayList de algun tipo concreto
	protected StringBuilder columns; // = new ArrayList(); // SELECT columns
	protected String table;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	protected String order_by; // ORDER BY order_by
	protected String group_by; // GROUP BY order_by
	protected String havingExpresion;
    //protected String limit;
	protected WhereClause wclause;
    protected SelectQuery union;
    protected boolean isUnionAll;
    protected boolean isDistinct;
    protected Join join;
    protected int rowlimit;
    protected int rowoffset;
    
    //protected ArrayList<Object> optimizationHints = new ArrayList<Object>();

    public DefaultSelectQuery(){
        //query1 = "";
        table = null;
        columns = new StringBuilder();
        //where_clauses = new ArrayList();
        order_by = null;
        group_by = null;
        join = null;
        wclause = null;
        rowlimit = -1;
        rowoffset = -1;
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
    
    public void addColumn(String table, String alias, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(table).append(".").append(column);
        columns.append(" AS ").append(alias);
    }
    
    abstract protected String getFunction(int functionId);
    // TODO  mover las funciones a Functions y ver los alias (AS)
    
    public void addFunctionColumn(String function, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(function).append("( ").append(column).append(" )");
    }

    public void addFunctionColumn(String function, String table, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(function).append("( ").append(table).append('.').append(column).append(" )");
    }
    
    public void addFunctionColumn(String alias, int functionId, String table, String column) {
    	if (columns.length() != 0) columns.append(", ");
        columns.append(getFunction(functionId)).append("( ").append(table).append('.').append(column).append(" )");
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
	}

	public Join addJoin(){
		if (join == null) join = new Join();
		return join;
	}
	
	public WhereClause addWhere(){
		if (wclause == null) wclause = new WhereClause();
		return wclause;
	}

	public void addGroupBy(String group_by){

		this.group_by = group_by;
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
		this.order_by = order_by;
	}
	
    //protected int limit[]; // 2 max that will be offset, count

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
		if (table == null) throw new RuntimeException("La sentencia no tiene tablas definidas," +
				" la sentencia no se puede construir");
		else return sentenceAsSQL();
	}



    /**
     * will recurse through all SelectQuery's until union is null
     * @return query unioned together
     */
    protected StringBuffer getUnionizedQuery(StringBuffer ret) {
        if(union != null){
            return ret.append( " UNION " + union.toString());
        }
        else{
            return ret;
        }
    }
    
    /*
    public CrossdbResultSet execute(Connection conn) throws SQLException{
		Statement stmt = conn.createStatement();
		CrossdbResultSet ret = execute(stmt);
		// Can't close the statement here, because that also closes the Resultset!  ouch
        //stmt.close();
        // but lets set a flag that we made the statement so we should close it after
        ret.setStatementCreated(stmt);
		return ret;
	}
    public CrossdbResultSet execute(Statement stmt) throws SQLException{

        String q = getUnionizedQuery();
		return new DefaultResultSet(stmt.executeQuery(q));
	}
	*/

//    public void addOptimizationHint(OptimizationHint optimizationHint) {
//        optimizationHints.add(optimizationHint);
//    }

    
}
