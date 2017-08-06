package blcrawler.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class MoldMaster
{
	String masterPartNumber;
	ArrayList<String> subPartNumbers;
	Hashtable<String, Integer> rcodes;

	public MoldMaster(String pid)
	{
		masterPartNumber = pid;
		subPartNumbers = new ArrayList<>();
	}
	public void addRCode(String size, Integer count)
	{
		rcodes.put(size, count);
	}
	public Integer getRCode(String size)
	{
		return rcodes.get(size);
	}
	public String getMasterPartNumber()
	{
		return masterPartNumber;
	}
	public void setMasterPartNumber(String masterPartNumber)
	{
		this.masterPartNumber = masterPartNumber;
	}
	public ArrayList<String> getSubPartNumbers()
	{
		return subPartNumbers;
	}
	public void setSubPartNumbers(ArrayList<String> subPartNumbers)
	{
		this.subPartNumbers = subPartNumbers;
	}
	public void addSubPartNumber(String number)
	{
		subPartNumbers.add(number);
	}
	public Hashtable<String, Integer> getRcodes()
	{
		return rcodes;
	}
	public void setRcodes(Hashtable<String, Integer> rcodes)
	{
		this.rcodes = rcodes;
	}


}
