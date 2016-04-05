package Ov2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Anders on 24.02.2016.
 */
public class TestModularity extends EvolutionaryAlg {

    public TestModularity() {
        super(TestClass.class,0,0);
    }

    @Override protected void assignFitness() {

    }
    @Override public Individual getCurrentBestFitIndividual() {
        return null;
    }
    @Override public Individual findSolution() {
        return null;
    }

    public static class TestClass extends Individual {

        protected ArrayList<Integer> genotype;
        protected boolean phenotype;

        @Override
        public Individual reproduce(Random random, Individual parent2) {
            // do a special genetic operation
            return null;
        }
    }
}
