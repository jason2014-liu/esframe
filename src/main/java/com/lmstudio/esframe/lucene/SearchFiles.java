/**
* TODO
* @Project: esframe
* @Title: SearchFiles.java
* @Package com.appframe.component.lucene
* @author jason.liu
* @Copyright
* @Version 
*/
package com.lmstudio.esframe.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * TODO
 * 
 * @ClassName: SearchFiles
 * @author jason.liu
 */
public class SearchFiles {

	/**
	 * TODO
	 * 
	 * @Title: main
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		String index = "E:\\indexdata";// 索引所在目录
		String field = "content";
		String queryString = "方案";
		int hitsPerPage = 10;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(queryString);//查询字符串
		
		System.out.println("Searching for:" + query.toString(field));

		doPagingSearch(searcher, query, hitsPerPage, 1);
	}

	/**
	 * TODO 分页查询 (默认查询前5页命中，，一般情况下全文搜索不需要查询所有命中结果)
	 * 
	 * @Title: doPagingSearch
	 * @param searcher
	 * @param query
	 * @param hitsPerPage
	 * @param currPage
	 *            从1开始
	 * @throws IOException
	 */
	public static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage,
			int currPage) throws IOException {

		// Collect enough docs to show 5 pages
		// Finds the top n hits for query.
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;// 文档命中数，一个文档中可能命中多个字符串
		int numTotalHits = results.totalHits;// 字符串命中数

		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		hits = searcher.search(query, end).scoreDocs;

		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null) {
				System.out.println((i + 1) + ". " + path);
				String title = doc.get("title");
				if (title != null) {
					System.out.println("   Title: " + doc.get("title"));
				}
			} else {
				System.out.println((i + 1) + ". " + "No path for this document");
			}
		}

	}

}
