package GA;
import UI.Other_Main_Drawer;
import javafx.scene.paint.Color;


import java.util.ArrayList;

/**
 * Tester class for the genetic & random filling algorithms
 */
public class Main {

    public static final int ITERATIONS = 5;
    public static final int NUMBER_OF_ELITES = 20;
    public static final double CROSSOVER_PROBABILITY = 0.8;
    public static final double MUTATION_PROBABILITY = 0.6;


    public static void main(String[] args) { }

    /**
     * Start filling the container with randomly chosen boxes
     */
    public static void startRandomFill() {
        Logic logic = new Logic();
        Cargo cargo = logic.fillRandomly();
        displayBoxes(cargo);
    }

    /**
     * Start the genetic algorithm
     */
    public static void startAlgo() {
        Logic logic = new Logic();

        ArrayList<Cargo> cargos = new ArrayList<>();
        int max = 0;


        // Generate boxes to be send to the algorithm
        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
            boxes.add(new Box(10, 10, 20, 3, "A"));
        }
//        for (int i = 0; i < 60; i++) {
//            boxes.add(new Box(10, 15, 20, 4, "B"));
//        }
        for (int i = 0; i < 10; i++) {
            boxes.add(new Box(15, 15, 15, 5, "C"));
        }

        // Load boxes into genetic algorithm
        GeneticAlgorithm ga = new GeneticAlgorithm(boxes, boxes.size());

        // Create initial population
        Chromosome[] population = ga.getPopulation().getChromosomes();

        int populationSize = population.length;

        double[] fitnessValues = new double[boxes.size()];

        Chromosome[] elites = new Chromosome[NUMBER_OF_ELITES];
        double[] fitnessOfElites = new double[NUMBER_OF_ELITES];

        for (int i = 0; i < fitnessOfElites.length; i++) {
            fitnessOfElites[i] = 1;
        }

        for (int i = 0; i < ITERATIONS; i++) {
            System.out.println("Iteration " + i);

            // Start packing
            for (int j = 0; j < populationSize; j++) {
//                System.out.println("Chromosome: " + j + " of " + populationSize);
                try {
                    Cargo cargo = logic.packBoxes(boxes, population[j].getGenes());
                    fitnessValues[j] = logic.calculateFitness(cargo);
//                    System.out.println(cargo);
                    if (cargo.getTotalValue() > max) {
                        max = cargo.getTotalValue();
                    }
                    cargos.add(cargo);
                } catch (NullPointerException e) {

                }
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
                displayBoxes(c);
                displayBoxes(c);
            }
        }
    }

    /**
     * Displays the boxes on the GUI
     * @param cargo
     */public static void displayBoxes(Cargo cargo){
        Other_Main_Drawer.clearScene();
        for (Box b : cargo.getBoxes1()) {
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

            Other_Main_Drawer.addBox(b.getWidth(), b.getHeight(), b.getLength(), b.getCoords()[2], b.getCoords()[1], b.getCoords()[0], boxColor, false);
        }
    }

}
