package sae.demo.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.postgresql.ds.PGSimpleDataSource;

public class BDD {

        PGSimpleDataSource ds = new PGSimpleDataSource();
        Connection conn;

        public BDD() throws SQLException {
            ds.setServerName("iutinfo-sgbd.uphf.fr");
            ds.setDatabaseName("iutinfo300");
            ds.setUser("iutinfo300");
            ds.setPassword("3mwMCOap");

            this.conn = ds.getConnection();
        }

    public Connection getConn() {
        return conn;
    }
}
