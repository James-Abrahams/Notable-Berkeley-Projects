package bearmaps;
import java.util.List;


public class KDTree implements PointSet {
    private Node root;
    private int level = 0;

    public KDTree(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            add(points.get(i));
        }
    }
    private class Node {
        private Point point;
        private boolean horizontal;
        private Node left;
        private Node right;

        Node(Point p, boolean h) {
            this.point = p;
            this.horizontal = h;
        }
    }
    public void add(Point p) {
        root = addHelper(p, root);
    }
    private Node addHelper(Point p, Node n) {
        if (n == null) {
            //if the level is even, the orientation is horizontal
            if (level % 2 != 0) {
                return new Node(p, true);
            }
            return new Node(p, false);
        }
        level++; //we are going into a deeper level of nodes
        double comp = comparePoints(p, n.point, level);
        if (comp < 0) {
            n.left = addHelper(p, n.left);
        }
        if (comp > 0) {
            n.right = addHelper(p, n.right);
        }
        if (comp == 0) {
            //checks if points are exactly the same (x and y) if so replace
            if (comparePoints(p, n.point, level + 1) == 0) {
                n.point = p;
                return n;
            }
            n.right = addHelper(p, n.right);
        }
        level = 0;
        return n;
    }
    private int comparePoints(Point child, Point parent, int thisLevel) {
        if (thisLevel % 2 != 0) {
            return Double.compare(child.getX(), parent.getX());
        }
        return Double.compare(child.getY(), parent.getY());
    }
    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        return speedyNearest(root, p, root).point;
    }
    private Node speedyNearest(Node n, Point goal, Node best) {
        Node goodside;
        Node badside;

        if (n == null) {
            return best;
        }
        if (Point.distance(goal, n.point) < Point.distance(goal, best.point)) {
            best = n;
        }
        if (n.horizontal) {

            if (n.point.getY() > goal.getY()) {
                goodside = n.left;
                badside = n.right;
            } else {
                goodside = n.right;
                badside = n.left;
            }
        } else {
            if (n.point.getX() > goal.getX()) {
                goodside = n.left;
                badside = n.right;
            } else {
                goodside = n.right;
                badside = n.left;
            }
        }
        best = speedyNearest(goodside, goal, best);
        if (n.horizontal && (Math.abs(n.point.getY() - goal.getY()))
                < Math.sqrt(Point.distance(goal, best.point))) {
            best = speedyNearest(badside, goal, best);
        } else if (!n.horizontal && (Math.abs(n.point.getX() - goal.getX()))
                < Math.sqrt(Point.distance(goal, best.point))) {
            best = speedyNearest(badside, goal, best);
        }

        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        kd.nearest(4, 6);
    }
}
