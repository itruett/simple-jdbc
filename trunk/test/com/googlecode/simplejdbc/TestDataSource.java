/*
 *	Copyright 2009 Isaac Truett.
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.googlecode.simplejdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * A {@link DataSource} implementation for testing.
 */
public class TestDataSource implements DataSource {
	private TestConnection testConnection = new TestConnection();

	/**
	 * @see javax.sql.DataSource#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return testConnection;
	}

	/**
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns <code>true</code> if <code>commit()</code> has been called.
	 * 
	 * @return <code>true</code> if <code>commit()</code> has been called
	 */
	public boolean isCommitCalled() {
		return testConnection.isCommitCalled();
	}

	/**
	 * Return <code>true</code> if <code>rollback()</code> has been called.
	 * 
	 * @return <code>true</code> if <code>rollback()</code> has been called
	 */
	public boolean isRollbackCalled() {
		return testConnection.isRollbackCalled();
	}

	/**
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the current <code>autoCommit</code> setting.
	 * 
	 * @return the current <code>autoCommit</code> setting
	 * @throws SQLException
	 */
	public boolean isAutoCommit() throws SQLException {
		return testConnection.getAutoCommit();
	}

	/**
	 * Sets the specified column value in the specified row of the test result set.
	 * 
	 * @param row
	 * @param columnName
	 * @param value
	 */
	public void setResultValue(final int row, final String columnName, final Object value) {
		testConnection.setResultValue(row, columnName, value);
	}

	/**
	 * Returns <code>true</code> if <code>executeUpdate()</code> was called.
	 * 
	 * @return <code>true</code> if <code>executeUpdate()</code> was called
	 */
	public boolean isExecuteUpdateCalled() {
		return testConnection.isExecuteUpdateCalled();
	}
}
