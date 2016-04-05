package Ov3;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by Anders on 21.03.2016.
 */
public class NeuralNet {

//    each weight is determined by 8-bits in the genotype bit-string
//    the 8 bits symbolize values from -1 to 1

//    public static int GENOTYPE_BIT_SIZE;
    public static double CROSSOVER_RATE;
    public static double MUTATION_RATE;

    public static final int SIZE_OF_WEIGHT = 8;

    protected BitSet genotype;
    protected ArrayList<ArrayList<ArrayList<Float>>> phenotype;
    protected double fitness;
    protected boolean isAdult;

    public NeuralNet(BitSet genotype) {
        this.genotype = (BitSet) genotype.clone();
    }

    public void growPhenotype() {
        if (phenotype == null) {
            phenotype = new ArrayList<>();
        }

        int layer = 0;
        int previousNodeSize = 6;
        ArrayList<ArrayList<Float>> currentLayer = new ArrayList<>();
        int nodesInNextLayer = 5;

        for (int node = 0; node < previousNodeSize; node++) {
            ArrayList<Float> currentNodeWeights = new ArrayList<>();

            for (int nextLayerNode = 0; nextLayerNode < nodesInNextLayer; nextLayerNode++) {

                int geno_start = layer * previousNodeSize * nodesInNextLayer + node * nodesInNextLayer + nextLayerNode;

                float weightValue = 0.0f;
                for (int index = genotype.nextSetBit(geno_start); index < geno_start + SIZE_OF_WEIGHT && index != -1; index = genotype.nextSetBit(index + 1)) {
                    weightValue += Math.pow(2, (SIZE_OF_WEIGHT-1) - ((index - geno_start) % SIZE_OF_WEIGHT));
                }
                weightValue = (weightValue - 128) / 128;

                currentNodeWeights.add(weightValue);
            }

            currentLayer.add(currentNodeWeights);
        }

        phenotype.add(currentLayer);

//        for (int index = genotype.nextSetBit(0); index != -1; index = genotype.nextSetBit(index + 1)) {
//            phenotype.set(index/SIZE_OF_WEIGHT, phenotype.get(index/SIZE_OF_WEIGHT) + (int) Math.pow(2, (SIZE_OF_WEIGHT-1) - (index % SIZE_OF_WEIGHT)));
//        }

    }

    /**
     * run when testing for fitness
     * fitness is the inverse distance to the goal
     */
    public void runNeuralNet(ArrayList<Boolean> food, ArrayList<Boolean> poison) {
        assert food.size() == 3 && poison.size() == 3;


    }

    public void reproduce(NeuralNet other) {

    }


}
