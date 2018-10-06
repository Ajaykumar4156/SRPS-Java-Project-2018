package com.srps.database;

import java.sql.*;

/**
 *  <h1> Controls backend connection with OracleXE database</h1>
 *  The main purpose of this class is to streamline the database connectivity and query precessing for front end
 *  developers. Extensive uses of methods simplifies both connectivity, result retrieval and exception handling.
 *  Front end developers only have to deal with SQLException while processing 'ResultSet' type objects.
 *
 *  @author MD. Fakhruddin Gazzali
 *  @version 1.0
 *  @since 2018-10-28
 */

public class dbase {
    private Connection connection; // Connection type object to get connection using driver.
    private String dbURL; // URL for database used.
    private Statement statement; // Statement type object to execute DDL and DML queries.
    private String query; // Holds query for execution by statement object.
    private ResultSet resultSet; // ResultSet type object to return database tables.
    String name; // Username of teacher or student.
    private String password; // Password for database.
    private boolean error; // Error state. Can be called at any time to check for errors.
    boolean connected; // Connection state. Can be called at any time to check for connectivity.
    private int retint; // Private variable to return int type data by methods.

    /**
     * * Constructor for dbase class objects. Creates connection with database.
     * * By default 'username' will be selected as SRPS and 'password' as hello123 (case-sensitive).
     * * @param _username String Username for the database account
     * * @param _password String Password that is allocated to the user
     */

    public dbase(String _username, String _password) {
        connection = null;
        error = false;
        connected = false;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
            name = _username;
            password = _password;
            connection = DriverManager.getConnection(dbURL, name, password);

            if (connection != null) {
                connected = true;
                System.out.println("Database Connected Successfully, Cheers !!!");
                statement = connection.createStatement();
            } else
                connected = false;

        } catch (ClassNotFoundException | SQLException ex) {
            error = true;
            ex.printStackTrace();
        }
    }

    /**
     * Closes connection with database. Should always be called at the end of each session to close the database.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                resultSet.close();
                connection.close();
            }
        } catch (SQLException ex) {
            error = true;
            ex.printStackTrace();
        }
    }

    /**
     *Method to Insert Student info into "DEPT" table .
     * @param dept_name for department name
     * @param building for  building name
     */

    public int insertDept(String dept_name, String building)
    {
        query = String.format("INSERT INTO DEPT ('%s' , '%s');", dept_name, building);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *Method to Insert Student info into "STUDENT" table .
     * @param s_id for Student ID
     * @param s_name for student name
     * @param s_dept for student department
     * @param s_address for student address
     * @param s_contact for student contact
     */

    public int insertStudent(String s_id, String s_name, String s_dept, String s_address, String s_contact )
    {
        query = String.format("INSERT INTO STUDENT VALUES (%s, '%s' , '%s' , '%s' , '%s' ;)", s_id, s_name, s_dept, s_address, s_contact);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     * Repetition preventing method.
     * @return Integer nullable integer data type as count.
     */
    private Integer getCount() {
        int count;
        try{
            count = statement.executeUpdate(query);
            return count;
        }
        catch (SQLException ex) {
            error = true;
            ex.printStackTrace();
        }
        return null;
    }

}


