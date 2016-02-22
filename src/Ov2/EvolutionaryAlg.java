package Ov2;

import java.util.*;

/**
 * Created by Anders on 22.02.2016.
 */
public abstract class EvolutionaryAlg {

    // should be changable at runtime
    public static final int MAX_POPULATION_SIZE = 10;
    public int generation;

    protected HashSet<Individual> population;
    protected Random random;

    public EvolutionaryAlg(Class<? extends Individual> individualClass) {

        generation = 1;

        this.population = new HashSet<>();

        random = new Random();

        try {
            for (int individual = 0; individual < MAX_POPULATION_SIZE; individual++) {
                Individual i = individualClass.getConstructor(Random.class).newInstance(random);

                while (population.contains(i)) {
                    System.err.println("Collision!");

                    i = individualClass.getConstructor(Random.class).newInstance(random);
                }

                population.add(i);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

    }

    public void runNextGeneration() {
        generation++;

        developPhenotypes();
        assignFitness();
        selectAdults();
        selectParentsAndReproduce();
    }


    protected void developPhenotypes() {
        population.forEach(Individual::growPhenotype);
    }


    protected abstract void assignFitness();

    /**
     * eliminate non-viable children and adults
     * could execute one of the methods below
     */
    protected abstract void selectAdults();

    protected void fullGenerationReplacement() {
        for (Iterator<Individual> i = population.iterator(); i.hasNext();) {
            Individual element = i.next();
            if (element.isAdult()) {
                i.remove();
            }
        }

        population.forEach(Individual::mature);
    }

    protected void overProduction() {
        for (Iterator<Individual> i = population.iterator(); i.hasNext();) {
            Individual element = i.next();
            if (element.isAdult()) {
                i.remove();
            }
        }

        battle();
    }

    protected void battle() {
        ArrayList<Individual> all = new ArrayList<>(population);
        Collections.sort(all);

        population.retainAll(all.subList(0, Math.min(MAX_POPULATION_SIZE, population.size())));

        population.forEach(Individual::mature);
    }

    /**
     * select and generate new genotypes from parents genotypes
     */
    protected abstract void selectParentsAndReproduce();

    protected void rouletteWheelAndReproduce(ArrayList<Double> proportions, ArrayList<Individual> popCopy, int numberOfChildren) {

        // normalize
        proportions.forEach(fitness -> fitness /= proportions.size());

        // accumulate proportions
        for (int i = 1; i < proportions.size(); i++) {
            proportions.set(i, proportions.get(i) + proportions.get(i-1));
        }

        for (int i = 0; i < numberOfChildren; i++) {

            double value = random.nextDouble();

            for (int j = 0; j < population.size(); j++) {
                if (value <= proportions.get(j)) {
                    population.add(popCopy.get(j).reproduce(random));
                    break;
                }
            }
        }
    }

    protected void fitnessProportionaleParentSelection(int numberOfChildren) {
        ArrayList<Double> proportions = new ArrayList<>();
        ArrayList<Individual> popCopy = new ArrayList<>(population);
        popCopy.forEach(individual -> proportions.add((double) individual.fitness));

        rouletteWheelAndReproduce(proportions, popCopy, numberOfChildren);
    }

    protected void sigmaScalingParentSelection(int numberOfChildren) {
        ArrayList<Double> proportions = new ArrayList<>();
        ArrayList<Individual> popCopy = new ArrayList<>(population);
        popCopy.forEach(individual -> proportions.add((double) individual.fitness));

        double average = 0;
        OptionalDouble opAverage = popCopy.stream().mapToInt(value -> value.fitness).average();
        if (opAverage.isPresent()) {
            average = opAverage.getAsDouble();
        }
        final double finalAverage = average;

        double standardDeviation = Math.sqrt(popCopy.stream().mapToDouble(value -> Math.pow(value.fitness - finalAverage, 2)).sum() / popCopy.size());

        if (standardDeviation != 0.0) {
            for (int i = 0; i < proportions.size(); i++) {
                proportions.set(i, 1 + (proportions.get(i) - finalAverage) / (2 * standardDeviation));
            }
        }

        rouletteWheelAndReproduce(proportions, popCopy, numberOfChildren);
    }

    protected void tournamentSelectionAndReproduce(int numberOfChildren) {

    }


    public void printStats() {
        population.forEach(individual -> System.out.println(individual.genotype));
    }


    public abstract Individual findSolution();
}
