import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.IntegerVectorIndividual;

public class AddSubtract extends Problem implements SimpleProblemForm
{
    public void evaluate(final EvolutionState state,
                         final Individual individual,
                         final int subpopulation,
                         final int threadnum)
    {
        if (individual.evaluated) return;

        if (!(individual instanceof IntegerVectorIndividual))
            state.output.fatal("It's not a IntegerVectorIndividual",null);

        IntegerVectorIndividual individual2 = (IntegerVectorIndividual)individual;

        int rawfitness = 0;
        for(int x = 0; x < individual2.genome.length; x++)
            if (x % 2 == 0) rawfitness += individual2.genome[x];
            else rawfitness -= individual2.genome[x];

        if (rawfitness < 0) rawfitness = -rawfitness;
        if (!(individual2.fitness instanceof SimpleFitness))
            state.output.fatal("It's not a SimpleFitness",null);
        ((SimpleFitness)individual2.fitness).setFitness(state,
                // normalize the fitness for genome length
                rawfitness/(double)individual2.genome.length,
                // If it is the individual ideal, indicate here
                false);
        individual2.evaluated = true;
    }
}
