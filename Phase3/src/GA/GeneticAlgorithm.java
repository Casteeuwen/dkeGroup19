package GA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Algorithm in charge of performing selection, crossover and mutation of chromosomes, as well as determining the elites
 */
public class GeneticAlgorithm {
    private Population population;

    /**
     * Constructor
     * @param boxes list of boxes to be analysed
     * @param size amount of boxes
     */
    public GeneticAlgorithm(ArrayList<Box> boxes, int size) {
        population = new Population(size, boxes);
    }


    /**
     * Gets the best performing chromosomes from the population
     * @param chromosomeScores array containing performance value of the chromosomes
     * @param size
     * @return An array with the indices of the best performing chromosomes
     */
    public int[] getElites(double[] chromosomeScores, int size) {
        int[] eliteIndexes = new int[size];
        double[] tmp = chromosomeScores.clone();

        Arrays.sort(tmp);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < chromosomeScores.length; j++) {
                if (tmp[i] == chromosomeScores[j] && !contains(eliteIndexes, j)) {
                    eliteIndexes[i] = j;
                }
            }
        }

        return eliteIndexes;
    }

    /**
     * Helper method, checks if a primitive array contains a given number
     * @param arr
     * @param nr
     * @return True/False
     */
    public boolean contains(int[] arr, int nr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == nr) {
                return true;
            }
        }
        return false;
    }

    /**
     * Selects two random chromosomes from the whole population, finds the best performing one and creates a new population based on those
     * @param population given population from which the selection will be done
     * @param fitness array containing the performances of the chromosomes in the population
     * @return a new generation of chromosomes
     */
    public Chromosome[] select(Chromosome[] population, double[] fitness) {
        System.out.println("[*] Performing selection");
        Chromosome[] newGeneration = new Chromosome[population.length];
        Random random = new Random();

        for (int i = 0; i < population.length; i++) {

            // Find a better random method
            int r1 = random.nextInt(population.length);
            int r2 = random.nextInt(population.length);
            double minFitness = Math.min(fitness[r1], fitness[r2]);

            if (minFitness == fitness[r1]) {
                newGeneration[i] = population[r1];
            } else {
                newGeneration[i] = population[r2];
            }
        }

        return newGeneration;
    }

    /**
     * Mutates a chromosome by randomly switching gene values
     * @param chromosome chromosome to be mutated
     * @return a mutated chromosome
     */
    public Chromosome mutateChromosome(Chromosome chromosome) {
        Random random = new Random();
        int numberOfBoxes = chromosome.getGenes().length;

        int[] newGenes = chromosome.getGenes().clone();

        int[] reversedGenes = newGenes.clone();
        population.reverse(reversedGenes);

        int index1 = random.nextInt(numberOfBoxes);
        int index2 = random.nextInt(numberOfBoxes);

        newGenes[index1] = reversedGenes[index1];
        newGenes[index2] = reversedGenes[index2];

        chromosome.setGenes(newGenes);

        return chromosome;
    }

    /**
     * Mutate the whole population and return a new, mutated generation
     * @param population population to be mutated
     * @param probability chance of mutation
     * @return a new mutated generation
     */
    public Chromosome[] mutatePopulation(Chromosome[] population, double probability) {
        System.out.println("[*] Performing mutation");
        Chromosome[] newGeneration = new Chromosome[population.length];
        int[] mutationIndexes = new int[population.length];

        for (int i = 0; i < population.length; i++) {
            if (Math.random() < probability) {
                mutationIndexes[i] = 1;
            } else {
                mutationIndexes[i] = 0;
            }
        }

        for (int i = 0; i < mutationIndexes.length; i++) {
            if (mutationIndexes[i] == 1) {
                newGeneration[i] = mutateChromosome(population[i]);
            } else {
                newGeneration[i] = population[i];
            }
        }

        return newGeneration;
    }

    /**
     * Create a new chromosome via crossover by randomly mixing genes of two parents
     * @param mom first parent
     * @param dad second parent
     * @return a child
     */
    public Chromosome crossoverChromosome(Chromosome mom, Chromosome dad) {
        int numberOfBoxes = mom.getGenes().length;
        int[] childGenes = new int[numberOfBoxes];
        Random r = new Random();

        // Get two random cutting points
        int randomPoint1 = r.nextInt(numberOfBoxes);
        int randomPoint2 = r.nextInt(numberOfBoxes);


        int minLimit = Math.min(randomPoint1, randomPoint2);
        int maxLimit = Math.max(randomPoint1, randomPoint2);

        for (int i = 0; i < numberOfBoxes; i++) {
            if (i >= minLimit && i <= maxLimit) {
                childGenes[i] = mom.getGenes()[i];
            } else {
                childGenes[i] = dad.getGenes()[i];
            }
        }

        return new Chromosome(childGenes);
    }

    /**
     * Perform crossover on the entire population
     * @param population population to be crossed over
     * @param probability chance of crossover
     * @return a new generation
     */
    public Chromosome[] crossoverPopulation(Chromosome[] population, double probability) {
        System.out.println("[*] Performing crossover");
        Chromosome[] nextGeneration = new Chromosome[population.length];
        Random r = new Random();

        int j = 0;
        for (int i = population.length; i > 0; i -= 2) {
            if (i > 1) {
                int index1 = r.nextInt(i);
                int index2 = r.nextInt(i);

                if (Math.random() < probability) {
                    Chromosome firstChild = crossoverChromosome(population[index1], population[index2]);
                    Chromosome secondChild = crossoverChromosome(population[index2], population[index1]);

                    nextGeneration[j] = firstChild; j++;
                    nextGeneration[j] = secondChild; j++;
                } else {
                    nextGeneration[j] = population[index1]; j++;
                    nextGeneration[j] = population[index2]; j++;
                }
            } else {
                nextGeneration[j] = population[i];
            }
        }

        return nextGeneration;
    }

    public Population getPopulation() {
        return population;
    }
}
