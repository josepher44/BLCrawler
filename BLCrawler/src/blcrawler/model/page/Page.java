package blcrawler.model.page;

import java.util.Date;

public interface Page {
	
	void parseFromWeb();
	
	Date getLastPullTimestamp();

}
