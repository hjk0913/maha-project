package com.maha.crawler.twitter.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bzfree.bzekbox.data.BoxParam;
import com.bzfree.bzekbox.util.BoxUtil;
import com.maha.common.util.StringUtil;
import com.maha.crawler.facebook.service.FacebookCrawlerService;
import com.maha.crawler.google.service.GoogleCrawlerService;
import com.maha.crawler.twitter.service.TwitterCrawlerService;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
@RequestMapping(value = "/twitter/")
public class TwitterCrawlerController {
	
	@Resource
    private TwitterCrawlerService twitterCrawlerService;
	
	@Resource
	private GoogleCrawlerService googleCrawlerService;
	
	@RequestMapping(value = "signin.do")
	public ModelAndView goRegedit(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/twitter");
		return mav;
	}
	
	@RequestMapping(value = "submit.do")
	public ModelAndView getUserData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TwitterException{
		ModelAndView mav = new ModelAndView();

		Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("7gepsv3kmDt6eVbAnkA", "MgrUz8bT1Wf9FDFdpmSkwa9K40EV2WanlR0CFg85F8A");

        RequestToken requestToken = null;
        requestToken = twitter.getOAuthRequestToken();

        request.getSession().setAttribute("twitterToken", requestToken);
        request.getSession().setAttribute("twitterSecretToken", requestToken.getTokenSecret());
		
        String authUrl = requestToken.getAuthorizationURL();
        mav.setViewName("redirect:" + authUrl);
        
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "callback.do")
	public ModelAndView getCallBackData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		BoxParam param = BoxUtil.getBox(request);
		param.put("serviceId", "T");
		param.put("userSeq", request.getSession().getAttribute("userSeq"));
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("7gepsv3kmDt6eVbAnkA", "MgrUz8bT1Wf9FDFdpmSkwa9K40EV2WanlR0CFg85F8A");
		AccessToken accessToken = null;
		RequestToken requestToken = (RequestToken )request.getSession().getAttribute("twitterToken");

		String oauth_verifier= request.getParameter("oauth_verifier");
		 
		accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
		twitter.setOAuthAccessToken(accessToken);
		
		// 사용자 계정정보 입력
		param.put("gid", StringUtil.nvl(twitter.getScreenName()));
		googleCrawlerService.regeditUserAccount(param);
		
		// 사용자 정형 데이터 입력

		User definiteData = twitter.showUser(twitter.getScreenName());
		param.put("definiteData", definiteData);
		twitterCrawlerService.insertDefiniteData(param);
		
		ResponseList<Status> timeLine = twitter.getHomeTimeline();
		param.put("timeLine", timeLine);
		twitterCrawlerService.insertNonDefiniteData(param);
		
        mav.setViewName("/finish");
        
		return mav;
	}

}
