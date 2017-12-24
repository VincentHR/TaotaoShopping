/*package com.taobao.rest.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.aop.ThrowsAdvice;

public class SolrJTest {
		
	@Test
	public void addDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.6.138:8080/solr");
		//创建一个文档对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "first111");
		solrInputDocument.addField("item_price", 666666);
		//将文档对象写入到索引库
		solrServer.add(solrInputDocument);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.6.138:8080/solr");
		//solrServer.deleteById("test001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	@Test
	public void queryDocument() throws Exception{
		//创建一个连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.6.138:8080/solr");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		query.setStart(20);
		query.setRows(50);
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("共查询到记录： " + solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));

		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
*/