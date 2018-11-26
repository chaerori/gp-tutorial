import ec.*;
import ec.simple.*;
import ec.vector.*;

public class MaxOnes extends Problem implements SimpleProblemForm
{
    public void evaluate(final EvolutionState state,
                         final Individual individual,
                         final int subpopulation,
                         final int threadnum)
    {
        if (individual.evaluated) return;

        if (!(individual instanceof BitVectorIndividual))
            state.output.fatal("It's not a BitVectorIndividual!", null);

        int sum = 0;
        BitVectorIndividual individual2 = (BitVectorIndividual)individual;

        for(int x = 0; x < individual2.genome.length; x++)
            sum += (individual2.genome[x] ? 1 : 0);

        if (!(individual2.fitness instanceof SimpleFitness))
            state.output.fatal("It's not a SimpleFitness!", null);
        ((SimpleFitness)individual2.fitness).setFitness(state,
                // fitness
                sum / (double)individual2.genome.length,
                // is the individual ideal?  Indicate here
                sum == individual2.genome.length);
        individual2.evaluated = true;
    }
}
