package edu.gatech.cs6400.team81.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.RequestedItem;
import edu.gatech.cs6400.team81.model.RequestedItemStatus;

public abstract class BaseDAO<RESULT> {
	
	@Autowired
	private DataSource dataSource;

	protected abstract RESULT mapResult(ResultSet rs) throws SQLException;

	protected RESULT getUnique(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			RESULT result = null;
			if(rs.next()){
				result = mapResult(rs);
			}
			
			return result;
		} finally {
			close(conn, stmnt, rs);
		}
	}
	
	protected int getCount(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try{
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			
			rs = stmnt.executeQuery();
			
			System.out.println(sql);
			
			int count=0;
			
			while(rs.next()){
				count++;
			}
			
			return count;
			
		} finally {
			close(conn, stmnt, rs);
		}
	}
	
	protected boolean exists(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);
			
			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			
			try{
				if (stmnt.execute(sql)){
					return true;
				} else {
					return false;
				}
				
//				boolean exists = stmnt.execute(sql);				
//				return exists;
				
			} catch (SQLException e){
				e.printStackTrace();
				//saveErrors(request, new String[]{e.toString()});
				return false;
			}
		
			//return exists;
			
		} finally {
			close(conn, stmnt, rs);
		}
	}

	protected List<RESULT> getMultiple(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			List<RESULT> multiples = new ArrayList<RESULT>();
			while(rs.next()){
				RESULT result = mapResult(rs);
				multiples.add(result);
			}
			
			return multiples;
		} finally {
			close(conn, stmnt, rs);
		}
	}
	
	protected List<Integer> getIntegers(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			
			List<Integer> multiples = new ArrayList<Integer>();
			
			while(rs.next()){
				multiples.add(rs.getInt(1));
			}
			
			return multiples;
		} finally {
			close(conn, stmnt, rs);
		}
	}		
		
	protected List<RequestedItem> getRequestedItems(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();			
			
			List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();			
			
			while(rs.next()){
				
				RequestedItem reqItem = new RequestedItem();
				
				reqItem.setItemId(rs.getInt("ItemId"));
				reqItem.setRequesteeSiteId(rs.getInt("RequesteeSiteId"));
				reqItem.setReqDateTime(rs.getTimestamp("ReqDateTime"));
				reqItem.setUserId(rs.getString("UserId"));
				
				//RequestedItemStatus RIS = (RequestedItemStatus) rs.getObject("Status");
				
				//reqItem.setStatus(RequestedItemStatus.valueOf("Status"));
				reqItem.setStrStatus(rs.getString("Status"));
				
				reqItem.setNumRequested(rs.getInt("NumRequested"));
				reqItem.setNumFilled(rs.getInt("NumFilled"));
							
				requestedItems.add(reqItem);		
									
			}			
			
			return requestedItems;
			
		} finally {
			close(conn, stmnt, rs);
		}
	}			

	protected Item getMatchingRequestedItems(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			
			Item myItem = new Item();
			
			if (rs.next()){								
				
				myItem.setName(rs.getString("Name"));
				myItem.setSiteId(rs.getInt("SiteId"));
				myItem.setNumberUnits(rs.getInt("NumberUnits"));
			}
			
			return myItem;
			
		} finally {
			close(conn, stmnt, rs);
		}	
		
	}
	
	protected int[] getIntResult(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			int[] result = null;
			if(rs.next()){
				ResultSetMetaData metaData = rs.getMetaData();
				int columns = metaData.getColumnCount();

				result = new int[columns];
				for (int i = 0; i < columns; i++) {
                    // Columns index start at 1 thus plus 1
                    result[i] = rs.getInt(i + 1);
				}
			}
			
			return result;
		} finally {
			close(conn, stmnt, rs);
		}
	}
	
	protected List<Map<String, Object>> getResult(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			rs = stmnt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while(rs.next()){
				Map<String, Object> row = new HashMap<String, Object>();
				result.add(row);
				
				for (int i = 1; i <= columns; i++) {
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(columnName);
					row.put(columnName, value);
				}
			}
			
			return result;
		} finally {
			close(conn, stmnt, rs);
		}
	}
	
	protected int execute(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		int result = 0;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			result = stmnt.executeUpdate();

			return result;
		} finally {
			close(conn, stmnt);
		}
	}

	protected Integer executeForGeneratedKey(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement stmnt = null;
		Integer result = null;
		try {
			conn = dataSource.getConnection();
			stmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int parameterIndex = 0;
			for (Object param : params) {
				stmnt.setObject(++parameterIndex, param);
			}
			result = stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys();
			if(rs.next()){
				result = rs.getInt(1);
			}
			return result;
		} finally {
			close(conn, stmnt);
		}
	}
	
	private void close(Connection conn, Statement stmnt) {
		close(conn, stmnt, null);
	}
	
	private void close(Connection conn, Statement stmnt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmnt != null) {
			try {
				stmnt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
