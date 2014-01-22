package com.maha.crawler.twitter.dao;

import org.springframework.stereotype.Repository;

import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.dao.CommonDAO;
import com.maha.common.dao.NameSpace;

@NameSpace("twitterCrawler")
@Repository("twitterCrawlerDAO")
public class TwitterCrawlerDAO extends CommonDAO {
	
	public void insertDefiniteData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertDefiniteData", param);
	}
	
	public void insertNonDefiniteData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertNonDefiniteData", param);
	}
	
}

