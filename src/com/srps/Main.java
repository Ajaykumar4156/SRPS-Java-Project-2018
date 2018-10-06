package com.srps;

import com.srps.database.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * main function to start the SRPS app.
 * Can be used for Demo testing.
 * @author MD. Fakhruddin Gazzali
 * @version 1.0
 * @since 2018-10-28
 */

public class Main {

    public static void main(String[] args) {
        /* Checking database connection.
        * imported class from dbase.java
        */

        dbase DataBaseConnectionCheck = new dbase("SRPS", "hello123");
        ResultSet rs; // for showing a table

        try {
            //DataBaseConnectionCheck.insertDept("CEE", "AC1" );
            //DataBaseConnectionCheck.insertStudent("160041014", "Gazzali", "CSE", "Dhaka", "123456");
            //DataBaseConnectionCheck.insertTeacher("154383", "Ahnaf Munir", "CSE", "Dhaka", "123456");
            //DataBaseConnectionCheck.insertCourse("CSE 4402", "Visual Java Lab", "CSE", "1.0");
            //DataBaseConnectionCheck.insertMarks("160041014", "CSE", "CSE 4402", "55.02", "225.02");

            rs = DataBaseConnectionCheck.ListSearchAll("TEACHER"); //checking teacher table result
            if (rs == null)
                System.out.println("NO RESULT FOUND !");
            else {
                System.out.println("Teacher ID\tTeacher Name\tTeacher Dept.\tTeacher Address\tTeacher Contact");
                while (rs.next()) {

                    System.out.println(rs.getString(1) + "\t");
                    System.out.println(rs.getString(2) + "\t");
                    System.out.println(rs.getString(3) + "\t");
                    System.out.println(rs.getString(4) + "\t");
                    System.out.println(rs.getString(5) + "\t");

                }
            }
            System.out.println("-------------------------------------------------");

            rs = DataBaseConnectionCheck.ListSelectedStudentFromMarksDeatils("160041014");
            if (rs == null)
                System.out.println("NO RESULT FOUND !");
            else {
                System.out.println("Student ID\tStudent Name\tCourse ID.\tCourse Title\tMid Term\tFinal Exam");
                while (rs.next()) {
                    for (int n = 1; n <= 6; n++) // 6 cols. in STD_MARK_DETAILS so iterating 1 to 6
                        System.out.println(rs.getString(n) + "\t");
                }
            }

            System.out.println("-------------------------------------------------");
        }
        catch (SQLException ex){
            ex.printStackTrace(); // Exception handled and printed
        }
        finally {
            DataBaseConnectionCheck.close(); // finally statement makes sure database is always closed at end regardless of the occurrence of exception
        }

    }
}
