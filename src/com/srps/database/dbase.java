package com.srps.database;

import java.util.Scanner;
import java.sql.*;

/**
 *  <h1> Controls backend connection with OracleXE database</h1>
 *  The main purpose of this class is to streamline the database connectivity and query precessing for front end
 *  developers. Extensive uses of methods simplifies both connectivity, result retrieval and exception handling.
 *  Front end developers only have to deal with SQLException while processing 'ResultSet' type objects.
 *  Generally the methods that end with or has 'List' in them returns ResultSet type objects. Returns NULL in case of error.
 *  Generally the methods that end with or has 'Count' in them returns int >= 0 or -1 in case of error
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
     * Constructor for dbase class objects. Creates connection with database.
     * By default 'username' will be selected as SRPS and 'password' as hello123 (case-sensitive).
     * @param _username String Username for the database account
     * @param _password String Password that is allocated to the user
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
     * A method for checking error state.
     * @return boolean True if there is any error.
     */
    boolean getErrorState(){
        return error;
    }

    /**
     * A method for checking connection state.
     * @return boolean False if there is no connection.
     */
    boolean getConnectonState(){
        return connected;
    }

    /**
     *
     *Method to Insert Student info into "DEPT" table .
     * @param dept_name for department name
     * @param building for  building name
     */

    public int insertDept(String dept_name, String building)
    {
        query = String.format("INSERT INTO DEPT VALUES(\'%s\' , \'%s\')", dept_name, building);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     *Method to Insert Student info into "STUDENT" table .
     * @param s_id for Student ID
     * @param s_name for student name
     * @param s_dept for student department
     * @param s_address for student address
     * @param s_contact for student contact
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */

    public int insertStudent(String s_id, String s_name, String s_dept, String s_address, String s_contact )
    {
        query = String.format("INSERT INTO STUDENT VALUES (%s, \'%s\' , \'%s\' , \'%s\' , \'%s\')", s_id, s_name, s_dept, s_address, s_contact);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     *Method to Insert Student info into "TEACHER" table .
     * @param t_id for Student ID
     * @param t_name for student name
     * @param t_dept for student department
     * @param t_address for student address
     * @param t_contact for student contact
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */

    public int insertTeacher(String t_id, String t_name, String t_dept, String t_address, String t_contact )
    {
        query = String.format("INSERT INTO TEACHER VALUES (%s, \'%s\' , \'%s\' , \'%s\' , \'%s\')", t_id, t_name, t_dept, t_address, t_contact);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     * @param c_id for course id (e.g.: "CSE 4402")
     * @param c_tilte for course title
     * @param c_dept course department
     * @param c_credit for course credit
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */
    public int insertCourse(String c_id, String c_tilte, String c_dept, String c_credit)
    {
        query = String.format("INSERT INTO COURSES VALUES (\'%s\', \'%s\', \'%s\', %s)",c_id, c_tilte, c_dept, c_credit);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }


    /**
     *
     * @param m_sid for student id to be inserted into marks
     * @param m_dept for student department to be inserted into marks
     * @param m_cid for student taken course
     * @param midexam for marks obtained in the midterm exam
     * @param finalexam for marks obtained in the final exam.
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */
    public int insertMarks(String m_sid, String m_dept, String m_cid, String midexam, String finalexam)
    {
        query = String.format("INSERT INTO MARKS VALUES (%s, \'%s\', \'%s\', %s, %s)",m_sid, m_dept, m_cid, midexam, finalexam);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     * method to update mid exam marks if mid marks are changed after it's been inserted once
     * @param s_iD student id to be updated
     * @param midexam midexam marks to be updated
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */
    public int updateMidMarks(String s_iD, String midexam)
    {
        query = String.format("UPDATE MARKS SET MIDEXAM = %s WHERE M_SID = %s", midexam, s_iD);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     * method to update final exam marks if mid marks are changed after it's been inserted once
     * @param s_iD student id to be updated
     * @param finalexam finalexam marks to be updated
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     */
    public int updateFinalMarks(String s_iD, String finalexam)
    {
        query = String.format("UPDATE MARKS SET FINALEXAM = %s WHERE M_SID = %s", finalexam, s_iD);
        Integer count1 = getCount();
        if (count1 != null) return count1;
        return -1;
    }

    /**
     *
     * @param s_id student id to be deleted from STUDENT Table
     * @return count1 , on success an integer value indicating how many rows are affected by this query .
     * @return -2 if the user chooses to CANCEL this delete operation
     * @return -1 if anything else happens
     */

    public int deleteStudent(String s_id)
    {
        char choice;
        Scanner confirm = new Scanner(System.in);
        System.out.printf("Do you want REALLY delete this ID %s (Y/N)?.\nThis action CAN\'T be UNDONE : ", s_id);
        choice = confirm.next().charAt(0);
        /*if (choice != 'Y' || choice != 'y' || choice != 'N' || choice != 'n')
        {
            System.out.printf("Please Enter a valid Choice.\nDelete this ID %s (Y/N) : ",s_id);
        }*/
        if(choice == 'Y') {
            query = String.format("DELETE FROM STUDENT WHERE S_ID = %s", s_id);
            Integer count1 = getCount();
            if (count1 != null) return count1;
            return -1;
        }
        else return -2;
    }

    /*--------------- Table Returning Method Starts---------------------*/
    /**
     *
     * method to get full STUDENT table.
     * @return On SUCCESS : a resultset or a full table data; On Failure : null.
     */
    public ResultSet listGetAllStudent()
    {
        query = String.format("SELECT * FROM STUDENT");
        if (resultSetHandler())
            return resultSet;
        return null;
    }

    /**
     *
     * @param s_id student id to be searched for
     * @return On SUCCESS : a resultset or a full table data; On Failure : null.
     */
    public ResultSet listSelectedStudentFromMarksDeatils(String s_id)
    {
        query = String.format("SELECT * FROM STD_MARKS_DETAILS");
        if (resultSetHandler())
            return resultSet;
        return null;
    }

    /*--------------- Table Returning Method Ends---------------------*/

    /*---------------Repetition preventing methods Starts-------------*/
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

    /**
     * Repetition preventing method.
     * @param columnIndex int Index of the column of result set to derive integer value from.
     * @return boolean Whether setting value of ResultSet rs was successful or not.
     */
    private boolean integerHandler(int columnIndex) {
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next())
                retint = resultSet.getInt(columnIndex);
            return true;
        } catch (SQLException ex) {
            error = true;
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Repetition preventing method.
     * @return boolean Whether assigning resultSet was successful or not.
     */
    private boolean resultSetHandler() {
        try{
            resultSet = statement.executeQuery(query);
            return true;
        }
        catch (SQLException ex){
            error = true;
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Repetition preventing method.
     * @return boolean Whether a certain value is present or not in a single attribute returning ResultSet.
     */
    private boolean boolHandler(String match){
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                return match.equals(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            error = true;
            ex.printStackTrace();
        }
        return false;
    }

    /*---------------Repetition preventing methods Starts-------------*/
}



