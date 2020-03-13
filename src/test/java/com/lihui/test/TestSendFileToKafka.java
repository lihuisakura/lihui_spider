package com.lihui.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.lihui.cms.domain.enums.ContentType;
import com.lihui.domain.Article;
import com.lihui.utils.StreamUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:producer.xml")
public class TestSendFileToKafka {

	@Autowired
	private KafkaTemplate kafkaTemplate;
	
	@Test
	public void testSendFileToKafka() {
		String path="E:\\学习/大数据/小二/每日练习/spider/";
		File file=new File(path);
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			String name = file2.getName();
			String articleName = name.replace(".txt", "");
			String readFile = StreamUtil.readFile(file2);
			Article article = new Article();
			article.setTitle(articleName);
			article.setContent(readFile);
			article.setChannel_id(1);
			article.setCategory_id(1);
			article.setUser_id(125);
			article.setPicture("51bf8b8a-0640-4430-9b3a-dcac7271e8ff.jpg");
			article.setContent_type(ContentType.HTML);
			String jsonString = JSON.toJSONString(article);
			kafkaTemplate.send("article", jsonString);
			
		}
	}
	
}