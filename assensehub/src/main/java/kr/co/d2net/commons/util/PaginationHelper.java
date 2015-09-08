package kr.co.d2net.commons.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * <p></p>
 * @author Kang Myeong Seong
 * <pre>
 * </pre>
 */
public class PaginationHelper<E> {
	private static Log logger = LogFactory.getLog(PaginationHelper.class);
	
	public PaginationSupport<E> fetchPage(            
			final NamedParameterJdbcTemplate jt,            
			final String sqlCountRows,            
			final String sqlFetchRows,            
			final Map<String, Object> args,            
			final int pageNo,            
			final int pageSize,            
			final BeanPropertyRowMapper<E> rowMapper) {
		
		return fetchPage(jt, sqlCountRows, sqlFetchRows, args, pageNo, pageSize, PaginationSupport.PAGESIZE, rowMapper);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PaginationSupport<E> fetchPage(            
			final NamedParameterJdbcTemplate jt,            
			final String sqlCountRows,            
			final String sqlFetchRows,            
			final Map<String, Object> args,            
			final int pageNo,            
			final int pageSize,
			final int pageIndexCount,
			final BeanPropertyRowMapper<E> rowMapper) { // determine how many rows are available

		// create the page object        
		final PaginationSupport<E> ps = new PaginationSupport<E>();
		ps.setStartIndex(pageNo);
		ps.setPageIndexCount(pageIndexCount);
		ps.setPageSize(pageSize);
		
		// calculate the number of pages
		final int rowCount = jt.queryForInt(sqlCountRows, args);      
		ps.setTotalCount(rowCount);
		if (logger.isDebugEnabled()) {
			logger.debug("Count Query: "+sqlCountRows);
			logger.debug("Tag Search Result TotalCount: "+rowCount);
		}
		
		int pageCount = rowCount / pageSize;        
		if (rowCount > pageSize * pageCount) {            
			pageCount++;        
		}        
		ps.setPageCount(pageCount);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch Query: "+sqlFetchRows);
		}
		final int startRow = (pageNo - 1) * pageSize;       
		ps.setStartIndex(pageNo);
		jt.query(                
				sqlFetchRows,         
				args,                
				new ResultSetExtractor() {                    
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {                        
						final List pageItems = ps.getItems();  
						int currentRow = 0;                       
						while (rs.next() && currentRow < startRow + pageSize) {                            
							if (currentRow >= startRow) {                                
								pageItems.add(rowMapper.mapRow(rs, currentRow));                            
							}                            
							currentRow++;                        
						}    
						return ps;                    
					}                
				});        
		return ps;    
	}
}
