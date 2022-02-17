/**
 * 
 */
package org.topicquests.os.asr.wd;

import java.util.List;

import org.apache.lucene.search.IndexSearcher;
import org.topicquests.os.asr.DbPediaWikidataEnvironment;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;

import net.minidev.json.JSONObject;

/**
 * @author jackpark
 * <p>Query the Wikidata database for artifact (entity and property) identifiers
 * and return JSON artifacts</p>
 * <p>NOTE<br/>
 * From https://github.com/filbertkm/wikidata-dump-parser
 * Requires
 * 
 * 		postgresql-9.3
 *		postgresql-9.3-postgis-2.1
 *		postgresql-contrib-9.3 (for hstore extension)
 *	We will be using later versions of those</p>
 *<p>NOTE again<br/>
 * Switching to Lucene</p>
 */
public class WikidataHandler {
	private DbPediaWikidataEnvironment environment;
	private LuceneDriver lucene;
	/**
	 * @param env
	 */
	public WikidataHandler(DbPediaWikidataEnvironment env) throws Exception {
		environment = env;
		lucene = new LuceneDriver(environment);
	}
	

	
	public void close() throws Exception {
		lucene.close();
	}
	
	public void indexArtifact(JSONObject artifact) throws Exception {
		lucene.indexArtifact(artifact);
	}
	
	public void indexArtifacts(List<JSONObject> artifacts) throws Exception {
		lucene.indexArtifacts(artifacts);
	}
	
	public IResult query(String artifactName, String language) {
		return lucene.query(artifactName, language);
	}

	public IndexSearcher getSearcher() {
		return lucene.getSearcher();
	}

}
