/*
 * Copyright (c) 2008-2016 Computer Network Information Center (CNIC), Chinese Academy of Sciences.
 * 
 * This file is part of Duckling project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 */
package net.duckling.dchat.rtp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.duckling.dchat.rtp.domain.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RtpLevelDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Level> levelMapper = new RowMapper<Level>(){

		@Override
		public Level mapRow(ResultSet rs, int rowNum) throws SQLException {
			Level l = new Level();
			l.setDefault(rs.getBoolean("isDefault"));
			l.setLevelID(rs.getInt("levelID"));
			l.setParentID(rs.getInt("parentID"));
			l.setLevelName(rs.getString("levelName"));
			l.setIcon(rs.getString("icon"));
			return l;
		}
		
	};
	
	public Level query(int levelID){
		String sql = "select * from rtplevel where levelID = ?";
		List<Level> list = jdbcTemplate.query(sql, new Object[]{},levelMapper);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	public int query(int levelIndex, boolean isVisible) {
		String levelName = ((isVisible) ? "visible" : "hidden") + levelIndex;
		String sql = "select * from rtplevel where levelName = ?";
		List<Level> list = jdbcTemplate.query(sql, new Object[]{levelName},levelMapper);
		if(list!= null && list.size() > 0 ){
			return list.get(0).getLevelID();
		}
		return 0;
	}

}
