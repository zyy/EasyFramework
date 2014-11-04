/**
 * Copyright (c) 2011-2014, yycoder 692895299@qq.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.easy.plugin.activerecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Cross Package Invoking pattern for package activerecord.
 */
public abstract class CPI {
	
	/**
	 * Return the attributes map of the model
	 * @param model the model extends from class Model
	 * @return the attributes map of the model
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static final Map<String, Object> getAttrs(Model model) {
		return model.getAttrs();
	}
	
	public static <T> List<T> query(Connection conn, String sql, Object... paras) throws SQLException {
		return Db.query(DbUtil.config, conn, sql, paras);
	}
	
	public static <T> List<T> query(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return Db.query(DbUtil.getConfig(configName), conn, sql, paras);
	}
	
	/**
	 * Return the columns map of the record
	 * @param record the Record object
	 * @return the columns map of the record
	public static final Map<String, Object> getColumns(Record record) {
		return record.getColumns();
	} */
	
	public static List<Record> find(Connection conn, String sql, Object... paras) throws SQLException {
		return Db.find(DbUtil.config, conn, sql, paras);
	}
	
	public static List<Record> find(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return Db.find(DbUtil.getConfig(configName), conn, sql, paras);
	}
	
	public static Page<Record> paginate(Connection conn, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws SQLException {
		return Db.paginate(DbUtil.config, conn, pageNumber, pageSize, select, sqlExceptSelect, paras);
	}
	
	public static Page<Record> paginate(String configName, Connection conn, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws SQLException {
		return Db.paginate(DbUtil.getConfig(configName), conn, pageNumber, pageSize, select, sqlExceptSelect, paras);
	}
	
	public static int update(Connection conn, String sql, Object... paras) throws SQLException {
		return Db.update(DbUtil.config, conn, sql, paras);
	}
	
	public static int update(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return Db.update(DbUtil.getConfig(configName), conn, sql, paras);
	}
}

