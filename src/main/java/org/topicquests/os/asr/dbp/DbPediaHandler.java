/**
 * 
 */
package org.topicquests.os.asr.dbp;

import org.topicquests.os.asr.DbPediaWikidataEnvironment;
import org.topicquests.os.asr.driver.DbpDriverEnvironment;
import org.topicquests.support.ResultPojo;
import org.topicquests.support.api.IResult;

/**
 * @author jackpark
 * <p> Pass whole sentences through an HTTP query to DbPedia Spotlight (Model)
 * and return JSON artifacts</p>
 * 
 */
public class DbPediaHandler {
	private DbPediaWikidataEnvironment environment;
	private DbpDriverEnvironment driver;
	
	/**
	 * @param env
	 */
	public DbPediaHandler(DbPediaWikidataEnvironment env) {
		environment = env;
	}
	
	public IResult processSentence(String sentence) {
		IResult result = new ResultPojo();
		//TODO
		return result;
	}


}
