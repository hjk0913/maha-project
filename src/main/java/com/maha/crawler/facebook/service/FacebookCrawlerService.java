package com.maha.crawler.facebook.service;

import java.util.List;

import com.bzfree.bzekbox.data.BoxParam;

public interface FacebookCrawlerService {
	public void insertDefiniteData(BoxParam param) throws Exception;
	public void insertNonDefiniteData(BoxParam param) throws Exception;
}
