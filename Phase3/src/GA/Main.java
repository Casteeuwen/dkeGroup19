package GA;
import UI.Other_Main_Drawer;
import javafx.scene.paint.Color;


import java.util.ArrayList;

/**
 * Tester class for the genetic & random filling algorithms
 */
public class Main {

    public static final int ITERATIONS = 20;
    public static final int NUMBER_OF_ELITES = 20;
    public static final double CROSSOVER_PROBABILITY = 0.7;
    public static final double MUTATION_PROBABILITY = 0.5;


    public static void main(String[] args) throws Exception { }


    /**
     * Start the genetic algorithm
     */
    public static void startAlgo(double valA, double valB, double valC, int nrA, int nrB, int nrC) throws Exception {
        if (nrA == 100000) {nrA = 80; valA = 3;}
        if (nrB == 100000) {nrB = 0; valB = 4;}
        if (nrC == 100000) {nrC = 0; valC = 5;}


        Logic logic = new Logic();

        ArrayList<Cargo> cargos = new ArrayList<>();
        int max = 0;


        // Generate boxes to be send to the algorithm
        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < nrA; i++) {
            boxes.add(new Box(1.0, 1.0, 2.0, valA, "A"));
        }
        for (int i = 0; i < nrB; i++) {
            boxes.add(new Box(1.0, 1.5, 2.0, valB, "B"));
        }
        for (int i = 0; i < nrC; i++) {
            boxes.add(new Box(1.5, 1.5, 1.5, valC, "C"));
        }

        // Load boxes into genetic algorithm
        GeneticAlgorithm ga = new GeneticAlgorithm(boxes, boxes.size());

        // Create initial population
        Chromosome[] population = ga.getPopulation().getChromosomes();

        int populationSize = population.length;
//        int populationSize = 1;

        double[] fitnessValues = new double[boxes.size()];

        Chromosome[] elites = new Chromosome[NUMBER_OF_ELITES];
        double[] fitnessOfElites = new double[NUMBER_OF_ELITES];

        for (int i = 0; i < fitnessOfElites.length; i++) {
            fitnessOfElites[i] = 1;
        }

        Cargo cargo;

        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Iteration " + i);

            // Start packing
            for (int j = 0; j < populationSize; j++) {
//                System.out.println("Chromosome: " + j + " of " + populationSize);
                    cargo = logic.packBoxes(boxes, population[j].getGenes());
                    fitnessValues[j] = logic.calculateFitness(cargo);
//                    System.out.println(cargo);
                    if (cargo.getTotalValue() > max) {
                        max = cargo.getTotalValue();
                    }
                    cargos.add(cargo);
            }

//            System.out.println("Fitness values after packing: " + Arrays.toString(fitnessValues));

            Chromosome[] populationWithElites = new Chromosome[populationSize + NUMBER_OF_ELITES];
            double[] fitnessWithElites = new double[populationSize + NUMBER_OF_ELITES];

            for (int x = 0; x < populationSize + NUMBER_OF_ELITES; x++) {
                if (x >= populationSize) {
                    populationWithElites[x] = elites[x - populationSize];
                    fitnessWithElites[x] = fitnessOfElites[x - populationSize];
                } else {
                    populationWithElites[x] = population[x];
                    fitnessWithElites[x] = fitnessValues[x];
                }
            }
//            System.out.println("Fitness values + elites " + Arrays.toString(fitnessWithElites));


            if (i != ITERATIONS - 1) {

                // Get indexes of best performing chromosomes
                int[] bestChromosomes = ga.getElites(fitnessWithElites, NUMBER_OF_ELITES);
//                System.out.println("Elites indexes: " + Arrays.toString(bestChromosomes));


                // Get elites & their fitness from the indexes
                for (int k = 0; k < NUMBER_OF_ELITES; k++) {
                    elites[k] = populationWithElites[bestChromosomes[k]];
                    fitnessOfElites[k] = fitnessWithElites[bestChromosomes[k]];
                }
//                System.out.println("Elites fitness " + Arrays.toString(fitnessOfElites));


                // Marking elites & fitness values for removal
                for (int j = 0; j < NUMBER_OF_ELITES; j++) {
                    for (int k = 0; k < populationWithElites.length; k++) {
                        if (populationWithElites[k] == elites[j]) {
                            populationWithElites[k] = null;
                        }
                        if (fitnessWithElites[k] == fitnessOfElites[j]) {
                            fitnessWithElites[k] = -1;
                        }
                    }
                }

                // Removing elites from population & fitness values
                for (int l = 0; l < populationSize; l++) {
                    if (populationWithElites[l] != null) {
                        population[l] = populationWithElites[l];
                    }
                    if (fitnessWithElites[l] != -1) {
                        fitnessValues[l] = fitnessWithElites[l];
                    }
                }

                // Sending rest of population to tournament
                Chromosome[] chromosomesToReproduce = ga.select(population, fitnessValues);

                // Performing the crossover of the population
                Chromosome[] crossover = ga.crossoverPopulation(chromosomesToReproduce, CROSSOVER_PROBABILITY);

                // Mutating the new, post-crossover population
                Chromosome[] newPopulation = ga.mutatePopulation(crossover, MUTATION_PROBABILITY);

                population = newPopulation.clone();
            }
        }

        // Get the best solution from all
        System.out.println("Maximum value:" + max);
        for (Cargo c : cargos) {
            if (c.getTotalValue() == max) {
//                System.out.println(c);
//                System.out.println(c.getBoxesInfo());
                displayBoxes(c);
            }

        }
    }

    /**
     * Displays the boxes on the GUI
     * @param cargo
     */
    public static void displayBoxes(Cargo cargo){
        Other_Main_Drawer.clearScene();
        for (Box b : cargo.getBoxes()) {
            Color boxColor;

            if (b.getName() == "A") {
                boxColor = Other_Main_Drawer.BOX_GREEN;
            } else if (b.getName() == "B") {
                boxColor = Other_Main_Drawer.BOX_RED;
            } else if (b.getName() == "C") {
                boxColor = Other_Main_Drawer.BOX_BLUE;
            } else {
                boxColor = Other_Main_Drawer.BOX_DARK;
            }

            Other_Main_Drawer.addBox(b.getWidth(), b.getHeight(), b.getLength(), b.getOrigin()[2], b.getOrigin()[1], b.getOrigin()[0], boxColor, true);
        }
    }

}
