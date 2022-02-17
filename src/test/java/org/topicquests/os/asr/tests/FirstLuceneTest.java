/**
 * 
 */
package org.topicquests.os.asr.tests;


import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.topicquests.os.asr.wd.LuceneDriver;
import org.topicquests.support.api.IResult;

import net.minidev.json.JSONObject;

/**
 * @author jackpark
 * Index and query on a single entity
 */
public class FirstLuceneTest extends TestCore {
	private final String 
		ENTITY_LABEL 	= "foo",
		LANGUAGE		= "en",
		ID				= "Q99";
	
	/**
	 * 
	 */
	public FirstLuceneTest() {
		JSONObject jo = new JSONObject();
		jo.put(LuceneDriver.LABEL_FIELD, ENTITY_LABEL);
		jo.put(LuceneDriver.LANGUAGE_FIELD, LANGUAGE);
		jo.put(LuceneDriver.WD_ID_FIELD, ID);
		try {
			//insert data
			wikiData.indexArtifact(jo);
			wikiData.close(); // flush the index
			//search
			IResult r = wikiData.query(ENTITY_LABEL, LANGUAGE);
			System.out.println("A "+r.getErrorString());
			TopDocs td = (TopDocs)r.getResultObject();
			System.out.println("B "+td.toString());
			for (ScoreDoc sd : td.scoreDocs) 
		    {
		      Document d = wikiData.getSearcher().doc(sd.doc);
		      System.out.println(String.format(d.get("id")));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
