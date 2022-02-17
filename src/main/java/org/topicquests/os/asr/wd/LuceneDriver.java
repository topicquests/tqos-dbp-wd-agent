/**
 * 
 */
package org.topicquests.os.asr.wd;

import org.topicquests.os.asr.DbPediaWikidataEnvironment;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;

import net.minidev.json.JSONObject;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 
/**
 * @author jackpark
 * @see https://howtodoinjava.com/lucene/lucene-index-search-examples/
 */
public class LuceneDriver {
	private DbPediaWikidataEnvironment environment;
	private IndexWriter writer;
	private IndexSearcher searcher;
	private final String LUCENE_PATH;
	public static final String
		//Lucene document fields
		LABEL_FIELD		= "label",
		LANGUAGE_FIELD	= "lang",
		WD_ID_FIELD		= "id";
		
	
	/**
	 * 
	 */
	public LuceneDriver(DbPediaWikidataEnvironment env) throws Exception {
		environment = env;
		LUCENE_PATH = environment.getStringProperty("LUCENE_PATH");
		bootstrap();
	}
	
	/**
	 * Must call at end of importing session
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.flush();
		writer.close();
	}
	
	/**
	 * Create the index
	 */
	private void bootstrap() throws Exception{
		FSDirectory dir = FSDirectory.open(Paths.get(LUCENE_PATH));
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		writer = new IndexWriter(dir, config);
	}
	
	/**
	 * Called iteratively on each Wikidata artifact<br/>
	 * The idea is to pull out AT LEAST the "en" label field; perhaps others
	 * together with the Wikidata identity.
	 * @param artifact
	 * @throws Exception
	 */
	public void indexArtifact(JSONObject artifact) throws Exception {
		Document document = new Document();
		String id = artifact.getAsString(WD_ID_FIELD);
		String lang = artifact.getAsString(LANGUAGE_FIELD);
		String label = artifact.getAsString(LABEL_FIELD);
		//TODO some evidence that Lucene wants integer identifiers
		document.add(new StringField(WD_ID_FIELD, id , Field.Store.YES));
		document.add(new TextField(LABEL_FIELD, label , Field.Store.YES));
		document.add(new TextField(LANGUAGE_FIELD, lang , Field.Store.YES));
		writer.addDocument(document);
	}
	
	public void indexArtifacts(List<JSONObject> artifacts) throws Exception {
		Iterator<JSONObject> itr = artifacts.iterator();
		JSONObject jo;
		while (itr.hasNext())
			indexArtifact(itr.next());
	}
	/**
	 * Can return {@code null} if a query was not activated.
	 * @return
	 */
	public IndexSearcher getSearcher() {
		return searcher;
	}
	/**
	 * 
	 * @param artifactName
	 * @param language
	 * @return {@link TopDocs}
	 */
	public IResult query(String artifactName, String language) {
		IResult result = new ResultPojo();
		try {
			Directory dir = FSDirectory.open(Paths.get(LUCENE_PATH));
			IndexReader reader = DirectoryReader.open(dir);
			searcher = new IndexSearcher(reader);
			TopDocs td = searchByLabelAndLanguage(artifactName, language, searcher);
			result.setResultObject(td);
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			result.addErrorString(e.getMessage());
		}
		return result;
	}
	
	private TopDocs searchByLabelAndLanguage(String label, String language, IndexSearcher searcher) throws Exception
	  {
	    QueryParser qp = new QueryParser("firstName", new StandardAnalyzer());
	    String q = LABEL_FIELD+":"+label+" + "+LANGUAGE_FIELD+":"+language;
	    Query qry = qp.parse(q);
	    TopDocs hits = searcher.search(qry, 10);
	    return hits;
	  }

}
