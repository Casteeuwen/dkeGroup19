import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private ArrayList<Triangle> tris;
    private ArrayList<Rectangle> rectangles;
    private UserInterface ui;

    public MainPanel(UserInterface ui) {
        tris = new ArrayList<>();
        rectangles = new ArrayList<>();
        this.ui = ui;

        addRectangles(new Rectangle(new Vertex(100, 100, 100),
                 new Vertex(-100, 100, 100),
                 new Vertex(-100, -100, 100),
                 new Vertex(100, -100, 100),
                 Color.WHITE));
        addRectangles(new Rectangle(new Vertex(100, 100, 100),
                new Vertex(100, 100,-100),
                new Vertex(100, -100,-100),
                new Vertex(100, -100, 100),
                Color.RED));
        addRectangles(new Rectangle(new Vertex(-100, -100, 100),
                new Vertex(-100, -100, -100),
                new Vertex(100, -100, -100),
                new Vertex(100, -100, 100),
                Color.GREEN));
        addRectangles(new Rectangle(new Vertex(-100, -100, -100),
                new Vertex(-100, 100, -100),
                new Vertex(100, 100, -100),
                new Vertex(100, -100, -100),
                Color.BLUE));
        addRectangles(new Rectangle(new Vertex(-100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(-100, -100, -100),
                new Vertex(-100, 100, -100),
                Color.YELLOW));
        addRectangles(new Rectangle(new Vertex(-100, 100, 100),
                new Vertex(-100, 100, -100),
                new Vertex(100, 100, -100),
                new Vertex(100, 100, 100),
                Color.PINK));

    }

    public void addRectangles(Rectangle rectangle) {
        rectangles.add(rectangle);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        double heading = Math.toRadians(ui.getHeadingSlider().getValue());
        Matrix3 headingTransform = new Matrix3(new double[] {
                Math.cos(heading), 0, Math.sin(heading),
                0, 1, 0,
                -Math.sin(heading), 0, Math.cos(heading)
        });

        double pitch = Math.toRadians(ui.getPitchSlider().getValue());
        Matrix3 pitchTransform = new Matrix3(new double[] {
                1, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch),
                0, -Math.sin(pitch), Math.cos(pitch)
        });

        Matrix3 transform = headingTransform.multiply(pitchTransform);

        g2.translate(getWidth() / 2, getHeight() / 2);
        g2.setColor(Color.WHITE);


        for (Rectangle r : rectangles) {
            Vertex v1 = transform.tranform(r.v1);
            Vertex v2 = transform.tranform(r.v2);
            Vertex v3 = transform.tranform(r.v3);
            Vertex v4 = transform.tranform(r.v4);

//            v1.x += getWidth() / 2;
//            v1.y += getHeight() / 2;
//            v2.x += getWidth() / 2;
//            v2.y += getHeight() / 2;
//            v3.x += getWidth() / 2;
//            v3.y += getHeight() / 2;
//            v4.x += getWidth() / 2;
//            v4.y += getHeight() / 2;

            int minX = (int) Math.max(0, Math.ceil(Math.min(Math.min(v1.x, v2.x), Math.min(v3.x, v4.x))));
            int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(Math.max(v1.x, v2.x), Math.max(v3.x, v4.x))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(Math.min(v1.y, v2.y), Math.min(v3.y, v4.y))));
            int maxY = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(Math.max(v1.y, v2.y), Math.max(v3.y, v4.y))));

            double rectangleArea = (v1.x - v2.x) * (v1.y - v3.y) + (v3.x - v4.x) * (v2.y - v3.y);
            System.out.println(rectangleArea);

            Path2D path = new Path2D.Double();
            path.moveTo(v1.x, v1.y);
            path.lineTo(v2.x, v2.y);
            path.lineTo(v3.x, v3.y);
            path.lineTo(v4.x, v4.y);
            path.closePath();
            g2.draw(path);
        }
    }
}
