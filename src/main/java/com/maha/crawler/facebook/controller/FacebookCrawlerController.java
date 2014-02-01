package com.maha.crawler.facebook.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bzfree.bzekbox.data.BoxParam;
import com.bzfree.bzekbox.util.BoxUtil;
import com.maha.common.util.StringUtil;
import com.maha.crawler.facebook.service.FacebookCrawlerService;
import com.maha.crawler.facebook.service.FacebookCrawlerServiceImpl;
import com.maha.crawler.google.service.GoogleCrawlerService;

import facebook4j.Achievement;
import facebook4j.Activity;
import facebook4j.Album;
import facebook4j.Book;
import facebook4j.Comment;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Group;
import facebook4j.IdNameEntity;
import facebook4j.Like;
import facebook4j.Location;
import facebook4j.Milestone;
import facebook4j.Movie;
import facebook4j.Music;
import facebook4j.Note;
import facebook4j.PagableList;
import facebook4j.Page;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.Post.Action;
import facebook4j.ResponseList;
import facebook4j.Television;
import facebook4j.User;

@Controller
@RequestMapping(value = "/facebook/")
public class FacebookCrawlerController {
	
	private static Logger logger = Logger.getLogger(FacebookCrawlerController.class);
	
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
        facebook.setOAuthPermissions("email, publish_actions, publish_stream, user_likes, friends_likes, read_stream");
       
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
		//param.put("userSeq", "10000001");
		
		Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
		String oauthCode = request.getParameter("code");
		facebook.getOAuthAccessToken(oauthCode);
		
		
		User definiteData = facebook.getMe();
		param.put("definiteData", definiteData);
		
