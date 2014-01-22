package com.maha.crawler.facebook.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.dao.CommonDAO;
import com.maha.common.dao.NameSpace;
import com.maha.common.util.StringUtil;

import facebook4j.User;

@NameSpace("facebookCrawler")
@Repository("facebookCrawlerDAO")
public class FacebookCrawlerDAO extends CommonDAO {
	
	public void insertDefiniteData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertDefiniteData", param);
	}
	

	public void insertNonDefiniteData(BoxParam param) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".insertNonDefiniteData", param);
	}
}

