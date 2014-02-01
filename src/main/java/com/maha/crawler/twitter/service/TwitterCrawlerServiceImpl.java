package com.maha.crawler.twitter.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;

import com.bzfree.bzekbox.data.BoxParam;
import com.maha.common.util.StringUtil;
import com.maha.crawler.facebook.service.FacebookCrawlerServiceImpl;
import com.maha.crawler.twitter.dao.TwitterCrawlerDAO;


@Service
public class TwitterCrawlerServiceImpl implements TwitterCrawlerService {
	
	private static Logger logger = Logger.getLogger(TwitterCrawlerServiceImpl.class);
	
	@Resource
	private TwitterCrawlerDAO twitterCrawlerDAO;

	@SuppressWarnings("unchecked")
	public void insertDefiniteData(BoxParam param) {
		User definiteData = (User) param.get("definiteData");
		
		param.put("data", definiteData.getId());
		param.put("dataType", "ID");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getScreenName());
		param.put("dataType", "SCREENNAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getName());
		param.put("dataType", "NAME");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getLang());
		param.put("dataType", "LANGUAGE");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getDescription());
		param.put("dataType", "DESCRIPTION");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getLocation());
		param.put("dataType", "LOCATION");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getURL());
		param.put("dataType", "URL");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
		param.put("data", definiteData.getCreatedAt().toString());
		param.put("dataType", "CREATEDAT");
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
			twitterCrawlerDAO.insertDefiniteData(param);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void insertNonDefiniteData(BoxParam param) throws Exception {
		twitterCrawlerDAO.insertNonDefiniteData(param);
		/*
		ResponseList<Status> timeLine = (ResponseList<Status>) param.get("timeLine");
		for(int i = 0; i < timeLine.size(); i++){
			
			param.put("data", timeLine.get(i).getId());
			param.put("dataType", "ID");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
				twitterCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", timeLine.get(i).getText());
			param.put("dataType", "TEXT");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
				twitterCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", timeLine.get(i).getSource());
			param.put("dataType", "SOURCE");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
				twitterCrawlerDAO.insertNonDefiniteData(param);
			}
			
			param.put("data", StringUtil.nvl(timeLine.get(i).getCreatedAt()).toString());
			param.put("dataType", "CREATEDAT");
			if(!StringUtil.nvl(param.getString("data")).equals("")) {
				logger.info("twitter::  " + param.getString("dataType") + "::" + param.getString("data"));
				twitterCrawlerDAO.insertNonDefiniteData(param);
			}
		}
		*/
	}
}
	
