package Ov2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Anders on 22.02.2016.
 */
public class MainProgram2 {

    public static void main(String[] args) throws IOException {

        BufferedReader br;

        while (true) {
            br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Pick one of {onemax, lolz, surprise} by typing the first letter: ");
            String algorithm = br.readLine();

            System.out.println("Pick one of {full replacement, overproduction, mixed} by typing the first letter: ");
            EvolutionaryAlg.selectAdultMethod = br.readLine();

            System.out.println("Pick one of {fitness prop, sigma, rank, tournament} by typing the first letter: ");
            EvolutionaryAlg.selectParentMethod = br.readLine();

            if (EvolutionaryAlg.selectParentMethod.equals("t")) {
                System.out.println("Tournament size?");
                EvolutionaryAlg.TOURNAMENT_SIZE = Integer.parseInt(br.readLine());
            }

            System.out.println("How many generations?");
            int numIterations = Integer.valueOf(br.readLine());

            System.out.println("Max population?");
            EvolutionaryAlg.MAX_POPULATION_SIZE = Integer.valueOf(br.readLine());

            System.out.println("How many children?");
        EvolutionaryAlg.numOfChildren = Integer.valueOf(br.readLine());

            System.out.println("Mutation rate??");
            Individual.MUTATION_RATE = Double.parseDouble(br.readLine());

            System.out.println("Crossover rate?");
            Individual.CROSSOVER_RATE = Double.parseDouble(br.readLine());

            EvolutionaryAlg alg;
            switch (algorithm) {
                case "o":
                    System.out.println("Size of genotype?");
                    Individual.GENOTYPE_BIT_SIZE = Integer.valueOf(br.readLine());
                    alg = new OneMaxAlg();
                    break;
                case "l":
                    System.out.println("Size of genotype?");
                    Individual.GENOTYPE_BIT_SIZE = Integer.valueOf(br.readLine());

                    System.out.println("z?");
                    LolzAlg.z = Integer.valueOf(br.readLine());
                    alg = new LolzAlg();
                    break;
                case "s":
                    System.out.println("Size?");
                    SurprisingSeqAlg.SIZE = Integer.valueOf(br.readLine());
                    Individual.GENOTYPE_BIT_SIZE = SurprisingSeqAlg.SIZE * 2;

                    System.out.println("Local-surprising (y/n)?");
                    SurprisingSeqAlg.onlyLocal = br.readLine().equals("y");

                    System.out.println("Timelimit (in seconds)?");
                    SurprisingSeqAlg.timelimit = Integer.parseInt(br.readLine());

                    alg = new SurprisingSeqAlg();
                    break;
                default:
                    throw new RuntimeException();
            }

            for (int generation = 0; generation < numIterations; generation++) {
                alg.runNextGeneration();

                Individual individual;
                if ((individual = alg.findSolution()) != null) {
                    break;
                }
            }
        }

    }

}
