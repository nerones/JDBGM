/**
 * Structure to hold column name and value pair for UpdateableQuery's
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Sep 5, 2002
 * Time: 6:50:18 PM
 * 
 */
package com.crossdb.sql;

public class ColumnValue
{
    String name;
    Object value;

    boolean isAutoIncrement;

    /**
     * Used for auto-inc columns that need a sequence (Oracle)
     */
    String sequence;


    boolean noAlter;


    /**
     *
     * this construction NEVER gets called???
     * always goes through int constructor i think.  Why is that??
     *
     * @param columnName
     * @param columnValue
     */
      public ColumnValue(String columnName, char columnValue){

          this(columnName, "" + columnValue); // convert to string
          System.out.println("GOING INTO CHAR: " + columnValue);
      }

    public ColumnValue(String columnName, Object columnValue)
    {
        this.name = columnName;
        this.value = columnValue;
    }




    public ColumnValue(String columnName, int columnValue){
        this(columnName, new Integer(columnValue));
    }
    public ColumnValue(String columnName, float columnValue){
        this(columnName, new Float(columnValue));
    }
    public ColumnValue(String columnName, double columnValue){
        this(columnName, new Double(columnValue));
    }
    public ColumnValue(String columnName, boolean columnValue){
        this(columnName, new Boolean(columnValue));
    }


    public String getName()
    {
        return name;
    }

    public void setName(String columnName)
    {
        this.name = columnName;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object columnValue)
    {
        this.value = columnValue;
    }

    public boolean isAutoIncrement()
    {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement)
    {
        isAutoIncrement = autoIncrement;
    }

    public boolean isNoAlter()
    {
        return noAlter;
    }

    public void setNoAlter(boolean noAlter)
    {
        this.noAlter = noAlter;
    }

    public String getSequence()
    {
        return sequence;
    }

    public void setSequence(String sequence)
    {
        this.sequence = sequence;
    }
}
