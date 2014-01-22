package com.maha.crawler.twitter.service;

import com.bzfree.bzekbox.data.BoxParam;

public interface TwitterCrawlerService {
	public void insertDefiniteData(BoxParam param) throws Exception;
	public void insertNonDefiniteData(BoxParam param) throws Exception;
}
