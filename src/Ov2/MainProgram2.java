package Ov2;

import java.security.Principal;

/**
 * Created by Anders on 22.02.2016.
 */
public class MainProgram2 {

    public static void main(String[] args) {
        EvolutionaryAlg alg = new OneMaxAlg(Individual.class);

        for (int generation = 0; generation < 50; generation++) {
            alg.runNextGeneration();

            Individual i;
            if ((i = alg.findSolution()) != null) {
                System.out.println(i.genotype);
                break;
            }
        }

//        alg.printStats();
    }

}
