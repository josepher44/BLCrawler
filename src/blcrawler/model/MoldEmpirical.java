package blcrawler.model;

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
