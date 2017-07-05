package blcrawler.primatives;

public class ObjectSpecificColorCount implements Comparable<ObjectSpecificColorCount>
{
	ColorCount cc;
	String partNumber;
	
	public ObjectSpecificColorCount(String pn, ColorCount colorCount) 
	{
		this.cc = colorCount;
		this.partNumber = pn;
	}

	public ColorCount getCc()
	{
		return cc;
	}

	public void setCc(ColorCount cc)
	{
		this.cc = cc;
	}

	public String getPartNumber()
	{
		return partNumber;
	}

	public void setPartNumber(String partNumber)
	{
		this.partNumber = partNumber;
	}
	


	@Override
	public int compareTo(ObjectSpecificColorCount o)
	{
		// TODO Auto-generated method stub
		return (cc.getCount()-o.getCc().getCount());
	}
	
	
}
