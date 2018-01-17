import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private UserInterface ui;
    private ArrayList<Box> boxes;

    public MainPanel(UserInterface ui) {
        setPreferredSize(new Dimension(500, 500));
        boxes = new ArrayList<>();
        this.ui = ui;
        addBox(new Box(150, 50, 100, 0, 0, 50, Color.GRAY));
    }

    public void printBoxes() {
        for (Box b : boxes) {
            System.out.println(b);
        }
    }

    public void addBox(Box box) {
        boxes.add(box);
        System.out.println(box.xCoord + " " + box.yCoord + " " + box.c);
    }

    public Color getShade(Color color, double shade, int alpha) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1/2.4);
        int green = (int) Math.pow(greenLinear, 1/2.4);
        int blue = (int) Math.pow(blueLinear, 1/2.4);
        return new Color(red, green, blue, alpha);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        double heading = Math.toRadians(ui.getHeadingSlider().getValue());
        Matrix4 headingTransform = new Matrix4(new double[] {
                Math.cos(heading), 0, Math.sin(heading), 0,
                0, 1, 0, 0,
                -Math.sin(heading), 0, Math.cos(heading), 0,
                0, 0, 0, 1
        });

        double pitch = Math.toRadians(ui.getPitchSlider().getValue());
        Matrix4 pitchTransform = new Matrix4(new double[] {
                1, 0, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch), 0,
                0, -Math.sin(pitch), Math.cos(pitch), 0,
                0, 0, 0, 1
        });

        Matrix4 transform = headingTransform.multiply(pitchTransform);

        for (Box b : boxes) {
            BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            double[] zBuffer = new double[img.getWidth() * img.getHeight()];
            for (int q = 0; q < zBuffer.length; q++) {
                zBuffer[q] = Double.NEGATIVE_INFINITY;
            }

            for (Triangle t : b.getTriangles()) {
                Vertex v1 = transform.tranform(t.v1);
                Vertex v2 = transform.tranform(t.v2);
                Vertex v3 = transform.tranform(t.v3);

                Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z, v2.w - v1.w);
                Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z, v3.w - v1.w);
                Vertex norm = new Vertex(
                        ab.y * ac.z - ab.z * ac.y,
                        ab.z * ac.x - ab.x * ac.z,
                        ab.x * ac.y - ab.y * ac.x,
                        1
                );

                double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
                norm.x /= normalLength;
                norm.y /= normalLength;
                norm.z /= normalLength;

                double angleCos = Math.abs(norm.z);

                v1.x += getWidth() / 2;
                v1.y += getHeight() / 2;
                v2.x += getWidth() / 2;
                v2.y += getHeight() / 2;
                v3.x += getWidth() / 2;
                v3.y += getHeight() / 2;

                int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
                int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                int maxY = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

                for (int y = minY; y <= maxY; y++) {
                    for (int x = minX; x <= maxX; x++) {
                        double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                        double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                        double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                        if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >=0 && b3 <= 1) {
                            double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                            int zIndex = y * img.getWidth() + x;
                            if (zBuffer[zIndex] < depth) {
                                img.setRGB(x, y, getShade(t.c, angleCos, b.alpha).getRGB());
                                zBuffer[zIndex] = depth;
                            }
                        }
                    }
                }
                g2.drawImage(img, b.xCoord, b.yCoord,null);
            }
        }
    }
}
