
import java.sql.*;

public class ExampleSQLite {
	public static void main(String[] args) throws Exception {
	    Class.forName("org.sqlite.JDBC");
	    Connection conn =
	      DriverManager.getConnection("jdbc:sqlite:example.db");
	    Statement stat = conn.createStatement();
	    stat.executeUpdate("drop table if exists facultades;");
	    stat.executeUpdate("create table facultades (nombre, tematica);");
	    PreparedStatement prep = conn.prepareStatement(
	      "insert into facultades values (?, ?);");
	    
	    prep.setString(1, "Exactas");
	    prep.setString(2, "matematicas");
	    prep.addBatch();
	    prep.setString(1, "Ingenieria");
	    prep.setString(2, "mas matematicas");
	    prep.addBatch();
	    prep.setString(1, "Humanidades");
	    prep.setString(2, "letras");
	    prep.addBatch();

	    conn.setAutoCommit(false);
	    prep.executeBatch();
	    conn.setAutoCommit(true);

	    ResultSet rs = stat.executeQuery("select * from facultades;");
	    while (rs.next()) {
	      System.out.print("nombre = " + rs.getString("nombre") + "  ");
	      System.out.println("tematica = " + rs.getString("tematica"));
	    }
	    System.out.println("Otra consulta");
	    rs.close();
	    rs = stat.executeQuery("select * from facultades where nombre = \"Exactas\";");
	    while (rs.next()) {
	      System.out.print("nombre = " + rs.getString("nombre") + "  ");
	      System.out.println("tematica = " + rs.getString("tematica"));
	    }
	    rs.close();
	    conn.close();
	  }

}
