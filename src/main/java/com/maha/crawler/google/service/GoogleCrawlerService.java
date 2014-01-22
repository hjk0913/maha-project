package com.maha.crawler.google.service;

import com.bzfree.bzekbox.data.Box;
import com.bzfree.bzekbox.data.BoxParam;

public interface GoogleCrawlerService {
	public Box getUserData(BoxParam param) throws Exception;
	public int regeditUser(BoxParam param) throws Exception;
	public void regeditUserAccount(BoxParam param) throws Exception;
	public void insertGoogleData(BoxParam param) throws Exception;
	public void insertNonDefiniteData(BoxParam param) throws Exception;
}
