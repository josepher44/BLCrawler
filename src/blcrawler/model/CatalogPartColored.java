package blcrawler.model;

public class CatalogPartColored
{
	short partnumber;
	short[] alternateNumbers;
	short[] colors_Listed;		//
	short[] colors_Wanted;
	short[] colors_Sold;
	short[] colors_Inventory;
	short[] colors_Verified;	//Colors that Third Pig Bricks has personally and physically verified
	Boolean assembly;			//Is this part an assembly of subparts?
	short catID;				//Category ID
	double blweight;			//Weight in grams, as reported by Bricklink
	
	
	
	
	
}
