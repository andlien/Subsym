package Ov2;

import java.util.*;

/**
 * Created by Anders on 22.02.2016.
 */
public abstract class EvolutionaryAlg {


    public static float TOURNAMENT_EPSILON = 0.05f;
    public static int TOURNAMENT_SIZE;

    public static String selectAdultMethod;
    public static String selectParentMethod;

    public int numOfChildren;
    public int MAX_POPULATION_SIZE;
    public static int generation;
    protected HashSet<Individual> population;
    protected Random random;

    private ArrayList<Float> avgFitnes;
    private ArrayList<Float> bestFitnes;
    public Individual currentBest;

    public EvolutionaryAlg(Class<? extends Individual> clazz, int MAX_POPULATION_SIZE, int numOfChildren) {

        generation = 0;


        this.MAX_POPULATION_SIZE = MAX_POPULATION_SIZE;
        this.numOfChildren = numOfChildren;

        this.population = new HashSet<>();

        avgFitnes = new ArrayList<Float>();
        bestFitnes = new ArrayList<Float>();

        random = new Random();

        try {
            for (int individual = 0; individual < MAX_POPULATION_SIZE; individual++) {
                population.add(clazz.getConstructor(Random.class).newInstance(random));
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

        currentBest = getCurrentBestFitIndividual();

        logStats();
    }

    private void logStats() {
        System.out.println("Generation: " + generation);
        System.out.println("population size: " + population.size());
        System.out.println("Best fitness: " + currentBest.fitness);
        double average = population.stream().filter(Individual::isAdult).mapToDouble(value -> value.fitness).average().orElseThrow(RuntimeException::new);
        System.out.println("Average fitness: " + average);
        System.out.println("SD: " + Math.sqrt(population.stream().filter(Individual::isAdult).mapToDouble(value -> Math.pow(value.fitness - average, 2)).sum() / population.size()));
        System.out.println("Best phenotype: " + currentBest.phenotype);
        System.out.println();

        avgFitnes.add((float) average);
        bestFitnes.add((float) currentBest.fitness);

    }

    public void printSomeStats(){
        System.out.println("");
        for (int i = 0; i < avgFitnes.size() ; i++) {
            System.out.println(avgFitnes.get(i));
        }
        System.out.println(" ");
        System.out.println("");
        for (int i = 0; i < bestFitnes.size() ; i++) {
            System.out.println(bestFitnes.get(i));
        }
    }


    protected void developPhenotypes() {
        population.forEach(Individual::growBitPhenotype);
    }


    protected abstract void assignFitness();

    /**
     * eliminate non-viable children and adults
     * could execute one of the methods below
     */
    protected void selectAdults() {
        switch (selectAdultMethod) {
            case "f":
                fullGenerationReplacement();
                break;
            case "o":
                overProduction();
                break;
            case "m":
                battle();
                break;
        }
    }

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
        List<Individual> all = new ArrayList<>(population);
        Collections.sort(all);

        all = all.subList(Math.max(0, population.size() - MAX_POPULATION_SIZE), population.size());
        population.retainAll(all);

        population.forEach(Individual::mature);
    }

    /**
     * select and generate new genotypes from parents genotypes
     */
    protected void selectParentsAndReproduce() {
        switch (selectParentMethod) {
            case "f":
                fitnessProportionaleParentSelection(numOfChildren);
                break;
            case "s":
                sigmaScalingParentSelection(numOfChildren);
                break;
            case "r":
                rankSelection(numOfChildren);
                break;
            case "t":
                tournamentSelectionAndReproduce(numOfChildren, TOURNAMENT_SIZE);
                break;
        }
    }

    protected void rouletteWheelAndReproduce(ArrayList<Double> proportions, ArrayList<Individual> popCopy, int numberOfChildren) {

        double sum = proportions.stream().reduce(0.0, Double::sum);

        // normalize
        for (int i = 0; i < proportions.size(); i++) {
            proportions.set(i, proportions.get(i) / sum);
        }

        // accumulate proportions
        for (int i = 1; i < proportions.size(); i++) {
            proportions.set(i, proportions.get(i) + proportions.get(i-1));
        }

        Individual parent1 = null;

        for (int child = 0; child < numberOfChildren; child++) {

            double value = random.nextDouble();


            for (int j = 0; j < population.size(); j++) {
                if (value <= proportions.get(j)) {

                    if (parent1 == null) {
                        parent1 = popCopy.get(j);
                    } else {

                        Individual firstChild = parent1.reproduce(random, popCopy.get(j));
                        while (population.contains(firstChild)) {
//                            System.err.println("Collision");
                            firstChild = parent1.reproduce(random, popCopy.get(j));
                        }
                        population.add(firstChild);

                        Individual secondChild = popCopy.get(j).reproduce(random, parent1);
                        while (population.contains(secondChild)) {
//                            System.err.println("Collision");
                            secondChild = popCopy.get(j).reproduce(random, parent1);
                        }
                        population.add(secondChild);

                        parent1 = null;
                    }

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
        OptionalDouble opAverage = popCopy.stream().mapToDouble(value -> value.fitness).average();
        if (opAverage.isPresent()) {
            average = opAverage.getAsDouble();
        }
        final double finalAverage = average;

        double standardDeviation = Math.sqrt(popCopy.stream().mapToDouble(value -> Math.pow(value.fitness - finalAverage, 2)).sum() / popCopy.size());

        if (standardDeviation != 0.0) {
            for (int i = 0; i < proportions.size(); i++) {
                proportions.set(i, Math.max(1 + (proportions.get(i) - finalAverage) / (2 * standardDeviation), 0));
            }
        }

        rouletteWheelAndReproduce(proportions, popCopy, numberOfChildren);
    }

    protected void rankSelection(int numberOfChildren) {

        double MIN = 0.5;
        double MAX = 1.5;

        ArrayList<Double> proportions = new ArrayList<>();
        ArrayList<Individual> popCopy = new ArrayList<>(population);
        popCopy.sort(Individual::compareTo);

        for (int rank = 1; rank < popCopy.size()+1; rank++) {
            proportions.add(MIN + (MAX - MIN) * (rank - 1.0) / (popCopy.size() - 1));
        }

        rouletteWheelAndReproduce(proportions, popCopy, numberOfChildren);
    }

    protected void tournamentSelectionAndReproduce(int numberOfChildren, int tournamentSize) {

        List<Individual> popCopy = new ArrayList<>(population);

        Individual parent1 = null;

        for (int round = 0; round < numberOfChildren; round++) {

            List<Individual> group = new ArrayList<>(tournamentSize);

            int[] indices = random.ints(tournamentSize, 0, popCopy.size()).toArray();

            for (int i : indices) {
                group.add(popCopy.get(i));
            }

            Individual p = getTournamentWinner(group);

            if (parent1 == null) {
                parent1 = p;
            } else {
                Individual child = parent1.reproduce(random, p);

                while (population.contains(child)) {
                    System.err.println("Collision");
                    child = parent1.reproduce(random, p);
                }
                population.add(child);

                Individual child2 = parent1.reproduce(random, p);

                while (population.contains(child2)) {
                    System.err.println("Collision");
                    child2 = parent1.reproduce(random, p);
                }
                population.add(child2);

                parent1 = null;
            }
        }

    }

    private Individual getTournamentWinner(List<Individual> group) {

        if (TOURNAMENT_EPSILON > random.nextFloat()) {
            // pick random winner
            return group.get(random.nextInt(group.size()));
        } else {
            // pick best fitness
            return group.stream().max(Individual::compareTo).orElse(null);
        }
    }

    public void printStats() {
        population.forEach(individual -> System.out.println(individual.phenotype + ", fitness=" + individual.fitness));
    }


    public abstract Individual getCurrentBestFitIndividual();
    public abstract Individual findSolution();
}
