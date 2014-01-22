package com.maha.crawler.facebook.controller;

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

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;

@Controller
@RequestMapping(value = "/facebook/")
public class FacebookCrawlerController {
	
	@Resource
    private FacebookCrawlerService facebookCrawlerService;
	
	@Resource
	private GoogleCrawlerService googleCrawlerService;
	
	@RequestMapping(value = "signin.do")
	public ModelAndView goRegedit() throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/facebook");
		return mav;
	}
	
	@RequestMapping(value = "submit.do")
	public void getUserData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Facebook facebook = new FacebookFactory().getInstance();
        
        facebook.setOAuthAppId("235862589916712", "3bf8c2bc25b31846532609d1ee52b728");
        facebook.setOAuthPermissions("email, publish_actions,read_stream");
       
        request.getSession().setAttribute("facebook", facebook);
        StringBuffer callbackURL = request.getRequestURL();
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "").append("/callback.do");
        response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "callback.do")
	public ModelAndView getCallBackData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		BoxParam param = BoxUtil.getBox(request);
		param.put("serviceId", "F");
		param.put("userSeq", request.getSession().getAttribute("userSeq"));
		
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String oauthCode = request.getParameter("code");
		facebook.getOAuthAccessToken(oauthCode);
		
		
		User definiteData = facebook.getMe();
		param.put("definiteData", definiteData);
		
		// 사용자 계정정보 입력
		param.put("gid", StringUtil.nvl(definiteData.getEmail()));
		googleCrawlerService.regeditUserAccount(param);
				
				
		// 사용자 정형 데이터 입력
		facebookCrawlerService.insertDefiniteData(param);
		
		// 비정형 데이터 입력
		ResponseList<Post> feedList = facebook.getFeed();
		param.put("feedList", feedList);
		facebookCrawlerService.insertNonDefiniteData(param);
		
		/*
		ResponseList<Post> gh = facebook.getHome();
		for(int i = 0; i < gh.size(); i++){
			gh.get(i).getName();
			gh.get(i).getMessage();
			gh.get(i).getDescription();
			gh.get(i).getCaption();
			gh.get(i).getId();
			gh.get(i).getStatusType();
		}
		*/
		
		//Close처리
		
		mav.setViewName("/twitter");
		
		return mav;
	}
	

}
