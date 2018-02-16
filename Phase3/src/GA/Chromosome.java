package GA;
import java.util.Arrays;

/**
 * Chromosomes containing different possible box arrangements as 'genes'
 */
public class Chromosome {
    private int[] genes;

    public Chromosome(int[] values) {
        genes = values;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
