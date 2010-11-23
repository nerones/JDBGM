/**
 * 
 */
package example;

import com.crossdb.sql.AlterTableQuery;
import com.crossdb.sql.Column;
import com.crossdb.sql.CreateTableQuery;
import com.crossdb.sql.Join;
import com.crossdb.sql.SQLFactory;
import com.crossdb.sql.SelectQuery;
import com.crossdb.sql.SpecificSQLFactory;
import com.crossdb.sql.WhereCondition;

/**
 * @author Nelson Efrain A. Cruz
 *
 */
public class CrossdbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpecificSQLFactory fact = SQLFactory.getFactory(SQLFactory.MYSQL_DB);
		SelectQuery sq = fact.getSelectQuery();
		sq.addColumn("nombre");
		sq.addColumn("apellido");
		sq.addTable("personas");
		sq.addTable(Join.LEFT_OUTER_JOIN, "rada", new WhereCondition("idalumno", WhereCondition.EQUAL_TO, "idcasa"));
		sq.addWhereCondition("id",WhereCondition.EQUAL_TO , 10);
		sq.addWhereCondition("id",WhereCondition.EQUAL_TO , 10);
		System.out.println(sq);
		AlterTableQuery atq = fact.getAlterTableQuery();
		atq.setTable("tabla");
		atq.addColumn(new Column("casa"));
		System.out.println(atq);
		CreateTableQuery ctq = fact.getCreateTableQuery();
		ctq.setName("persona");
		Column col = new Column("direccion", java.sql.Types.VARCHAR);
		col.setPrimaryKey(true);
		ctq.addColumn(col);
		System.out.println(ctq);

	}

}
