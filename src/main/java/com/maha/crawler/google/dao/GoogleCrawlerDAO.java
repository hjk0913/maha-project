package com.maha.crawler.google.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.bzfree.bzekbox.data.Box;
import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.dao.CommonDAO;
import com.maha.common.dao.NameSpace;
import com.maha.crawler.google.controller.GoogleCrawlerController;

@NameSpace("googleCrawler")
@Repository("googleCrawlerDAO")
public class GoogleCrawlerDAO extends CommonDAO {
	
	
	
	public Box getUserData(BoxParam param) {
		return (Box) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getUserData", param);
	}
	
	public int regeditUser(BoxParam param) {
		return (Integer) getSqlMapClientTemplate().insert(getNameSpace() + ".regeditUser", param);
	}
	
	public void regeditUserAccount(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".regeditUserAccount", param);
	}
	
	public void insertGoogleData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertGoogleData", param);
	}
	
	
	public void insertNonDefiniteData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertNonDefiniteData", param);
	}
	
}

