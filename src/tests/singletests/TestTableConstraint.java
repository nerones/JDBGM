/**
 * Copyright 2011 Nelson Efrain Abraham Cruz
 *
 * This file is part of JDBGM.
 * 
 * JDBGM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDBGM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JDBGM.  If not, see <http://www.gnu.org/licenses/>.
 */
package tests.singletests;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.crossdb.sql.Column;
import com.crossdb.sql.TableConstraint;

/**
 * 
 * @author Nelson Efrain A. Cruz
 * @version 0.1
 */
public class TestTableConstraint {
	
	TableConstraint pkConstraint;
	TableConstraint fkConstraint;
	TableConstraint uConstraint;
	
	@Before
	public void setUp(){
		pkConstraint = new TableConstraint(TableConstraint.TYPE_PRIMARY_KEY, new Column("column1"));
		fkConstraint = new TableConstraint(TableConstraint.TYPE_FOREIGN_KEY, new Column("column1", 0, "fktable", "fkcolumn"));
		uConstraint = new TableConstraint(TableConstraint.TYPE_UNIQUE, new Column("column1"));
	}
	
	@Test
	public void testSimpleConstraint(){
		Assert.assertEquals(" PRIMARY KEY (column1 )", pkConstraint.toString());
		Assert.assertEquals(" UNIQUE (column1 )", uConstraint.toString());
		Assert.assertEquals(" FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn)", fkConstraint.toString());
		//tableConstraint.toString();
	}
	
	@Test
	public void testCompleteFKConstraint(){
		fkConstraint.setConstraintName("myFK");
		String expectedSQL = " CONSTRAINT myFK FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn)";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
		
		fkConstraint.setMatchTypeFK(TableConstraint.MATCH_FULL);
		expectedSQL = " CONSTRAINT myFK FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn) MATCH FULL";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
		
		fkConstraint.setMatchTypeFK(TableConstraint.MATCH_PARTIAL);
		expectedSQL = " CONSTRAINT myFK FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn) MATCH PARTIAL";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
		
		fkConstraint.setFirstReferentialTriggeredAction(TableConstraint.TRIGGERED_ON_UPDATE, TableConstraint.REFERENTIAL_ACTION_NO_ACTION);
		expectedSQL = " CONSTRAINT myFK FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn) MATCH PARTIAL ON UPDATE NO ACTION";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
		
		fkConstraint.setSecondReferentialTriggeredAction(TableConstraint.TRIGGERED_ON_DELETE, TableConstraint.REFERENTIAL_ACTION_NO_ACTION);
		expectedSQL = " CONSTRAINT myFK FOREIGN KEY (column1 ) REFERENCES fktable(fkcolumn) MATCH PARTIAL ON UPDATE NO ACTION ON DELETE NO ACTION";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
	}
	@Test
	public void testCompundConstraint(){
		
		Vector<Column> columns = new Vector<Column>();
		columns.add(new Column("column1"));
		columns.add(new Column("column2"));
		columns.add(new Column("column3"));
		
		pkConstraint = new TableConstraint(TableConstraint.TYPE_PRIMARY_KEY, columns);
		uConstraint = new TableConstraint(TableConstraint.TYPE_UNIQUE, columns);
		
		String expectedSQL = " PRIMARY KEY (column1 ,column2 ,column3 )"; 
		Assert.assertEquals( expectedSQL, pkConstraint.toString());
		expectedSQL = " UNIQUE (column1 ,column2 ,column3 )";
		Assert.assertEquals( expectedSQL, uConstraint.toString());
		
		columns.removeAllElements();
		columns.add(new Column("column1", 0, "fktable", "fkcolumn1"));
		columns.add(new Column("column2", 0, "fktable", "fkcolumn2"));
		columns.add(new Column("column3", 0, "fktable", "fkcolumn3"));
		
		fkConstraint = new TableConstraint(TableConstraint.TYPE_FOREIGN_KEY, columns);
		expectedSQL = " FOREIGN KEY (column1 ,column2 ,column3 ) REFERENCES fktable(fkcolumn1 ,fkcolumn2 ,fkcolumn3)";
		Assert.assertEquals( expectedSQL, fkConstraint.toString());
	}

}
