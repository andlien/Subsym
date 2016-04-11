package Ov2;

import java.util.*;

/**
 * Created by Anders on 23.02.2016.
 */
public class SurprisingSeqAlg extends EvolutionaryAlg {

    // bits required to represent the alphabet
    public static int SIZE;
    public static boolean onlyLocal;
    public static int timelimit;

    public int timeSinceLastIncrease;
    private Date time;

    public SurprisingSeqAlg() {
        super(Individual.class,0,0,0);

        time = new Date();

        assert Individual.GENOTYPE_BIT_SIZE % SIZE == 0;
    }

    @Override
    protected void developPhenotypes() {
        for (Individual individual : population) {
            if (individual.phenotype != null) {
                continue;
            }

            individual.phenotype = new ArrayList<>();
            for (int i = 0; i < Individual.GENOTYPE_BIT_SIZE / SIZE; i++) {
                individual.phenotype.add(0);
            }

            for (int index = individual.genotype.nextSetBit(0); index != -1; index = individual.genotype.nextSetBit(index + 1)) {
                individual.phenotype.set(index/SIZE, individual.phenotype.get(index/SIZE) + (int) Math.pow(2, (SIZE-1) - (index % SIZE)));
            }
        }
    }


    /**
     * compares all possible sequences with the same sequences except the duplicates has been removed
     */
    @Override
    protected void assignFitness() {

        for (Individual individual : population) {

            List<List<Integer>> lists = new ArrayList<>();

            for (int i = 0; i < individual.phenotype.size() - 1; i++) {
                for (int j = i + 1; j < individual.phenotype.size(); j++) {
                    ArrayList<Integer> thisList = new ArrayList<>();
                    thisList.add(individual.phenotype.get(i));
                    thisList.add((j - i) - 1); // dist between i and j
                    thisList.add(individual.phenotype.get(j));
                    lists.add(thisList);

                    if (onlyLocal) {
                        break;
                    }
                }
            }

            Set<List<Integer>> distinctList = new HashSet<>(lists);

            // scale fitness up
            individual.fitness = (double) distinctList.size() / lists.size();
        }

    }

//    @Override
//    protected void selectAdults() {
//        overProduction();
//    }
//
//    @Override
//    protected void selectParentsAndReproduce() {
//        sigmaScalingParentSelection(20);
//    }

    @Override
    public Individual getCurrentBestFitIndividual() {
        // if there exists an surprising sequence: store it and increase bitsize
        Optional<Individual> optional = population.stream().filter(individual -> individual.fitness == 1.0).findFirst();
        if (optional.isPresent()) {
            Individual.GENOTYPE_BIT_SIZE += SIZE;
            timeSinceLastIncrease = 0;
            return optional.get();
        } else {
            timeSinceLastIncrease++;
            return currentBest;
        }

    }

    @Override
    public Individual findSolution() {

        // end if we have had 25 generation without change in geno size
//        end if over timestop
        if ((new Date().getTime() - time.getTime()) / 1000 > timelimit) {
            return currentBest;
        }

        return null;
    }


}
