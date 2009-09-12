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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Processes a {@link ResultSet} from a SQL query, returning a {@link QueryResult} object.
 * 
 * @param <T> the type of data returned by this processor
 */
public interface ResultProcessor<T> {
	/**
	 * Processes a {@link ResultSet}.
	 * 
	 * @param resultSet the result set to process
	 * @return the product of processing the result set
	 * @throws SQLException
	 */
	QueryResult<T> processResultSet(ResultSet resultSet) throws SQLException;
}
