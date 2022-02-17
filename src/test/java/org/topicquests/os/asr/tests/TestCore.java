/**
 * 
 */
package org.topicquests.os.asr.tests;

import org.topicquests.os.asr.DbPediaWikidataEnvironment;
import org.topicquests.os.asr.dbp.DbPediaHandler;
import org.topicquests.os.asr.wd.WikidataHandler;

/**
 * @author jackpark
 *
 */
public class TestCore {
	protected DbPediaWikidataEnvironment environment;
	protected DbPediaHandler dbPedia;
	protected WikidataHandler wikiData;

	/**
	 * 
	 */
	public TestCore() {
		environment = new DbPediaWikidataEnvironment();
		dbPedia = environment.getDbPedia();
		wikiData = environment.getWikiData();
	}

}
