/*
 * Author : Avibha IT Solutions Copyright 2006 Avibha IT Solutions. All rights
 * reserved. AVIBHA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.avibha.com
 *  
 */
package com.bzpayroll.common.db;
import java.sql.SQLException;
/**
 * @author avibha
 * 
 */
/**
 * Subclass of DatabaseException with specific knowledge of various MySQL sql error codes.
 * 
 */
public class MySQLException extends DatabaseException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * MySQLException Constructor 
     * @param msg exception message
     * @param e SQLException
     */
    public MySQLException(String msg, SQLException e)
    {
        super(msg, e);
    }

    /**
     * MySQLException Constructor 
     * @param msg exception message
     */
    public MySQLException(String msg)
    {
        super(msg);
    }

    /**
     * Was db exception caused by a data integrity violation
     * @return true or false
     */
    public boolean isDataIntegrityViolation()
    {
        switch (sqlErrorCode)
        {
            case 1217: return(true);
            default  : return(false);
        }
    }

    /**
     * Was db exception caused by a duplicate record (unique constraint) violation 
     */
    public boolean isUniqueConstraintViolation()
    {
    	return((sqlErrorCode == 1062) || (sqlErrorCode == 1022));
    }

    /**
     * Was db exception caused by bad sql grammer (a typo)
     * return true or false
     */
    public boolean isBadSQLGrammar()
    {
        return(sqlErrorCode == 1064);
    }

    /**
     * Was db exception caused by referencing a non existent table or view
     * @return true or false
     */
    public boolean isNonExistentTableOrViewOrCol()
    {
        switch (sqlErrorCode)
        {
            case 1146: 
            case 1054: return(true);
            default  : return(false);
        }
    }

    /** 
     * Was db exception caused by referencing a invalid bind parameter name
     * @return true or false
     */
    public boolean isInvalidBindVariableName()
    {
        return(false);
    }
    
    /**
     * Was db exception caused by database being unavailable
     * @return true or false
     */
    public boolean isDatabaseUnavailable()
    {
        return(sqlErrorCode == 64);
    }

    /**
     * Was db exception caused by a row lock error or some other sql querty timeout
     * return true or false
     */
    public boolean isRowlockOrTimedOut()
    {
        switch (sqlErrorCode)
        {
            case 1213: 
            case 1205: return(true);
            default  : return(false);
        }
    }
    
    /** was db exception caused by a an unbound variable (parameter) 
     * @return boolean
     */
    public boolean isVarParameterUnbound()
    {
        return(sqlErrorCode == 0);
    }
}
