package com.crossdb.sql;

import java.util.Vector;

/**
 * Implementación base de {@link CreateTableQuery}, de  esta clase deberían heredar todas
 * las implementaciones especificas para algún motor dado.
 *   
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.6
 */
public abstract class DefaultCreateTableQuery implements CreateTableQuery {

    protected String tableName;
    protected Vector<Column> columns;
    //protected boolean auto_defaults = true;
    protected boolean isTemporary = false;
    protected SelectQuery selectStatementSource = null;
    protected boolean havePrimaryKey = false;
    protected boolean haveAutoincrementPrimaryKey = false;

	protected Vector<TableConstraint> tableConstraints; 
    protected boolean hasFK = false;
    
    /**
     * La clase que mapea los tipos de datos de {@link java.sql.Types} a los tipos
     * que usa cada motor.
     */
    protected DataTypes datatype;
    
    //protected Formatter formatter;

    /**
     * El constructor obliga a que se le pase una implementación especifica de
     * {@link DataTypes} para que después la clase sepa como debe mapear los
     * tipos de datos de JDBC a los de cada motor en especifico.
     * 
     * @param datatype la clase que sirve para mapear los tipos de datos.
     */
    public DefaultCreateTableQuery(DataTypes datatype) {
        columns = new Vector<Column>();
        tableConstraints = new Vector<TableConstraint>();
        this.datatype = datatype;
    }

    public void setName(String name) {
        this.tableName = name;
    }

    public void setTemporary(boolean istemporary){
    	this.isTemporary = istemporary;
    }
    
    public void setSelectStatementSource( SelectQuery select){
    	this.selectStatementSource = select;
    }
    
    
    public void addColumn(Column column) {
//    	String pre = "";
//    	if (column.isPrimaryKey()){
//    		pkCounter++;
//    		if (pkCounter > 0) pre = ", "; 
//    		compositePrimaryKey += pre + column.getName();
//    	}
//    	if (column.isForeignKey()) setForeignKey(column);
    	
        columns.add(column);
    }
    
    public void addTableConstraint(TableConstraint tableConstraint){
    	if ( tableConstraint.isPrimaryKey() ){
    		if ( isHavePrimaryKey() ) throw new RuntimeException("No se puede agregar mas de una PRIMARY KEY a una tabla");
    		else setHavePrimaryKey(true);
    	}
    	
    	tableConstraints.add(tableConstraint);
    	Vector<Column> constraintColumns = tableConstraint.getColumns();
    	for (Column constraintColumn : constraintColumns){
    		if (!columns.contains(constraintColumn)) columns.add(constraintColumn);
    	}
    	
    }
    
    public void addPrimaryKeyColumn(Column pkcolumn){
    	TableConstraint pkConstraint = new TableConstraint(TableConstraint.TYPE_PRIMARY_KEY, pkcolumn);
    	addTableConstraint(pkConstraint);
    }
    
    public void addAutoincrementPrimaryKeyColumn(Column autoPKColumn){
    	if (havePrimaryKey) throw new RuntimeException("No se puede agregar mas de una calve primaria");
    	if ( !autoPKColumn.isAutoIncrementPK() ) autoPKColumn.setAutoIncrementPK(true);
    	haveAutoincrementPrimaryKey = true;
    	havePrimaryKey = true;
    	columns.add(autoPKColumn);
    	
    }
    
	public void addCompositePrimaryKeyColumns(Vector<Column> pkcolumns){
		addTableConstraint(new TableConstraint(TableConstraint.TYPE_PRIMARY_KEY, pkcolumns));
	}
	
	public void addUniqueColumn(Column uniqueColumn){
		addTableConstraint(new TableConstraint(TableConstraint.TYPE_UNIQUE, uniqueColumn));
	}
	
	public void addCompositeUniqueColumns(Vector<Column> uniqueColumns){
		addTableConstraint(new TableConstraint(TableConstraint.TYPE_UNIQUE, uniqueColumns));
	}
	
	public void addForeignKeyColumn(Column fkColumn){
		addTableConstraint(new TableConstraint(TableConstraint.TYPE_FOREIGN_KEY, fkColumn));
	}
	
	public void addForeignKeyColumn(Vector<Column> fkColumns){
		addTableConstraint(new TableConstraint(TableConstraint.TYPE_FOREIGN_KEY, fkColumns));
	}
	
	public boolean isHavePrimaryKey() {
		return havePrimaryKey;
	}

	protected void setHavePrimaryKey(boolean havePrimaryKey) {
		this.havePrimaryKey = havePrimaryKey;
	}
	
	public boolean isHaveAutoincrementPrimaryKey() {
		return haveAutoincrementPrimaryKey;
	}

	public void setHaveAutoincrementPrimaryKey(boolean haveAutoincrementPrimaryKey) {
		this.haveAutoincrementPrimaryKey = haveAutoincrementPrimaryKey;
	}
	//TODO quitar este método?
    public abstract String columnToString(Column column);

    /**
     * Convierte la sentencia en una cadena de texto pero es para uso interno de
     * la clase, el método adecuado para convertir la sentencia es {@link #toString()}.
     * 
     * @return La sentencia como una cadena de caracteres.
     */
    protected abstract String sentenceAsSQL();
    
    public String toString(){
    	
    	if (columns.isEmpty()) {
    		if (selectStatementSource == null) throw new RuntimeException("No se puede convertir a SQL no se agregaron columnas");
    	}
    	return sentenceAsSQL();
    }
    
    //TODO soporte default para tipos de datos extraños;
    //TODO falta compatibilidad de los tipos

}
