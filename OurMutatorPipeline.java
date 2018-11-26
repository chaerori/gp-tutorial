import ec.BreedingPipeline;
import ec.EvolutionState;
import ec.Individual;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import ec.vector.IntegerVectorSpecies;
import ec.vector.VectorDefaults;

import java.util.ArrayList;
import java.util.HashMap;

public class OurMutatorPipeline extends BreedingPipeline
{
    public static final String P_OURMUTATION = "our-mutation";

    public Parameter defaultBase() { return VectorDefaults.base().push(P_OURMUTATION); }

    public static final int NUM_SOURCES = 1;

    // Return: we only use one source
    public int numSources() { return NUM_SOURCES; }

    public int produce(final int min,
                       final int max,
                       final int subpopulation,
                       final ArrayList<Individual> individual,
                       final EvolutionState state,
                       final int thread, HashMap<String, Object> map)
    {
        int start = individual.size();

        int n = sources[0].produce(min, max, subpopulation, individual, state, thread, map);

        if (!state.random[thread].nextBoolean(likelihood))
        {
            return n;
        }

        if (!(individual.get(start) instanceof IntegerVectorIndividual))
            state.output.fatal("OurMutatorPipeline didn't get an IntegerVectorIndividual." +
                    "The offending individual is in subpopulation " + subpopulation + " and it's:" + individual.get(start));
        IntegerVectorSpecies species = (IntegerVectorSpecies)(individual.get(start).species);

        for(int q = start; q < n + start; q++)
        {
            IntegerVectorIndividual i = (IntegerVectorIndividual)individual.get(q);
            for(int x = 0; x < i.genome.length; x++)
                if (state.random[thread].nextBoolean(species.mutationProbability(x)))
                    i.genome[x] = -i.genome[x];
            // it's a "new" individual, so it's no longer been evaluated
            i.evaluated = false;
        }

        return n;
    }
}