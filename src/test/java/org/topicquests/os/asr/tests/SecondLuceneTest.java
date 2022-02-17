/**
 * 
 */
package org.topicquests.os.asr.tests;

import net.minidev.json.JSONObject;
import java.util.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.topicquests.os.asr.wd.LuceneDriver;
import org.topicquests.support.api.IResult;
/**
 * @author jackpark
 * Index and query on a collection of entities.
 */
public class SecondLuceneTest extends TestCore {
	private final String
		E1LBL				= "hello",
		E1LANG				= "en",
		E1ID				= "Q101",
		E2LBL				= "bounjour",
		E2LANG				= "fr",
		E2ID				= "Q101",
		E3LBL				= "안녕하세요",
		E3LANG				= "kr",
		E3ID				= "Q101",
		E4LBL				= "hallo",
		E4LANG				= "de",
		E4ID				= "Q101",
		E5LBL				= "Привет",
		E5LANG				= "ru",
		E5ID				= "Q101";

	/**
	 * 
	 */
	public SecondLuceneTest() {
		List<JSONObject> entities = new ArrayList<JSONObject>();
		JSONObject jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, E1LBL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, E1LANG);
		jo.put(LuceneDriver.WD_ID_FIELD, E1ID);
		entities.add(jo);
		jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, E2LBL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, E2LANG);
		jo.put(LuceneDriver.WD_ID_FIELD, E2ID);
		entities.add(jo);
		jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, E3LBL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, E3LANG);
		jo.put(LuceneDriver.WD_ID_FIELD, E3ID);
		entities.add(jo);
		jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, E4LBL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, E4LANG);
		jo.put(LuceneDriver.WD_ID_FIELD, E4ID);
		entities.add(jo);
		jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, E5LBL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, E5LANG);
		jo.put(LuceneDriver.WD_ID_FIELD, E5ID);
		entities.add(jo);
		try {
			//insert data
			wikiData.indexArtifacts(entities);
			wikiData.close(); // flush the index
			//search
			IResult r = wikiData.query(E3LBL, E3LANG);
			System.out.println("A "+r.getErrorString());
			TopDocs td = (TopDocs)r.getResultObject();
			System.out.println("B "+td.toString());
			for (ScoreDoc sd : td.scoreDocs) 
		    {
		      Document d = wikiData.getSearcher().doc(sd.doc);
		      System.out.println(String.format(d.get("id"))+" "+String.format(d.get("label")));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A 
	 * B org.apache.lucene.search.TopDocs@7c0c77c7
	 * Q101 안녕하세요
	 */


}
