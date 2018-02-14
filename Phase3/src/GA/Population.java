package GA;
import java.util.*;

/**
 * Collection of chromosomes
 */
public class Population {
    private int numberOfBoxes, size;
    private Chromosome[] chromosomes;
    private Chromosome[] customChromosomes;

    /**
     * Constructor
     * @param size size of population
     * @param boxes list of boxes to be analysed
     */
    public Population(int size, ArrayList<Box> boxes) {
        this.size = size;
        this.chromosomes = new Chromosome[size];
        customChromosomes = createCustomChromosomes(boxes);
        numberOfBoxes = boxes.size();
        initiatePopulation();
    }

    /**
     * Create the first population
     */
    public void initiatePopulation() {
//        for (int i = 0; i < customChromosomes.length; i++) {
//            chromosomes[i] = customChromosomes[i];
//        }

//        for (int i = customChromosomes.length; i < size - customChromosomes.length; i++) {
        for (int i = 0; i < size; i++) {
            int[] boxes = new int[numberOfBoxes];
            for (int j = 0; j < numberOfBoxes - 1; j++) {
                boxes[j] = j + 1;
            }
            shuffle(boxes);

            chromosomes[i] = new Chromosome(boxes);
        }
    }

    /**
     * Create custom chromosomes - similar to a normal chromosome only sorted by length, height, width & volume respectively
     * @param boxes list of boxes
     * @return a list of 4 custom chromosomes
     */
    public Chromosome[] createCustomChromosomes(ArrayList<Box> boxes) {
        int[] lengthArray = new int[boxes.size()];
        int[] heightArray = new int[boxes.size()];
        int[] widthArray = new int[boxes.size()];
        int[] volumeArray = new int[boxes.size()];



        for (int i = 0; i < boxes.size(); i++) {

            lengthArray[i] = boxes.get(i).getLength();
            heightArray[i] = boxes.get(i).getHeight();
            widthArray[i] = boxes.get(i).getWidth();
            volumeArray[i] = boxes.get(i).getVolume();
        }

        Arrays.sort(lengthArray);
        reverse(lengthArray);
        Arrays.sort(heightArray);
        reverse(lengthArray);
        Arrays.sort(widthArray);
        reverse(lengthArray);
        Arrays.sort(volumeArray);
        reverse(lengthArray);

        Chromosome lengthChromosome = new Chromosome(lengthArray);
        Chromosome heightChromosome = new Chromosome(heightArray);
        Chromosome widthChromosome = new Chromosome(widthArray);
        Chromosome volumeChromosome = new Chromosome(volumeArray);

        return new Chromosome[]{lengthChromosome, heightChromosome, widthChromosome, volumeChromosome};
    }

    /**
     * Randomize the order of the boxes in the genes
     * @param input the genes to be randomized
     */
    public void shuffle(int[] input) {
        Random random = new Random();
        for (int i = 0; i < input.length; i++) {
            int index = random.nextInt(i + 1);
            int tmp = input[index];
            input[index] = input[i];
            input[i] = tmp;
        }
    }

    /**
     * Reverse a primitive array after sorting it with Arrays.sort()
     * @param input the array to be reversed
     */
    public void reverse(int[] input) {
        int last = input.length - 1;
        int middle = input.length / 2;
        for (int i = 0; i <= middle; i++) {
            int temp = input[i];
            input[i] = input[last - i];
            input[last - i] = temp;
        }
    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(Chromosome[] chromosomes) {
        this.chromosomes = chromosomes;
    }
}
