package Ov4;

import Ov2.Individual;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by Anders on 21.03.2016.
 */
public class AdvancedNeuralNet extends Individual {

//    each weight is determined by 8-bits in the genotype bit-string
//    the 8 bits symbolize values from -1 to 1

    public static int GENOTYPE_BIT_SIZE = 8*36;
    public static double CROSSOVER_RATE = 0.5;
    public static double MUTATION_RATE = 0.5;

    private static final int SIZE_OF_WEIGHT = 8;

    private ArrayList<ArrayList<ArrayList<Float>>> phenotype;

    public AdvancedNeuralNet(BitSet genotype) {
        super(GENOTYPE_BIT_SIZE);
        this.genotype = (BitSet) genotype.clone();
    }

    public AdvancedNeuralNet(Random random) {
        super(GENOTYPE_BIT_SIZE);
    }

    @Override
    public void growBitPhenotype() {
        if (phenotype == null) {
            phenotype = new ArrayList<>();
        }

        int previousNodeSize = 6;
        ArrayList<ArrayList<Float>> currentLayer = new ArrayList<>();
        int nodesInNextLayer = 14;
        int counter = 0;

        for (int layer = 0; layer < 2; layer++) {

            for (int node = 0; node < previousNodeSize; node++) {
                ArrayList<Float> currentNodeWeights = new ArrayList<>();

                for (int nextLayerNode = 0; nextLayerNode < nodesInNextLayer; nextLayerNode++) {

                    int geno_start = layer * previousNodeSize * nodesInNextLayer + node * nodesInNextLayer + nextLayerNode;

                    float weightValue = 0.0f;
//                    System.out.println("genotype: " + genotype);
                    for (int index = genotype.nextSetBit(geno_start); index < geno_start + SIZE_OF_WEIGHT && index != -1; index = genotype.nextSetBit(index + 1)) {
                        weightValue += Math.pow(2, (SIZE_OF_WEIGHT-1) - (index % SIZE_OF_WEIGHT));
                    }
//                    System.out.println("weightValue: " + weightValue);
                    weightValue = (weightValue - 128.0f) / 128.0f;

                    counter ++;
                    currentNodeWeights.add(weightValue);
                }

                currentLayer.add(currentNodeWeights);
            }


            previousNodeSize = nodesInNextLayer;
            nodesInNextLayer = 3;

            phenotype.add(currentLayer);
            currentLayer = new ArrayList<>();
        }
//        System.out.println("Counter: " + counter);


    }


    public AdvancedNeuralNet reproduce(Random random, Individual parent2) {

//        System.out.println("CROSSOVER_RATE: " + CROSSOVER_RATE);
//        System.out.println("MUTATION_RATE: " + MUTATION_RATE);

        AdvancedNeuralNet child = new AdvancedNeuralNet((BitSet) this.genotype.clone());
        if (random.nextDouble() < CROSSOVER_RATE) {
            child.crossover(random, (BitSet) parent2.getGenotype().clone());
        }
        if (random.nextDouble() < MUTATION_RATE) {
            child.mutate(random);
        }

        return child;
    }

    /**
     * run when testing for fitness
     * returns the prefered direction to go
     */
    public int runNeuralNet(int[] foodAndPoison) {
        assert foodAndPoison.length == 5;

        ArrayList<Float> activationForNeurons = new ArrayList<>();
        for (int i : foodAndPoison) {
            activationForNeurons.add((float) i);
        }

        for (int layer = 0; layer < phenotype.size(); layer++) {

            ArrayList<Float> temp = new ArrayList<>();

            for (int toNode = 0; toNode < phenotype.get(layer).get(0).size(); toNode++) {
                float sumOfInputs = 0f;

                for (int node = 0; node < phenotype.get(layer).size(); node++) {

                    if(activationForNeurons.size() <= node) continue;// int node kan og vil som regel være større enn lengden på activationForNeurons
                    sumOfInputs += phenotype.get(layer).get(node).get(toNode) * activationForNeurons.get(node);
                }

                temp.add(applySigmoid(sumOfInputs));
            }

            activationForNeurons = temp;
        }


        // return argmax
//        System.out.println("activationForNeurons: " + activationForNeurons);

        return activationForNeurons.indexOf(activationForNeurons.stream().max(Float::compare).get());
    }

    /**
     *
     * @param input: the sum of inputs
     */
    private float applySigmoid(float input) {
        return (float) (1.0 / (1.0 + Math.exp(-2.0 * input)));
    }

}
