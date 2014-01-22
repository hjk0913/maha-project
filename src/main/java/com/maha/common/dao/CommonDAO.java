package com.maha.common.dao;

import javax.annotation.Resource;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapClient;

public class CommonDAO extends SqlMapClientDaoSupport implements DAO {
	
	private String nameSpace;
	
	public String getNameSpace(){
		return nameSpace;
	}
	
	public void setNameSpace(String nameSpace) {
		
		this.nameSpace = nameSpace;
		logger.debug("namespace - "+ this.nameSpace);
		
	}
	
	private void setNameSpace() throws Exception{
		
		Class thisClass = getClass();
		
		NameSpace nameSpaceAnnotation = (NameSpace) thisClass.getAnnotation(NameSpace.class);
		
		if ( nameSpaceAnnotation == null) {
			throw new Exception("nameSpace annotation is null");
		}
		
		String nameSpace = nameSpaceAnnotation.value();

		setNameSpace( nameSpace );
		
	}
	
	public void nameSpaceSetting(){
		try {
			setNameSpace();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public CommonDAO() {
		nameSpaceSetting();
	}

	@Resource(name = "sqlMapClient") 
	public void setSuperSqlMapClient(SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	} 

}