		// 사용자 계정정보 입력
		param.put("gid", StringUtil.nvl(definiteData.getEmail()));
		googleCrawlerService.regeditUserAccount(param);
		
//		
//		System.out.println("===========================    getInterestedIn    ==========================");
//		List<String> ii = definiteData.getInterestedIn();
//		for(int i = 0; i < ii.size(); i++){
//			System.out.println(i);
//		}
		
		
		// 사용자 정형 데이터 입력
		facebookCrawlerService.insertDefiniteData(param);
		// 비정형 데이터 입력
		// 피드리스트
		logger.info("=============================    feed    ===================================");
		ResponseList<Post> feed = facebook.getFeed();
		for(int i = 0; i < feed.size(); i++){
			param.put("data", feed.get(i).getId());
			param.put("dataType", "FEED_ID");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getMessage());
			param.put("dataType", "FEED_MESSAGE");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getCaption());
			param.put("dataType", "FEED_CAPTION");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getDescription());
			param.put("dataType", "FEED_DESCRIPTION");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getType());
			param.put("dataType", "FEED_TYPE");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getStory());
			param.put("dataType", "FEED_STORY");
			insertNonDefiniteData(param);
			
			param.put("data", feed.get(i).getCreatedTime());
			param.put("dataType", "FEED_CREATEDTIME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(feed.get(i).getLink()).toString());
			param.put("dataType", "FEED_URL");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(feed.get(i).getStatusType()));
			param.put("dataType", "FEED_STATUS_TYPE");
			insertNonDefiniteData(param);
			logger.info("=============================    feed comments    ===================================");
			ResponseList<Comment> comments = facebook.getPostComments(feed.get(i).getId());
			for(int q = 0; q < comments.size(); q++){
				param.put("data", StringUtil.nvl(comments.get(q).getId()));
				param.put("dataType", "COMMENTS_ID");
				insertNonDefiniteData(param);
				
				param.put("data", StringUtil.nvl(comments.get(q).getMessage()));
				param.put("dataType", "COMMENTS_MESSAGE");
				insertNonDefiniteData(param);
				
				param.put("data", StringUtil.nvl(comments.get(q).getFrom().getId()));
				param.put("dataType", "COMMENTS_FROM_ID");
				insertNonDefiniteData(param);
				
				param.put("data", StringUtil.nvl(comments.get(q).getFrom().getName()));
				param.put("dataType", "COMMENTS_FROM_NAME");
				insertNonDefiniteData(param);
				
				param.put("data", StringUtil.nvl(comments.get(q).getLikeCount()));
				param.put("dataType", "COMMENTS_LIKE_COMMENT");
				insertNonDefiniteData(param);
			}
			
			logger.info("=============================    like feed    ===================================");
			for(int p = 0; p < feed.size(); p++){
				if(p > 0) break;
				PagableList<Like> likeFeed = feed.get(i).getLikes();
				for(int j = 0; j < likeFeed.size(); j++){
					ResponseList<Post> likefeedContent = facebook.getFeed(likeFeed.get(j).getId());
					for(int k = 0; k < likefeedContent.size(); k++){

						param.put("data", StringUtil.nvl(likefeedContent.get(k).getId()));
						param.put("dataType", "LIKE_FEED_ID");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getStory()));
						param.put("dataType", "LIKE_FEED_STORY");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getMessage()));
						param.put("dataType", "LIKE_FEED_MESSAGE");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getDescription()));
						param.put("dataType", "LIKE_FEED_DESCRIPTION");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getType()));
						param.put("dataType", "LIKE_FEED_TYPE");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getStatusType()));
						param.put("dataType", "LIKE_FEED_STATUSTYPE");
						insertNonDefiniteData(param);
						
						param.put("data", StringUtil.nvl(likefeedContent.get(k).getSource()));
						param.put("dataType", "LIKE_FEED_SOURCE");
						insertNonDefiniteData(param);
					}
					
				}
			}
			
		}
		
		
		
		// 좋아요 영화
		logger.info("=============================    like movie    ===================================");
		ResponseList<Movie> movies = facebook.getMovies();
		for(int i = 0; i < movies.size(); i++){
			param.put("data", StringUtil.nvl(movies.get(i).getId()));
			param.put("dataType", "LIKE_MOVIE_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(movies.get(i).getName()));
			param.put("dataType", "LIKE_MOVIE_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(movies.get(i).getCategory()));
			param.put("dataType", "LIKE_MOVIE_CATEGORY");
			insertNonDefiniteData(param);
		}
		 
		 // 좋아요 tv
		logger.info("=============================    like TV    ===================================");
		ResponseList<Television> tv = facebook.getTelevision();
		for(int i = 0; i < tv.size(); i++){
			param.put("data", StringUtil.nvl(tv.get(i).getId()));
			param.put("dataType", "LIKE_TV_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(tv.get(i).getName()));
			param.put("dataType", "LIKE_TV_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(tv.get(i).getCategory()));
			param.put("dataType", "LIKE_TV_CATEGORY");
			insertNonDefiniteData(param);
		}
		 
		// 좋아요 책
		logger.info("=============================    like BOOK    ===================================");
		ResponseList<Book> book = facebook.getBooks();
		for(int i = 0; i < book.size(); i++){
			param.put("data", StringUtil.nvl(book.get(i).getId()));
			param.put("dataType", "LIKE_BOOK_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(book.get(i).getName()));
			param.put("dataType", "LIKE_BOOK_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(book.get(i).getCategory()));
			param.put("dataType", "LIKE_BOOK_CATEGORY");
			insertNonDefiniteData(param);
		}
		
		// 좋아요 음악
		logger.info("=============================    like MUSIC    ===================================");
		ResponseList<Music> music = facebook.getMusic();
		for(int i = 0; i < music.size(); i++){
			param.put("data", StringUtil.nvl(music.get(i).getId()));
			param.put("dataType", "LIKE_MUSIC_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(music.get(i).getName()));
			param.put("dataType", "LIKE_MUSIC_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(music.get(i).getCategory()));
			param.put("dataType", "LIKE_MUSIC_CATEGORY");
			insertNonDefiniteData(param);
		}
		
		logger.info("=============================    like ALBUM    ===================================");
		ResponseList<Album> album = facebook.getAlbums();
		for(int i = 0; i < album.size(); i++){
			param.put("data", StringUtil.nvl(album.get(i).getId()));
			param.put("dataType", "LIKE_ALBUM_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(album.get(i).getName()));
			param.put("dataType", "LIKE_ALBUM_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(album.get(i).getDescription()));
			param.put("dataType", "LIKE_ALBUM_DESCRIPTION");
			insertNonDefiniteData(param);
		}
		
		logger.info("=============================    group    ===================================");
		ResponseList<Group> group = facebook.getGroups();
		for(int i = 0; i < group.size(); i++){
			param.put("data", StringUtil.nvl(group.get(i).getId()));
			param.put("dataType", "GROUP_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(group.get(i).getName()));
			param.put("dataType", "GROUP_NAME");
			insertNonDefiniteData(param);
		}
		
		
		logger.info("=============================    location    ===================================");
		ResponseList<Location> location = facebook.getLocations();
		for(int i = 0; i < location.size(); i++){
			param.put("data", StringUtil.nvl(location.get(i).getId()));
			param.put("dataType", "LOCATION_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(location.get(i).getType()));
			param.put("dataType", "LOCATION_TYPE");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(location.get(i).getCreatedTime().toString()));
			param.put("dataType", "LOCATION_CREATEDTIME");
			insertNonDefiniteData(param);
			
			PagableList<IdNameEntity> tags = location.get(i).getTags();
			for(int j = 0; j < tags.size(); j++){
				param.put("data", StringUtil.nvl(tags.get(j).getId()));
				param.put("dataType", "LOCATION_TAGS_ID");
				insertNonDefiniteData(param);
				
				param.put("data", StringUtil.nvl(tags.get(j).getName()));
				param.put("dataType", "LOCATION_TAGS_NAME");
				insertNonDefiniteData(param);
			}
			
		}
		
		logger.info("=============================    event    ===================================");
		ResponseList<Event> event = facebook.getEvents();
		for(int i = 0; i < event.size(); i++){
			param.put("data", StringUtil.nvl(event.get(i).getId()));
			param.put("dataType", "EVENT_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(event.get(i).getDescription()));
			param.put("dataType", "EVENT_DESCRIPTION");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(event.get(i).getLocation()));
			param.put("dataType", "EVENT_LOCATION");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(event.get(i).getOwner()));
			param.put("dataType", "EVENT_OWNER");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(event.get(i).getStartTime()));
			param.put("dataType", "EVENT_STARTTIME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(event.get(i).getEndTime()));
			param.put("dataType", "EVENT_ENDTIME");
			insertNonDefiniteData(param);
		}
		
		logger.info("=============================    Activities    ===================================");
		ResponseList<Activity> acvities = facebook.getActivities();
		for(int i = 0; i < acvities.size(); i++){
			param.put("data", StringUtil.nvl(acvities.get(i).getId()));
			param.put("dataType", "ACTIVITIES_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(acvities.get(i).getName()));
			param.put("dataType", "ACTIVITIES_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(acvities.get(i).getCategory()));
			param.put("dataType", "ACTIVITIES_CATEGORY");
			insertNonDefiniteData(param);
		}
		
		// json타입으로 넘어옴
		System.out.println("===========================    friends    ==========================");
		ResponseList<Friend> friends = facebook.getFriends();
		for(int p = 0; p < friends.size(); p++){
			System.out.println(friends.get(p));
		}
		
		logger.info("=============================    NOTE    ===================================");
		ResponseList<Note> note = facebook.getNotes();
		for(int i = 0; i < note.size(); i++){
			param.put("data", StringUtil.nvl(note.get(i).getId()));
			param.put("dataType", "NOTE_ID");
			insertNonDefiniteData(param);

			param.put("data", StringUtil.nvl(note.get(i).getSubject()));
			param.put("dataType", "NOTE_SUBJECT");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(note.get(i).getMessage()));
			param.put("dataType", "NOTE_MESSAGE");
			insertNonDefiniteData(param);
		}
		
		// 내가 좋아하는 페이지
		logger.info("=============================    likes    ===================================");
		ResponseList<Like> userLikes = facebook.getUserLikes();
		for(int i = 0; i < userLikes.size(); i++){
			param.put("data", StringUtil.nvl(userLikes.get(i).getId()));
			param.put("dataType", "USER_LIKES_ID");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(userLikes.get(i).getName()));
			param.put("dataType", "USER_LIKES_NAME");
			insertNonDefiniteData(param);
			
			param.put("data", StringUtil.nvl(userLikes.get(i).getCategory()));
			param.put("dataType", "USER_LIKES_CATEGORY");
			insertNonDefiniteData(param);
		}
		
		 
		 // getHome 몽땅 다 가저옴
		System.out.println("===========================    home    ==========================");
		ResponseList <Post> home = facebook.getHome();
		for(int i = 0; i < home.size(); i++){
			System.out.println("===============================================================");
			System.out.println(home.get(i).getId() + "==home id");
			System.out.println(home.get(i).getDescription() + "==home getDescription");
			System.out.println(home.get(i).getCaption() + "==home getCaption");
			System.out.println(home.get(i).getMessage() + "==home getMessage");
			System.out.println(home.get(i).getStory() + "==home getStory");
			System.out.println(home.get(i).getStatusType() + "==home getStatusType");
			System.out.println(home.get(i).getCreatedTime().toString() + "==home getCreatedTime");
			System.out.println(home.get(i).getType() + "==home getType");
			System.out.println(home.get(i).getPicture() + "==home getPicture");
			/*
			PagableList<Like> likes = home.get(i).getLikes();
			if(likes.size() > 0){
				for(int j = 0; j < likes.size(); j++){
					System.out.println(likes.get(j).getId() + "==like id");
					System.out.println(likes.get(j).getCategory() + "==like getCategory");
					System.out.println(likes.get(j).getName() + "==like getName");
				}
			}
			PagableList<Comment> comment = home.get(i).getComments();
			if(comment.size() > 0){
				for(int k = 0; k < comment.size(); k++){
					System.out.println(comment.get(k).getId() + "=comment id");
					System.out.println(comment.get(k).getMessage() + "==comment getMessage");
					System.out.println(comment.get(k).getLikeCount() + "=comment getLikeCount");
				}
			}
			*/
		}
		
		//Close처리
		
		mav.setViewName("/twitter");
		
		return mav;
	}
	
	public void insertNonDefiniteData(BoxParam param) throws Exception{
		if(!StringUtil.nvl(param.getString("data")).equals("")) {
			logger.info("facebook::  " + param.getString("dataType") + "::    " + param.getString("data"));
			facebookCrawlerService.insertNonDefiniteData(param);
		}
	}
	

}
