package blcrawler.model;


import org.jdom2.*;

public class MoldEmpirical
{
    int[] empiricalMeasurementsSingle;
    int[] empiricalMeasurementsDouble;
    int extrapolateAt;
    int multiple;
    public boolean doublePreferred;
    boolean doublePossible;
    boolean empty;
    String partNumber;
    
    public MoldEmpirical(String pn)
    {
        empty = true;
        partNumber=pn;
        empiricalMeasurementsSingle = new int[17];
        empiricalMeasurementsDouble = new int[11];
        extrapolateAt=16;
        multiple = 1;
        doublePreferred = false;
        
        for (int i=1; i<17; i++)
        {
            
            empiricalMeasurementsSingle[i]=0;
            if (i<12 && (i%2)==0)
            {
                empiricalMeasurementsDouble[i]=0;
            }
        }
    }
    
    public MoldEmpirical(Element moldElement, String pn)
    {
        empty = false;
        partNumber=pn;
        empiricalMeasurementsSingle = new int[17];
        empiricalMeasurementsDouble = new int[11];
        try
        {
            extrapolateAt=Integer.valueOf(moldElement.getChild("extrapolationpoint").getText());
            multiple = Integer.valueOf(moldElement.getChild("multiples").getText());
            doublePreferred = Boolean.valueOf(moldElement.getChild("doublepref").getText());
        }
        catch (Exception e1)
        {
            System.out.println("Error at metaparsing");
            e1.printStackTrace();
        }
        
        for (int i=1; i<17; i++)
        {
            empiricalMeasurementsSingle[i]=Integer.valueOf(moldElement.getChild("singlecaps").getChild("cap"+i).getText());
            if (i<12 && (i%2)==0)
            {
                try
                {
                    empiricalMeasurementsDouble[i]=Integer.valueOf(moldElement.getChild("doublecaps").getChild("cap"+i+"x").getText());
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Error trying to read child cap"+i+"x");
                    e.printStackTrace();
                }
            }
        }
        
        
    }
   

    public int getEmpiricalMeasurementsSingle(int index)
    {
        return empiricalMeasurementsSingle[index];
    }

    public void setEmpiricalMeasurementsSingle(int[] empiricalMeasurementsSingle)
    {
        this.empiricalMeasurementsSingle = empiricalMeasurementsSingle;
        empty=false;
    }

    public int getEmpiricalMeasurementsDouble(int index)
    {
        return empiricalMeasurementsDouble[index];
    }

    public void setEmpiricalMeasurementsDouble(int[] empiricalMeasurementsDouble)
    {
        this.empiricalMeasurementsDouble = empiricalMeasurementsDouble;
        empty=false;
    }

    public int getExtrapolateAt()
    {
        return extrapolateAt;
    }

    public void setExtrapolateAt(int extrapolateAt)
    {
        this.extrapolateAt = extrapolateAt;
        empty=false;
    }

    public int getMultiple()
    {
        return multiple;
    }

    public void setMultiple(int multiple)
    {
        this.multiple = multiple;
        empty=false;
    }

    public boolean isDoublePreferred()
    {
        return doublePreferred;
    }

    public void setDoublePreferred(boolean doublePreferred)
    {
        this.doublePreferred = doublePreferred;
        empty=false;
    }

    public boolean isDoublePossible()
    {
        return doublePossible;
    }

    public void setDoublePossible(boolean doublePossible)
    {
        this.doublePossible = doublePossible;
        empty=false;
    }
    
    
}
