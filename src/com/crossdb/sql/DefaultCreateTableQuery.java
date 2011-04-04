package com.crossdb.sql;

import java.util.ArrayList;

/**
 * Implementación base de {@link CreateTableQuery}, de  esta clase deberían heredar todas
 * las implementaciones especificas para algún motor dado.
 *   
 * @author Travis Reeder - travis@spaceprogram.com
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 * 
 * Date: Jun 27, 2002
 * Time: 8:47:24 PM
 *
 */
public abstract class DefaultCreateTableQuery implements CreateTableQuery {

    protected String name;
    protected ArrayList<Column> columns;
    protected boolean auto_defaults = true;
    protected boolean isTemporary = false;
    /**
     * Cuenta la cantidad de columnas que forman la clave Primaria (pk) tener en
     * cuenta que si su valor final es 0 se trata de una pk formada por una única
     * columna, valores mayores a 0 indican la cantidad de columnas que forman la
     * clave primaria por lo que se estaría frente a una pk compuesta por varias
     * columnas.  
     */
    protected int pkCounter = -1;
    /**
     * De tener la tabla una clave primaria (pk) compuesta por mas de una columna
     * esta variable guarda la lista de columnas que la conforman en forma de String.
     */
    protected String compositePrimaryKey = "";

    public DefaultCreateTableQuery() {
        columns = new ArrayList<Column>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addColumn(Column column) {
    	String pre = "";
    	if (column.isPrimaryKey())
    		pkCounter++;
    		if (pkCounter > 0) pre = ", "; 
    		compositePrimaryKey += pre + column.getName();
        columns.add(column);
    }
    
    public boolean isCompositePK(){
    	if (pkCounter > 0) return true;
    	else return false;
    }
    
    public String getCompositePK(){
    	// TODO tirar error cuando me llamen y no sea clave compuesta?
    	return compositePrimaryKey;
    }

    public void setAutoDefaults(boolean b) {
        auto_defaults = b;
    }
    
    public void setTemporary(boolean istemporary){
    	isTemporary = istemporary;
    }
    public abstract String toString();
    //TODO soporte para multiples claves foraneas
    //TODO soporte para FK compuesta (multiples columnas)
    //TODO soporte para acciones que siguen a FK
  //TODO soporte unique
  //TODO soporte default;

}
