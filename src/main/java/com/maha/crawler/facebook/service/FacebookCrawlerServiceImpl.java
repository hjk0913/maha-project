package com.maha.crawler.facebook.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.util.StringUtil;
import com.maha.crawler.facebook.dao.FacebookCrawlerDAO;
import com.maha.crawler.google.service.GoogleCrawlerServiceImpl;

import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;

@Service
public class FacebookCrawlerServiceImpl implements FacebookCrawlerService {
	
	private static Logger logger = Logger.getLogger(FacebookCrawlerServiceImpl.class);
	
	@Resource
	private FacebookCrawlerDAO facebookCrawlerDAO;

	@SuppressWarnings("unchecked")
	public void insertDefiniteData(BoxParam param) {
		User definiteData = (User) param.get("definiteData");
		
		param.put("data", definiteData.getId());
		param.put("dataType", "ID");
		if(!StringUtil.nvl(param.getString("data")).equals(""))  {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getFirstName());
		param.put("dataType", "FIRSTNAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getMiddleName());
		param.put("dataType", "MIDDLENAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getLastName());
		param.put("dataType", "LASTNAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getBirthday());
		param.put("dataType", "BIRTHDAY");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getEmail());
		param.put("dataType", "EMAIL");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getName());
		param.put("dataType", "NAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getUsername());
		param.put("dataType", "USERNAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}

		param.put("data", definiteData.getGender());
		param.put("dataType", "GENDER");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getReligion());
		param.put("dataType", "RELIGION");
		if(!StringUtil.nvl(param.getString("data")).equals("")) { 
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getWebsite());
		param.put("dataType", "WEBSITE");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getRelationshipStatus());
		param.put("dataType", "RELATIONSHIPSTATUS");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
			facebookCrawlerDAO.insertDefiniteData(param);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void insertNonDefiniteData(BoxParam param) throws Exception {
		
		facebookCrawlerDAO.insertNonDefiniteData(param);
		
		/*
		// feedList
		ResponseList<Post> feedList = (ResponseList<Post>) param.get("feedList");
		for(int i = 0; i < feedList.size(); i++){
			param.put("data", feedList.get(i).getId());
			param.put("dataType", "ID");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", feedList.get(i).getMessage());
			param.put("dataType", "MESSAGE");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", feedList.get(i).getCaption());
			param.put("dataType", "CAPTION");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", feedList.get(i).getDescription());
			param.put("dataType", "DESCRIPTION");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}

			param.put("data", feedList.get(i).getType());
			param.put("dataType", "TYPE");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", feedList.get(i).getStory());
			param.put("dataType", "STORY");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", feedList.get(i).getCreatedTime());
			param.put("dataType", "CREATEDTIME");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}

			param.put("data", StringUtil.nvl(feedList.get(i).getLink()).toString());
			param.put("dataType", "URL");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("facebook::  " + param.getString("dataType") + "::" + param.getString("data"));
				facebookCrawlerDAO.insertNonDefiniteData(param);
			}
		}
		 */
	}
}
	
