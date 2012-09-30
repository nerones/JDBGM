package com.crossdb.sql;

import java.util.ArrayList;
import java.util.List;

import com.crossdb.sql.optimization.OptimizationHint;

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
	protected ArrayList<Object> columns; // = new ArrayList(); // SELECT columns
	protected ArrayList<Object> tables;// = new ArrayList(); // FROM tables
	//List where_clauses; // WHERE where_clauses
	protected ArrayList<Object> order_by; // ORDER BY order_by
	protected ArrayList<Object> group_by; // GROUP BY order_by
    //protected String limit;
	protected WhereClause wclause = new WhereClause();
    protected DefaultSelectQuery union;
    private boolean isDistinct;
    protected ArrayList<Object> optimizationHints = new ArrayList<Object>();

    public DefaultSelectQuery(){
        //query1 = "";
        tables = new ArrayList<Object>();
        columns = new ArrayList<Object>();
        //where_clauses = new ArrayList();
        order_by = new ArrayList<Object>();
        group_by = new ArrayList<Object>();
    }

	
	public void addTable(String table){

		tables.add(table);
	}

	public void addTable(int join_type, String table){

		tables.add(new Join(join_type, table));
	}

	public void addTable(int join_type, String table, WhereCondition cond){
		Join j = new Join(join_type, table);
		j.addWhereCondition(cond);
		tables.add(j);


	}
	public void addTable(Join join){

		tables.add(join);
	}
	public void addColumn(String column){

		columns.add(column);
	}

    public void addColumn(String table, String column) {
        columns.add(table + "." + column);
    }
    
    abstract protected String getFunction(int functionId);
    // TODO  mover las funciones a Functions y ver los alias (AS)
    public void addFunctionColumn(String function, String column) {
        columns.add(function + "( " + column + " )");
    }

    public void addFunctionColumn(String function, String table, String column) {
        columns.add(function + "( " + table + "." + column + " )");
    }
    
    public void addFunctionColumn(String alias, int functionId, String table, String column) {
        columns.add(getFunction(functionId) + "( " + table + "." + column + " ) AS " + alias );
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

    

    


    public void addOrderBy(String order_by){

		this.order_by.add(order_by);
	}
	public void addGroupBy(String group_by){

		this.group_by.add(group_by);
	}


	public abstract String toString();



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

    public void union(SelectQuery sq) {
        this.union = (DefaultSelectQuery)sq;
    }

    public void setDistinct(boolean distinct) {
        this.isDistinct = distinct;
    }

    protected int limit[]; // 2 max that will be offset, count

	public void setLimit(int count){
		if(limit == null){
			limit = new int[1];
		}
		limit[0] = count;
	}

	public void setLimit(int offset, int count){
		if(limit == null){
			limit = new int[2];
		}
		limit[0] = offset;
		limit[1] = count;
	}

    public void addOptimizationHint(OptimizationHint optimizationHint) {
        optimizationHints.add(optimizationHint);
    }

    public boolean isDistinct() {
        return isDistinct;
    }
}
