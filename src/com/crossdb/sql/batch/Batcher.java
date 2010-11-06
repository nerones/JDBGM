/**
 * Holds queries to execute as a batch
 * Executes queries in order they were added.
 *
 * Only accepts ExecuteUpdate queries
 *
 * @author Travis Reeder - travis@spaceprogram.com
 * @version 0.1
 * Date: Aug 14, 2002
 * Time: 1:35:04 PM
 * 
 */
package com.crossdb.sql.batch;

import com.crossdb.sql.ExecuteUpdate;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

/*
examples of batching with the Statements, should use at least the first one somehow
A simple example for java.sql.Statement would be:

Statement stmt = conn.createStatement();
stmt.insert("DELETE FROM Users");
stmt.insert("INSERT INTO Users VALUES('rod', 37, 'circle')");
stmt.insert("INSERT INTO Users VALUES('jane', 33, 'triangle')");
stmt.insert("INSERT INTO Users VALUES('freddy', 29, 'square')");

int[] counts = stmt.executeBatch();

A PreparedStatement is slightly different. It can only handle the one piece of SQL syntax, but it can have many parameters. So, rewriting part of the above example, we get:

// note that we don't do the delete anymore.

PreparedStatement stmt = conn.prepareStatement(
    "INSERT INTO Users VALUES(?,?,?)"
    );

User[ ] users = ...;
for(int i=0; i<users.length; i++) {
    stmt.setInt(1, users[i].getName());
    stmt.setInt(2, users[i].getAge());
    stmt.setInt(3, users[i].getShape());
     stmt.addBatch( );
}

int[ ] counts = stmt.executeBatch();

*/
public class Batcher
{
    private List queries = new ArrayList();

    /**
     * Add query to batch
     */
    public void add(ExecuteUpdate updateable){
        queries.add(updateable);
    }
    /**
     * run batch
     */
    public void run(Connection conn) throws SQLException {

        // use statement for optimization
        Statement stmt = conn.createStatement();
        for (int i = 0; i < queries.size(); i++)
        {
            ExecuteUpdate query = (ExecuteUpdate) queries.get(i);
            query.execute(stmt);
        }
    }

}
