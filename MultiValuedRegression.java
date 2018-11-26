import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

public class MultiValuedRegression extends GPProblem implements SimpleProblemForm
{
    private static final long serialVersionUID = 1;

    public double currentX;
    public double currentY;

    public void setup(final EvolutionState state,
                      final Parameter base)
    {
        super.setup(state, base);

        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof DoubleData))
            state.output.fatal("GPData class must subclass from " + DoubleData.class,
                    base.push(P_DATA), null);
    }

    public void evaluate(final EvolutionState state,
                         final Individual individual,
                         final int subpopulation,
                         final int threadnum)
    {
        if (!individual.evaluated)
        {
            DoubleData input = (DoubleData)(this.input);

            int hits = 0;
            double sum = 0.0;
            double expectedResult;
            double result;
            for (int y = 0; y < 10; y++)
            {
                currentX = state.random[threadnum].nextDouble();
                currentY = state.random[threadnum].nextDouble();
                expectedResult = currentX * currentX * currentY + currentX * currentY + currentY;
                ((GPIndividual)individual).trees[0].child.eval(
                        state, threadnum, input, stack, ((GPIndividual)individual), this);

                result = Math.abs(expectedResult - input.x);
                if (result <= 0.01) hits++;
                sum += result;
            }

            KozaFitness f = ((KozaFitness)individual.fitness);
            f.setStandardizedFitness(state, sum);
            f.hits = hits;
            individual.evaluated = true;
        }
    }
}

