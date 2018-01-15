public class Matrix3 {
    public double[] values;

    public Matrix3(double[] values) {
        this.values = values;
    }

    public Matrix3 multiply(Matrix3 other) {
        double[] result = new double[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i * 3 + j] += values[i * 3 + k] * other.values[k * 3 + j];
                }
            }
        }
        return new Matrix3(result);
    }

    public Vertex tranform(Vertex v) {
        return new Vertex(v.x * values[0] + v.y * values[3] + v.z * values[6],
                v.x * values[1] + v.y * values[4] + v.z * values[7],
                v.x * values[2] + v.y * values[5] + v.z * values[8]);
    }
}
