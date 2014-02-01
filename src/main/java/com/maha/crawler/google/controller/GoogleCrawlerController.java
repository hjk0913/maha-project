package com.maha.crawler.google.controller;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bzfree.bzekbox.data.Box;
import com.bzfree.bzekbox.data.BoxParam;
import com.bzfree.bzekbox.util.BoxUtil;
import com.maha.crawler.google.service.GoogleCrawlerService;

@Controller
@RequestMapping(value = "/google/")
public class GoogleCrawlerController {
	
	private static Logger logger = Logger.getLogger(GoogleCrawlerController.class);
	
	@Resource
    private GoogleCrawlerService googleCrawlerService;
	
	@RequestMapping(value = "signin.do")
	public ModelAndView goRegedit(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/google");
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submit.do")
	public ModelAndView getUserData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		BoxParam param = BoxUtil.getBox(request);
		param.put("serviceId", "G");
		
		Box userData = googleCrawlerService.getUserData(param);
		int userSeq = 0;

		if(userData.getString("USER_CNT").equals("0")){
			// 유저가 없으면 등록
			// TB_USER 등록
			userSeq = googleCrawlerService.regeditUser(param); 
			param.put("userSeq", userSeq);
			
			// TB_USER_ACCOUNT 등록
			googleCrawlerService.regeditUserAccount(param);
		} else if(userData.getString("USER_CNT").equals("1")){
			// 있으면 크롤링만
			userSeq = userData.getInt("USER_SEQ");
			param.put("userSeq", userSeq);
		}
		
		HttpSession hSession = null;
		hSession = request.getSession(false);
		hSession.setAttribute("userSeq", userSeq);
		
		// Saved as UTF-8
		// TODO Auto-generated method stub
		Properties props = new Properties();
	    props.put("mail.smtp.host", "my-mail-server");
	    //props.put("mail.from", "tony@techmaru.com");
	    props.put("mail.from", param.getString("gid"));
	    Session session = Session.getDefaultInstance(props, null);
		Store store = null;
		try {
			store = session.getStore("imaps");
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{
			
			//Input your mail account information, id, password
			store.connect("imap.gmail.com", param.getString("gid"), param.getString("gpw"));
			//show folders' list
			Folder[] f = store.getDefaultFolder().list();
			for(Folder fd:f)
			    System.out.println(">> "+fd.getName());
			
			//Retrieve last sent mail from Gmail
			Folder inbox = store.getFolder("[Gmail]/Sent Mail");
			//Retrieve mail from Inbox
			//Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			
			/* Taking whole mailbox
			Message messages[] = inbox.getMessages();
			for(Message message:messages)
			{
				//
			}
			*/					
				int messageCount = inbox.getMessageCount();
			 	Message msg = inbox.getMessage(messageCount);
		        
		        Flags seen = new Flags(Flags.Flag.SEEN);
		        // Taking unread msg
		        //FlagTerm unseenFlagTerm = new FlagTerm(seen,false);
		        FlagTerm unseenFlagTerm = new FlagTerm(seen,true);
		        Message message[] = inbox.search(unseenFlagTerm);
		        
		        
		        MimeMultipart bodyMultipart;
		        String ContentType[];

		        
		        for(int i = 0; i < message.length; i++){
		        	
		        	if(i == 10) break; // 테스트 코드
		        	
		        	
		            //System.out.println("Message " + (i + 1));
		            //System.out.println("From : " + InternetAddress.toString(message[i].getFrom()));
		            //System.out.println("To : " + InternetAddress.toString(message[i].getAllRecipients()));
		            if (message[i].getSubject() != null) {
		            	//System.out.println("Subject : " + message[i].getSubject().toString());
		            } else {
		            	//System.out.println("Subject : ");
		            }
		            //sort data along body content type
		            //need to understand MultiPart structure
		            //must develop a core module which sort data with a particular key after disassembling the MultiPart structure
		            //";" can distinguish content type from mail data which contains type and binary. sequence is "mulipart/MIXED;@binary"
		            //�곗씠�곕� 而⑦뀗痢���엯�쇰줈 �뺣젹
		            //硫�떚�뚰듃 援ъ“泥댁뿉 ��븳 �댄빐媛��덉뼱����
		            //寃곌뎅 硫�떚�뚰듃 援ъ“泥대� 遺꾪빐 �섏뿬 �뱀젙 ��媛믪쑝濡��곗씠�곕� �뺣젹�����덈뒗 紐⑤뱢���꾩슂�섎떎.
		            //而⑦뀗痢���엯怨�諛붿씠�덈━瑜�";" 援щ텇�먮줈 愿�━�� �쒖꽌��"mulipart/MIXED";@諛붿씠�덈━
		            ContentType = message[i].getContentType().split(";");
		            
		            //show its ContentType
		            //MultiPart ��而⑦뀗痢���엯��蹂댁뿬以�떎.
		            //System.out.println("Content Type : " + ContentType[0]);
		            if (ContentType[0].equals("multipart/MIXED"))
		            {	
			            bodyMultipart = (MimeMultipart)message[i].getContent();
			          	for (int j=0;j<bodyMultipart.getCount();j++)
			          	{
			          		//System.out.println("Body: " +  bodyMultipart.getBodyPart(j).getContent().toString());
			          		//System.out.println("Body ContentType : " + bodyMultipart.getBodyPart(j).getContentType().toString());
			          		param.put("data", bodyMultipart.getBodyPart(j).getContentType().toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
			          	}
		            }
		            else if (ContentType[0].equals("multipart/RELATED"))
		            {
		            	bodyMultipart = (MimeMultipart)message[i].getContent();
			          	for (int j=0;j<bodyMultipart.getCount();j++)
			          	{
			          		//System.out.println("Body: " +  bodyMultipart.getBodyPart(j).getContent().toString());
			          		param.put("data", bodyMultipart.getBodyPart(j).getContent().toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
			          	}
		            }
		            else if (ContentType[0].equals("multipart/ALTERNATIVE"))
		            {
		            	bodyMultipart = (MimeMultipart)message[i].getContent();
			          	for (int j=0;j<bodyMultipart.getCount();j++)
			          	{
			          		//System.out.println("Body: " +  bodyMultipart.getBodyPart(j).getContent().toString());
			          		param.put("data", bodyMultipart.getBodyPart(j).getContent().toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
			          	}
		            }
		            else {
		            	//System.out.println("Body: " +  message[i].getContent().toString());
		            	param.put("data", message[i].getContent().toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
		          		
		            }
		            googleCrawlerService.insertNonDefiniteData(param);
		        }    
		        
		        inbox.close(false);
		        store.close();
		}
		catch (MessagingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		mav.addObject("param", param);
		mav.setViewName("facebook");
		return mav;
	}
}
