/*
 * Filename: DatabaseConnectionBean.java
 * Author: Lara Aras
 * Created: 16/11/2021
 * Operating System: Windows 10 Enterprise
 * Version: Project 1
 * Description: This file contains the bean to be used by the web service to 
 *              connect to the database and perform data updates and inserts.
 */
package beans;

import java.io.*;
import java.sql.*;

/**
 * This bean provides a connection for use by the web service
 *
 * @author Lara Aras
 */
public class DatabaseConnectionBean implements Serializable {

    private Connection connection;
    private static String driverName
            = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dbURL
            = "jdbc:sqlserver://localhost:1433;databaseName=Forum;"
            + "user=admin;password=1234;";

    public DatabaseConnectionBean() {
        makeConnection();
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 16/11/2021
     * <p>
     * Returns the database connection
     *
     * @return Connection
     * @author Lara Aras
     */
    public Connection getConnection() {
        if (connection == null) {
            makeConnection();
        }

        return connection;
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 16/11/2021
     * <p>
     * Connects to the database
     *
     * @author Lara Aras
     */
    private void makeConnection() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException sqle) {
            System.out.println("SQLError " + sqle);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("ClassNotFound " + cnfe);
        }
    }
}
