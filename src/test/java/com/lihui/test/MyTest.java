package com.lihui.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.lihui.utils.StreamUtil;

public class MyTest {

	@Test
	public void test01() throws IOException {
		Connection connect = Jsoup.connect("https://www.sohu.com/");
		Document document = connect.get();
		// System.out.println(document);
		Elements element = document.select(".list16");
		for (Element e : element) {
			Elements a = e.select("a");
			for (Element aa : a) {
				String title = aa.attr("title");
				String href = aa.attr("href");
				if (!href.startsWith("http")) {
					href = "https:" + href;
				}
				Connection connect2 = Jsoup.connect(href);
				Document document2 = connect2.get();
				Elements content = document2.select(".article");
				StringBuffer sb=new StringBuffer();
				for (Element element2 : content) {
					
					String string = element2.toString();
					sb.append(string);
				}
				Elements select = content.select("a");
				String articleContent = sb.toString().replace(select.toString(), "");
				articleContent=articleContent.replace("max-width=\"600\"", "style=\"width: 650px\"");
				title=title.replace("\\", "").replace("/", "").replace(":", "").replace("*", "").replace("?", "").replace("\"", "").replace("<", "").replace(">", "").replace("\\|", "");
				File file=new File("E:\\学习/大数据/小二/每日练习/spider/"+title+".txt");
				StreamUtil.writeTextFile(articleContent, file);
				
			}

		}
	}
}
