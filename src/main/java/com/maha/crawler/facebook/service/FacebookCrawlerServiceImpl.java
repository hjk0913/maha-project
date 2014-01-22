package com.maha.crawler.facebook.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.util.StringUtil;
import com.maha.crawler.facebook.dao.FacebookCrawlerDAO;

import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;

@Service
public class FacebookCrawlerServiceImpl implements FacebookCrawlerService {
	
	@Resource
	private FacebookCrawlerDAO facebookCrawlerDAO;

	@SuppressWarnings("unchecked")
	public void insertDefiniteData(BoxParam param) {
		User definiteData = (User) param.get("definiteData");
		
		param.put("data", definiteData.getId());
		param.put("dataType", "ID");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getFirstName());
		param.put("dataType", "FIRSTNAME");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getMiddleName());
		param.put("dataType", "MIDDLENAME");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getLastName());
		param.put("dataType", "LASTNAME");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getBirthday());
		param.put("dataType", "BIRTHDAY");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getEmail());
		param.put("dataType", "EMAIL");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getName());
		param.put("dataType", "NAME");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getUsername());
		param.put("dataType", "USERNAME");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);

		param.put("data", definiteData.getGender());
		param.put("dataType", "GENDER");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getReligion());
		param.put("dataType", "RELIGION");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getWebsite());
		param.put("dataType", "WEBSITE");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
		param.put("data", definiteData.getRelationshipStatus());
		param.put("dataType", "RELATIONSHIPSTATUS");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  facebookCrawlerDAO.insertDefiniteData(param);
		
	}
	
	@SuppressWarnings("unchecked")
	public void insertNonDefiniteData(BoxParam param) throws Exception {
		ResponseList<Post> feedList = (ResponseList<Post>) param.get("feedList");
		for(int i = 0; i < feedList.size(); i++){
			param.put("data", feedList.get(i).getId());
			param.put("dataType", "ID");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
			
			param.put("data", feedList.get(i).getMessage());
			param.put("dataType", "MESSAGE");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
			
			param.put("data", feedList.get(i).getCaption());
			param.put("dataType", "CAPTION");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
			
			param.put("data", feedList.get(i).getDescription());
			param.put("dataType", "DESCRIPTION");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);

			param.put("data", feedList.get(i).getType());
			param.put("dataType", "TYPE");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
			
			param.put("data", feedList.get(i).getStory());
			param.put("dataType", "STORY");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
			
			param.put("data", feedList.get(i).getCreatedTime());
			param.put("dataType", "CREATEDTIME");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);

			param.put("data", StringUtil.nvl(feedList.get(i).getLink()).toString());
			param.put("dataType", "URL");
			if(!StringUtil.nvl(param.getString("data")).equals("")) 
				facebookCrawlerDAO.insertNonDefiniteData(param);
		}
	}
}
	
