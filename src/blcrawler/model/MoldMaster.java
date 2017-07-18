package blcrawler.model;

import java.util.ArrayList;

public class MoldMaster
{
	String masterPartNumber;
	ArrayList<String> subPartNumbers;
	public MoldMaster(String pid)
	{
		masterPartNumber = pid;
		subPartNumbers = new ArrayList<>();
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
	
	
}
