package Ov4;

import Ov2.Individual;

import java.util.*;

/**
 * Created by Anders on 21.03.2016.
 */
@SuppressWarnings("Duplicates")
public class AdvancedNeuralNet extends Individual {

    public static double CROSSOVER_RATE = 0.6;
    public static double MUTATION_RATE = 0.3;

    public static int[] nodesInLayer = {7, 2, 2};

    private static final int PARAMETER_BITSIZE = 8;

    // every node (including bias) has an index starting with 0
    // the arraylist is the from
    // the hashmap key is to
    // the hashmap value is the weight
    private ArrayList<HashMap<Integer, Float>> phenotype;
    private ArrayList<Float> gains;
    private ArrayList<Float> timeConstants;

    private ArrayList<Float> activationForNeurons;

    public AdvancedNeuralNet(BitSet genotype) {
        super(getGenotypeBitSize());
        this.genotype = (BitSet) genotype.clone();
    }

    public AdvancedNeuralNet(Random random) {
        super(getGenotypeBitSize());
    }

    private static int getGenotypeBitSize() {
        // normal weights (feed forward)
        int sum = 0;
        for (int i = 0; i < nodesInLayer.length - 1; i++) {
            sum += nodesInLayer[i+1] * nodesInLayer[i];
        }

        // bias weights, gains and time constants
        for (int i = 1; i < nodesInLayer.length; i++) {
            sum += 3 * nodesInLayer[i];
        }

        // interconnected weights
        for (int i = 1; i < nodesInLayer.length; i++) {
            sum += Math.pow(nodesInLayer[i], 2);
        }

        return sum * PARAMETER_BITSIZE;
    }

    @Override
    public void growBitPhenotype() {
        if (phenotype != null) {
            return;
        }


        phenotype = new ArrayList<>();
        gains = new ArrayList<>();
        timeConstants = new ArrayList<>();

        int nodesInEarlierLayers = 0;
        int geno_start = 0;

        // set weights (feed forward)
        for (int layer = 0; layer < nodesInLayer.length - 1; layer++) {
            for (int fromNode = 0; fromNode < nodesInLayer[layer]; fromNode++) {
                HashMap<Integer, Float> fromHashMap = new HashMap<>();
                for (int toNode = 0; toNode < nodesInLayer[layer + 1]; toNode++) {

                    float weightValue = 0.0f;
                    for (int index = genotype.nextSetBit(geno_start); index < geno_start + PARAMETER_BITSIZE && index != -1; index = genotype.nextSetBit(index + 1)) {
                        weightValue += Math.pow(2, (PARAMETER_BITSIZE-1) - (index % PARAMETER_BITSIZE));
                    }

                    fromHashMap.put(nodesInEarlierLayers + nodesInLayer[layer] + toNode, (weightValue - 128f) * 5f / 128f);
                    geno_start += PARAMETER_BITSIZE;
                }
                phenotype.add(fromHashMap);
            }
            nodesInEarlierLayers += nodesInLayer[layer];
        }

        // adding the nodes in the final layer (have no outgoing connections yet)
        for (int nodeInFinalLayer = 0; nodeInFinalLayer < nodesInLayer[nodesInLayer.length - 1]; nodeInFinalLayer++) {
            phenotype.add(new HashMap<>());
        }

        nodesInEarlierLayers = nodesInLayer[0];
        // interconnected
        for (int layer = 1; layer < nodesInLayer.length; layer++) {
            for (int fromNode = 0; fromNode < nodesInLayer[layer]; fromNode++) {
                for (int toNode = 0; toNode < nodesInLayer[layer]; toNode++) {

                    float weightValue = 0.0f;
                    for (int index = genotype.nextSetBit(geno_start); index < geno_start + PARAMETER_BITSIZE && index != -1; index = genotype.nextSetBit(index + 1)) {
                        weightValue += Math.pow(2, (PARAMETER_BITSIZE-1) - (index % PARAMETER_BITSIZE));
                    }

                    phenotype.get(nodesInEarlierLayers + fromNode).put(nodesInEarlierLayers + toNode, (weightValue - 128f) * 5f / 128f);
                    geno_start += PARAMETER_BITSIZE;

                }

            }
        }

        nodesInEarlierLayers = nodesInLayer[0];
        // set bias weights (not for inputlayer)
        HashMap<Integer, Float> biasMap = new HashMap<>();
        for (int layer = 1; layer < nodesInLayer.length; layer++) {
            for (int toNode = 0; toNode < nodesInLayer[layer]; toNode++) {
                float weightValue = 0.0f;
                for (int index = genotype.nextSetBit(geno_start); index < geno_start + PARAMETER_BITSIZE && index != -1; index = genotype.nextSetBit(index + 1)) {
                    weightValue += Math.pow(2, (PARAMETER_BITSIZE-1) - (index % PARAMETER_BITSIZE));
                }

                biasMap.put(nodesInEarlierLayers + toNode, (weightValue - 255f) * 10f / 255f);
                geno_start += PARAMETER_BITSIZE;
            }
            nodesInEarlierLayers += nodesInLayer[layer];
        }
        phenotype.add(biasMap);

        // gain (not for inputlayer)
        for (int layer = 1; layer < nodesInLayer.length; layer++) {
            for (int node = 0; node < nodesInLayer[layer]; node++) {

                float weightValue = 0.0f;
                for (int index = genotype.nextSetBit(geno_start); index < geno_start + PARAMETER_BITSIZE && index != -1; index = genotype.nextSetBit(index + 1)) {
                    weightValue += Math.pow(2, (PARAMETER_BITSIZE-1) - (index % PARAMETER_BITSIZE));
                }

                gains.add(weightValue * 4f / 255f + 1.0f);
                geno_start += PARAMETER_BITSIZE;

            }
        }

        // timeConst (not for inputlayer
        for (int layer = 1; layer < nodesInLayer.length; layer++) {
            for (int node = 0; node < nodesInLayer[layer]; node++) {

                float weightValue = 0.0f;
                for (int index = genotype.nextSetBit(geno_start); index < geno_start + PARAMETER_BITSIZE && index != -1; index = genotype.nextSetBit(index + 1)) {
                    weightValue += Math.pow(2, (PARAMETER_BITSIZE-1) - (index % PARAMETER_BITSIZE));
                }

                timeConstants.add(weightValue / 255f + 1f);
                geno_start += PARAMETER_BITSIZE;

            }
        }


    }


