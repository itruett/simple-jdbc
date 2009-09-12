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

import javax.sql.DataSource;

import org.junit.Before;

/**
 * Base class for SimpleJDBC unit tests.
 */
public class SimpleJdbcTest {
	/**
	 * {@link DataSource} for testing.
	 */
	protected TestDataSource testDataSource;

	/**
	 * {@link QueryRunner} for testing.
	 */
	protected TestQueryRunner runner;

	/**
	 * Setup the test data source and query runner.
	 */
	@Before
	public void setup() {
		testDataSource = new TestDataSource();
		runner = new TestQueryRunner(testDataSource);
	}
}
