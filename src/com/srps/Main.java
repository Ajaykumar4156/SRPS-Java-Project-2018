package com.srps;

import com.srps.database.*;

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
        DataBaseConnectionCheck.insertDept("EEE", "AC1");

    }
}