    /**
     * run when testing for fitness
     * returns the prefered direction to go
     */
    public int runNeuralNetTimeStep(int[] inputSensors) {

        if (activationForNeurons == null) {
            activationForNeurons = new ArrayList<>();

            for (int i = 0; i < phenotype.size()-1; i++) {
                activationForNeurons.add(0f);
            }
            activationForNeurons.add(1f);
        }

        for (int i = 0; i < inputSensors.length; i++) {
            activationForNeurons.set(i, (float) inputSensors[i]);
        }

        // temp are the state values for the previous timestep
        ArrayList<Float> temp = (ArrayList<Float>) activationForNeurons.clone();


        // clear current to ready for summing (except for input)
        for (int i = inputSensors.length; i < phenotype.size()-1; i++) {
            activationForNeurons.set(i, 0f);
        }
        activationForNeurons.set(activationForNeurons.size()-1, 1f);

        // calculate s with bias
        for (int fromNode = 0; fromNode < phenotype.size() - 1; fromNode++) {
            for (int toNode : phenotype.get(fromNode).keySet()) {
                activationForNeurons.set(toNode, activationForNeurons.get(toNode) + ((fromNode < inputSensors.length || fromNode == phenotype.size()-1)? temp.get(fromNode) : applySigmoid(gains.get(fromNode-inputSensors.length), temp.get(fromNode))) * phenotype.get(fromNode).get(toNode));
            }
        }

        // calculate dy/dt without bias
        for (int i = inputSensors.length; i < activationForNeurons.size() - 1; i++) {
            activationForNeurons.set(i, (activationForNeurons.get(i) - temp.get(i)) / timeConstants.get(i-inputSensors.length));
        }

        // calculate new y without bias
        for (int i = inputSensors.length; i < activationForNeurons.size() - 1; i++) {
            activationForNeurons.set(i, temp.get(i) + activationForNeurons.get(i));
        }

        int indexOfFirstOutput = (phenotype.size() - 1) - nodesInLayer[nodesInLayer.length-1];

        List<Float> outputs = activationForNeurons.subList(indexOfFirstOutput, phenotype.size()-1);

        return outputs.indexOf(outputs.stream().max(Float::compare).get());
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

    private float applySigmoid(float gain, float input) {
        return (float) (1.0 / (1.0 + Math.exp(-gain * input)));
    }

}
