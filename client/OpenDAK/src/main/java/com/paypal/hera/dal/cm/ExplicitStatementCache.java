package com.paypal.hera.dal.cm;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This interface allows explicit key lookup for prepared statements
 * 
 * Note that keys for prepared and callable statements are kept
 * in the same map, which means that keys should never overlap
 * for the same connection
 * 
 * This interface is implemented on connection
 * 
 * @author ichernyshev
 */
public interface ExplicitStatementCache {

	/**
	 * Prepares statement and caches it using the key
	 */
	public PreparedStatement prepareStatement(Object key,
		String sql, int resultSetType, int resultSetConcurrency)
		throws SQLException;

	/**
	 * Prepares statement and caches it using the key
	 */
	public PreparedStatement prepareStatement(Object key, String sql)
		throws SQLException;

	/**
	 * Prepares statement and caches it using the key
	 */
	public PreparedStatement prepareStatement(Object key, String sql, int autoGeneratedKeys)
		throws SQLException;
	
	/**
	 * Prepares callable statement and caches it using the key
	 */
	public CallableStatement prepareCall(Object key, String sql,
		int resultSetType, int resultSetConcurrency)
		throws SQLException;

	/**
	 * Prepares callable statement and caches it using the key
	 */
	public CallableStatement prepareCall(Object key, String sql)
		throws SQLException;

	/**
	 * Looks up pre-cached prepared statement using key,
	 * or returns null if key does not resolve to a cached statement
	 * 
	 * Statement must be closed after this call
	 */
	public PreparedStatement lookupPreparedStatement(Object key)
		throws SQLException;

	/**
	 * Looks up pre-cached callable statement using key,
	 * or returns null if key does not resolve to a cached statement
	 * 
	 * Statement must be closed after this call
	 */
	public CallableStatement lookupCallableStatement(Object key)
		throws SQLException;

	/**
	 * Fetches SQL associated with cached statement
	 * 
	 * Note that only statement returned by lookupCachedStatement
	 * can be used here
	 * 
	 * This method is needed for CAL Wrapper to provide correct SQL in log
	 */
	public String getCachedStatementSQL(PreparedStatement stmt)
		throws SQLException;

	/**
	 * Removes statement from cache, or does nothing if key does
	 * not resolve to a cached statement
	 * 
	 * If statement is currently in use, it will be marked as dead
	 * and will not return back to the pool
	 */
	public void removeStatement(Object key)
		throws SQLException;
}