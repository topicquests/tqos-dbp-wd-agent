/**
 * 
 */
package org.topicquests.os.asr;

import org.topicquests.os.asr.dbp.DbPediaHandler;
import org.topicquests.os.asr.wd.WikidataHandler;
import org.topicquests.support.RootEnvironment;

/**
 * @author jackpark
 *
 */
public class DbPediaWikidataEnvironment extends RootEnvironment {
	private DbPediaHandler dbPedia;
	private WikidataHandler wikiData;
	/**
	 * 
	 */
	public DbPediaWikidataEnvironment() {
		super("dbpwd-props.xml", "logger.properties");
		try {
			dbPedia = new DbPediaHandler(this);
			wikiData = new WikidataHandler(this);
		} catch (Exception e) {
			logError(e.getLocalizedMessage(), e);
			e.printStackTrace();
			try {
				wikiData.close();
			} catch (Exception x) {
				logError(x.getLocalizedMessage(), x);
				x.printStackTrace();
			}
		}
	}

	
	public DbPediaHandler getDbPedia() {
		return dbPedia;
	}
	
	public WikidataHandler getWikiData() {
		return wikiData;
	}
	
	
	@Override
	public void shutDown() {
		// TODO Auto-generated method stub

	}

}
