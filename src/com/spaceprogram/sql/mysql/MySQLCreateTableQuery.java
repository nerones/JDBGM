

package com.spaceprogram.sql.mysql;

import com.crossdb.sql.Column;
import com.crossdb.sql.DefaultCreateTableQuery;
import com.crossdb.sql.TableConstraint;

/**
 *
 * @author  prophecy
 * @author Nelson Efrain A. Cruz
 * @version 0.5
 */
public class MySQLCreateTableQuery extends DefaultCreateTableQuery {

    public MySQLCreateTableQuery() {
        super(new MySQLDataTypes());
    }
    
    /* (non-Javadoc)
	 * @see com.crossdb.sql.DefaultCreateTableQuery#columnToString(com.crossdb.sql.Column)
	 */
	@Override
	public String columnToString(Column column) {
		
		String columnAsString = column.getName() + " ";
		columnAsString += datatype.getAsString(column);
		if (column.isNullable() == 0) columnAsString += " NOT NULL";
		//TODO revisar valor por defecto y el tipo de dato
		if (column.getColumnDefaultValue() != null) columnAsString += " DEFAULT " + column.getColumnDefaultValue();
		if (column.isAutoIncrementPK()) columnAsString += " AUTO_INCREMENT PRIMARY KEY";
		
		return columnAsString;
	}

	public String toString() {

		//String query1;
		
		String createAsString = "CREATE TABLE ";
		
		if (isTemporary) createAsString += " TEMPORARY";
		createAsString +=  tableName + " ( ";
		for (Column column : columns){
			createAsString += columnToString(column) + ", ";
		}
		
		for (TableConstraint tableConstraint : tableConstraints){
			createAsString += tableConstraint.toString() + ", ";
		}
		//Elimino el ultimo ", " agregado
		createAsString = createAsString.substring(0, createAsString.length()-2);
		return createAsString + " )";
//		
//		for (int j = 0; j < columns.size(); j++) {
//			Column df = (Column) (columns.get(j));
//			query1 += df.getName() + " ";
//
//			query1 += datatype.getAsString(df);
//
//			// if (df.isAutoIncrement()) {
//			// //una tabla de mysql solo puede tener una columna auto_increment
//			// query1 += " AUTO_INCREMENT";// primary key NOT NULL";
//			if (df.isUnique()) query1 += " UNIQUE";
//			if (df.isPrimaryKey() && !isCompositePK()){
//				query1 += " PRIMARY KEY";
//				//No existe autoincrement por ahora en JBGM
//				//if (df.isAutoIncrement()) query1 += " AUTO_INCREMENT";
//			} 
//			if (df.isNullable() == 0)
//				query1 += " NOT NULL";
//			if (df.getColumnDefaultValue() != null) {
//				if (!(df.getType() == java.sql.Types.TIMESTAMP)) {// "datetime")){
//					// Can't use functions like Now() in defaults in mysql
//					query1 += " DEFAULT " + df.getColumnDefaultValue();
//				}
//
//			}
//
//			if (j < columns.size() - 1) {
//				query1 += ", ";
//			}
//
//		}
//		if (isCompositePK())
//			query1 += ", PRIMARY KEY (" + getCompositePK()+")";
//		if (hasFK) {
//			Vector<String[]> fks = getForeignKeys();
//			for (String[] strings : fks) {
//				query1 += ", FOREIGN KEY ("+strings[1]+") REFERENCES "+strings[0]+"("+strings[2]+")";
//			}
//		}
		// we add the foreign key or primary key definition if we have then

//		query1 += " )";


            // MYSQL already does this so skip it.
            /*else if(auto_defaults){ // set defaults automatically

             if(df.isNullable() == 0){ //not nullable
             if(!df.isAutoIncrement()){
             if(vendor != MY_SQL){

             if(df.getType().equalsIgnoreCase("bit")){
             if(vendor == SAPDB){
             query1 += " DEFAULT FALSE ";
             }
             else{
             query1 += " DEFAULT (0) ";
             }

             }
             else if(df.getType().equalsIgnoreCase("int")){
             if(vendor == SAPDB){
             query1 += " DEFAULT 0 ";
             }
             else{
             query1 += " DEFAULT (0) ";
             }
             }
             else if(df.getType().equalsIgnoreCase("datetime")){
             if(vendor == SAPDB){
             query1 += " DEFAULT TIMESTAMP ";
             }
             else{
             query1 += " DEFAULT (getDate()) ";
             }
             }

             }
             }
             else{ // is autoinc
             // sapdb needs to set default on primary keys
             if(vendor == SAPDB){
             query1 += " DEFAULT SERIAL ";
             }
             }
             }


             }
             */

       
//        return query1;
    }

}
