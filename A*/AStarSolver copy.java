package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private Vertex source;
    private Vertex goal;
    private double endTime;
    private Double finalTime;
    private HashMap<Vertex, Double> distTo; //come back to this
    private HashMap<Vertex, Vertex> edgeRoute;
    private DoubleMapPQ<Vertex> pQ;
    private int verticesVisited;
    int statesExplored;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        pQ = new DoubleMapPQ<>();
        distTo = new HashMap<>();
        edgeRoute = new HashMap<>();
        verticesVisited = 0;
        source = start;
        goal = end;
        endTime = timeout;
        pQ.add(source, 0);
        distTo.put(start, 0.0); //first vertex is distance 0

        Stopwatch sw = new Stopwatch();

        while (pQ.size() > 0 && !pQ.getSmallest().equals(end) && timeout >= sw.elapsedTime()) {
            Vertex p = pQ.removeSmallest();
            verticesVisited += 1;

            for (int i = 0; i < input.neighbors(p).size(); i++) { //iterate though edges of minPQ
                WeightedEdge neighbor = input.neighbors(p).get(i);
                Vertex t = (Vertex) neighbor.to(); //value of child vertex
                Vertex f = (Vertex) neighbor.from(); //value of parent vertex
                Double w = neighbor.weight();
                //weight gives weight of edge inbetween parent and child

                if (!distTo.containsKey(t)) {
                    distTo.put(t, Double.POSITIVE_INFINITY);
                }
                //relax edge
                if ((distTo.get(f) + w) < distTo.get(t)) {
                    edgeRoute.put(t, f); //t comes from f
                    statesExplored += 1;
                    distTo.put(t, w + distTo.get(f));
                    if (pQ.contains(t)) {
                        pQ.changePriority(t, distTo.get(t) + input.estimatedDistanceToGoal(t, end));
                    } else {
                        pQ.add(t, distTo.get(t) + input.estimatedDistanceToGoal(t, end));
                    }
                }
            }
        }
        finalTime = sw.elapsedTime();
    }
    @Override
    public SolverOutcome outcome() {
        if (pQ.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        }
        if (finalTime > endTime) {
            return SolverOutcome.TIMEOUT;
        } else {
            return SolverOutcome.SOLVED;
        }
    }
    @Override
    public List<Vertex> solution() {
        if (outcome() == SolverOutcome.TIMEOUT || outcome() == SolverOutcome.UNSOLVABLE) {
            return List.of();
        }
        LinkedList<Vertex> sol = new LinkedList<>();
        Vertex temp = edgeRoute.get(goal);
        while (temp != null) {
            sol.addFirst(temp);
            temp = edgeRoute.get(temp);
        }
        sol.addLast(goal);
        return sol;
    }
    @Override
    public double solutionWeight() {
        if (outcome() == SolverOutcome.TIMEOUT || outcome() == SolverOutcome.UNSOLVABLE) {
            return 0;
        }
        return distTo.get(goal);
    }
    @Override
    public int numStatesExplored() {
        return verticesVisited;
    }
    @Override
    public double explorationTime() {
        return finalTime;
    }
}
