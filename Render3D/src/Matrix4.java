public class Matrix4 {
    public double[] values;

    public Matrix4(double[] values) {
        this.values = values;
    }

    public Matrix4 multiply(Matrix4 other) {
        double[] result = new double[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i * 4 + j] += values[i * 4 + k] * other.values[k * 4 + j];
                }
            }
        }
        return new Matrix4(result);
    }

    public Vertex tranform(Vertex v) {
        return new Vertex(v.x * values[0] + v.y * values[4] + v.z * values[8] + v.w * values[12],
                v.x * values[1] + v.y * values[5] + v.z * values[9] + v.w * values[13],
                v.x * values[2] + v.y * values[6] + v.z * values[10] + v.w * values[14],
                v.x * values[3] + v.y * values[7] + v.z * values[11] + v.w * values[15]);
    }
}
