import java.awt.*;
import java.util.ArrayList;

public class Box {
    private ArrayList<Triangle> triangles;
    int xCoord, yCoord, alpha;
    int x, y, z;
    Color c;

    public Box(int x, int y, int z, int xCoord, int yCoord, int alpha, Color c) {
        this.triangles = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.z = z;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.alpha = alpha;
        this.c = c;
        addShape();
    }

    public void addShape() {
        //A
        addTriangle(new Triangle(new Vertex(-x, y, z, 1),
                new Vertex(x, y, z, 1),
                new Vertex(-x, y, -z, 1),
                c));
        //B
        addTriangle(new Triangle(new Vertex(x, y, z, 1),
                new Vertex(x, y, -z, 1),
                new Vertex(-x, y, -z, 1),
                c));
        //C
        addTriangle(new Triangle(new Vertex(x, -y, z, 1),
                new Vertex(x, y, -z, 1),
                new Vertex(x, y, z, 1),
                c));
        //D
        addTriangle(new Triangle(new Vertex(x, -y, z, 1),
                new Vertex(x, -y, -z, 1),
                new Vertex(x, y, -z, 1),
                c));
        //E
        addTriangle(new Triangle(new Vertex(-x, -y, z, 1),
                new Vertex(x, -y, z, 1),
                new Vertex(-x, y, z, 1),
                c));

        //F
        addTriangle(new Triangle(new Vertex(x, -y, z, 1),
                new Vertex(x, y, z, 1),
                new Vertex(-x, y, z, 1),
                c));
        //G
        addTriangle(new Triangle(new Vertex(-x, -y, z, 1),
                new Vertex(-x, y, z, 1),
                new Vertex(-x, -y, -z, 1),
                c));
        //H
        addTriangle(new Triangle(new Vertex(-x, y, z, 1),
                new Vertex(-x, y, -z, 1),
                new Vertex(-x, -y, -z, 1),
                c));
        //I
        addTriangle(new Triangle(new Vertex(-x, y, -z, 1),
                new Vertex(x, y, -z, 1),
                new Vertex(-x, -y, -z, 1),
                c));
        //J
        addTriangle(new Triangle(new Vertex(-x, -y, -z, 1),
                new Vertex(x, y, -z, 1),
                new Vertex(x, -y, -z, 1),
                c));
        //K
        addTriangle(new Triangle(new Vertex(x, -y, z, 1),
                new Vertex(-x, -y, z, 1),
                new Vertex(-x, -y, -z, 1),
                c));
        //L
        addTriangle(new Triangle(new Vertex(-x, -y, -z, 1),
                new Vertex(x, -y, -z, 1),
                new Vertex(x, -y, z, 1),
                c));
    }

    public void addTriangle(Triangle triangle) {
        triangles.add(triangle);
    }

    public ArrayList<Triangle> getTriangles() {
        return triangles;
    }

    @Override
    public String toString() {
        return "Box " + x + " " + y + " " + xCoord + " " + yCoord + " ";
    }
}
