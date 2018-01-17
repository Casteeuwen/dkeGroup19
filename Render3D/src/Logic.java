import java.awt.*;
import java.util.Random;

public class Logic {
    private Random random;

    public Logic() {
        random = new Random();
    }

    public Box addRandomBox() {
        Box randomBox = null;
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                randomBox = new Box(1.0, 1.0, 2.0, random.nextInt(160) - 80, random.nextInt(40) - 20, 255, Color.GREEN);
                break;
            case 1:
                randomBox = new Box(1.0, 1.5, 2.0, random.nextInt(160) - 80, random.nextInt(40) - 20, 255, Color.RED);
                break;
            case 2:
                randomBox = new Box(1.5, 1.5, 1.5, random.nextInt(160) - 80, random.nextInt(40) - 20, 255, Color.BLUE);
                break;
        }
        System.out.println(randomBox);
        return randomBox;
    }
}
