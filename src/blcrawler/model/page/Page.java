package blcrawler.model.page;

import java.util.Date;

public interface Page {
	
	Date getLastPullTimestamp();
	
	void parseFromWeb();

}
