package blcrawler.model;

public class MoldEmpirical
{
    int[] empiricalMeasurementsSingle;
    int[] empiricalMeasurementsDouble;
    int extrapolateAt;
    int multiple;
    boolean doublePreferred;
    boolean doublePossible;
    boolean empty;
    String partNumber;
    
    public MoldEmpirical(String pn)
    {
        empty = true;
        partNumber=pn;
    }

    public int[] getEmpiricalMeasurementsSingle()
    {
        return empiricalMeasurementsSingle;
    }

    public void setEmpiricalMeasurementsSingle(int[] empiricalMeasurementsSingle)
    {
        this.empiricalMeasurementsSingle = empiricalMeasurementsSingle;
        empty=false;
    }

    public int[] getEmpiricalMeasurementsDouble()
    {
        return empiricalMeasurementsDouble;
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
