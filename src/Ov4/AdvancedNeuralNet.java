package Ov4;

import Ov2.Individual;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Created by Anders on 21.03.2016.
 */
@SuppressWarnings("Duplicates")
public class AdvancedNeuralNet extends Individual {

//    each weight is determined by 8-bits in the genotype bit-string
//    the 8 bits symbolize values from -1 to 1

    public static double CROSSOVER_RATE = 0.8;
    public static double MUTATION_RATE = 0.1;

    public static int[] nodesInLayer = {5, 2, 2};

    private static final int SIZE_OF_WEIGHT = 8;

    private ArrayList<ArrayList<ArrayList<Float>>> phenotype;

    public AdvancedNeuralNet(BitSet genotype) {
        super(getGenotypeBitSize());
        this.genotype = (BitSet) genotype.clone();
    }

    public AdvancedNeuralNet(Random random) {
        super(getGenotypeBitSize());
    }

    private static int getGenotypeBitSize() {
        int prev = 6;
        int sum = 0;
        for (int i = 0; i < nodesInLayer.length; i++) {
            sum += prev * nodesInLayer[i];
            prev = nodesInLayer[i];
        }
        return sum;
    }

    @Override
    public void growBitPhenotype() {
        if (phenotype != null) {
            return;
        }

        phenotype = new ArrayList<>();

        int previousNodeSize = 6;
        ArrayList<ArrayList<Float>> currentLayer = new ArrayList<>();

        for (int layer = 0; layer < nodesInLayer.length; layer++) {

            for (int node = 0; node < previousNodeSize; node++) {
                ArrayList<Float> currentNodeWeights = new ArrayList<>();

                for (int nextLayerNode = 0; nextLayerNode < nodesInLayer[layer]; nextLayerNode++) {

                    int geno_start = layer * previousNodeSize * nodesInLayer[layer] + node * nodesInLayer[layer] + nextLayerNode;

                    float weightValue = 0.0f;
                    for (int index = genotype.nextSetBit(geno_start); index < geno_start + SIZE_OF_WEIGHT && index != -1; index = genotype.nextSetBit(index + 1)) {
                        weightValue += Math.pow(2, (SIZE_OF_WEIGHT-1) - (index % SIZE_OF_WEIGHT));
                    }
//                    System.out.println("weightValue: " + weightValue);
                    weightValue = (weightValue - 128.0f) / 128.0f;

                    currentNodeWeights.add(weightValue);
                }

                currentLayer.add(currentNodeWeights);
            }


            previousNodeSize = nodesInLayer[layer];

            phenotype.add(currentLayer);
            currentLayer = new ArrayList<>();
        }

    }


    /**
     * run when testing for fitness
     * returns the prefered direction to go
     */
    public int runNeuralNet(int[] foodAndPoison) {
        assert foodAndPoison.length == 6;

        ArrayList<Float> activationForNeurons = new ArrayList<>();
        for (int i : foodAndPoison) {
            activationForNeurons.add((float) i);
        }

        for (int layer = 0; layer < phenotype.size(); layer++) {

            ArrayList<Float> temp = new ArrayList<>();

            for (int toNode = 0; toNode < phenotype.get(layer).get(0).size(); toNode++) {
                float sumOfInputs = 0f;

                for (int node = 0; node < phenotype.get(layer).size(); node++) {
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

    @Override
    public Individual reproduce(Random random, Individual parent2) {
        AdvancedNeuralNet child = new AdvancedNeuralNet((BitSet) this.genotype.clone());
        boolean didCrossover = false;
        if (random.nextDouble() < CROSSOVER_RATE) {
            didCrossover = true;
            child.crossover(random, (BitSet) ((AdvancedNeuralNet)parent2).genotype.clone());
        }
        if (!didCrossover || random.nextDouble() < MUTATION_RATE) {
            child.mutate(random);
        }

        return child;
    }

    /**
     *
     * @param input: the sum of inputs
     */

    private float applySigmoid(float input) {
        return (float) (1.0 / (1.0 + Math.exp(-2.0 * input)));
    }

}
