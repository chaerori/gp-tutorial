import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;

public class OddRosenbrock extends Problem implements SimpleProblemForm
{
    public void setup(final EvolutionState state, final Parameter base) { }

    public void evaluate(final EvolutionState state,
                         final Individual individual,
                         final int subpopulation,
                         final int threadnum)
    {
        if( !( individual instanceof DoubleVectorIndividual) )
            state.output.fatal( "The individuals for this problem should be DoubleVectorIndividuals." );

        double[] genome = ((DoubleVectorIndividual)individual).genome;
        int len = genome.length;
        double value = 0;

        // Compute the Rosenbrock function for our genome
        for(int i = 1; i < len; i++)
        {
            value += 100 * (genome[i - 1] * genome[i - 1] - genome[i]) * (genome[i - 1] * genome[i - 1] - genome[i]) + (1 - genome[i - 1]) * (1 - genome[i - 1]);
        }

        value = 1.0 / (1.0 + value);
        ((SimpleFitness)(individual.fitness)).setFitness(state, value, value == 1.0);

        individual.evaluated = true;
    }
}