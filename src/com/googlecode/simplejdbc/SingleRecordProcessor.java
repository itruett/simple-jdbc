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
 * A {@link ResultProcessor} that processes a single record from a {@link ResultSet}.
 * 
 * @param <T> the data type produced by the processor
 */
public interface SingleRecordProcessor<T> extends ResultProcessor<T> {
	/**
	 * Process a single row in the {@link ResultSet}. The result set is assumed to be on a valid
	 * row. This method must not call <code>next()</code> on the result set.
	 * 
	 * @param resultSet the result set to process
	 * @return the value produced by this processor
	 * @throws SQLException
	 */
	T processRow(ResultSet resultSet) throws SQLException;
}
