package GA;
public class EMS extends Dimension implements Comparable<EMS> {
    private double distance;

    public EMS(double[] origin, double length, double height, double width) throws Exception {
        super(origin, length, height, width);
        distance = 0;
    }

    @Override
    public String toString() {
        return "EMS " + super.toString();
    }

    @Override
    public int compareTo(EMS other) {
        if (distance == other.getDistance()) {
            return 0;
        } else if (distance > other.getDistance()) {
            return 1;
        } else {
            return -1;
        }
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }
}
