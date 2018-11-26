import ec.gp.GPData;

public class DoubleData extends GPData
{
    public double x; // return value

    public void copyTo(final GPData gpData)
    {
        ((DoubleData)gpData).x = x;
    }
}