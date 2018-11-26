import ec.EvolutionState;
import ec.Individual;
import ec.SelectionMethod;
import ec.util.Parameter;

import java.util.ArrayList;

public class OurSelection extends SelectionMethod
{
    public static final String P_OURSELECTION = "our-selection";

    public Parameter defaultBase() { return new Parameter(P_OURSELECTION); }

    public static final String P_MIDDLEPROBABILITY = "middle-probability";  // parameter name

    public double middleProbability;

    public void setup(final EvolutionState state, final Parameter base)
    {
        super.setup(state, base);

        Parameter def = defaultBase();

        middleProbability = state.parameters.getDoubleWithMax(base.push(P_MIDDLEPROBABILITY),
                def.push(P_MIDDLEPROBABILITY), 0.0, 1.0);
        if (middleProbability < 0.0)
            state.output.fatal("Middle-Probability must be between 0.0 and 1.0", base.push(P_MIDDLEPROBABILITY), def.push(P_MIDDLEPROBABILITY));
    }

    public int produce(final int subpopulation, final EvolutionState state, final int thread)
    {
        //toss a coin
        if (state.random[thread].nextBoolean(middleProbability))
        {
            //pick three individuals, return the middle one
            ArrayList<Individual> individuals = state.population.subpops.get(subpopulation).individuals;
            int one = state.random[thread].nextInt(individuals.size());
            int two = state.random[thread].nextInt(individuals.size());
            int three = state.random[thread].nextInt(individuals.size());

            if (individuals.get(two).fitness.betterThan(individuals.get(one).fitness))
            {
                if (individuals.get(three).fitness.betterThan(individuals.get(two).fitness)) // 1 < 2 < 3
                    return two;
                else if (individuals.get(three).fitness.betterThan(individuals.get(one).fitness)) // 1 < 3 < 2
                    return three;
                else // 3 < 1 < 2
                    return one;
            }
            else if (individuals.get(three).fitness.betterThan(individuals.get(one).fitness)) // 2 < 1 < 3
                return one;
            else if (individuals.get(three).fitness.betterThan(individuals.get(two).fitness)) // 2 < 3 < 1
                return three;
            else // 3 < 2 < 1
                return two;
        }
        else // select a random individual's index
        {
            return state.random[thread].nextInt(state.population.subpops.get(subpopulation).individuals.size());
        }
    }
}
