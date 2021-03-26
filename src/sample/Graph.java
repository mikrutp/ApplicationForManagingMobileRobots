package sample;

import java.util.*;

public class Graph {

    private Set<Square> squares = new HashSet<>();

    public void addSquare(Square square) {
        squares.add(square);
    }

    public Set<Square> getSquareFromGraph() {
        return squares;
    }


    //Dijkstra

    public static Graph calculateShortestPathFromSource(Graph graph, Square source) {
        source.setDistance(0);

        Set<Square> settledSquare = new HashSet<>();
        Set<Square> unsettledSquare = new HashSet<>();

        unsettledSquare.add(source);

        while (unsettledSquare.size() != 0) {
            Square currentSquare = getLowestDistanceSquare(unsettledSquare);
            unsettledSquare.remove(currentSquare);
            for (Map.Entry<Square, Integer> adjacencyPair : currentSquare.getAdjacentSquare().entrySet()) {
                Square adjacentSquare = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledSquare.contains(adjacentSquare)) {
                    calculateMinimumDistance(adjacentSquare, edgeWeight, currentSquare);
                    unsettledSquare.add(adjacentSquare);
                }
            }
            settledSquare.add(currentSquare);
        }
        return graph;
    }

    private static Square getLowestDistanceSquare(Set<Square> unsettledSquare) {
        Square lowestDistanceSquare = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Square Square : unsettledSquare) {
            int SquareDistance = Square.getDistance();
            if (SquareDistance < lowestDistance) {
                lowestDistance = SquareDistance;
                lowestDistanceSquare = Square;
            }
        }
        return lowestDistanceSquare;
    }

    private static void calculateMinimumDistance(Square evaluationSquare, Integer edgeWeigh, Square sourceSquare) {
        Integer sourceDistance = sourceSquare.getDistance();
        if (sourceDistance + edgeWeigh < evaluationSquare.getDistance()) {
            evaluationSquare.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Square> shortestPath = new LinkedList<Square>(sourceSquare.getShortestPath());
            shortestPath.add(sourceSquare);
            evaluationSquare.setShortestPath(shortestPath);
        }
    }

    public void printGraph() {
        for (Square s : squares) {
            s.printSquare();
        }
    }

//    public static List<Square> printPath(Square target) {
//        List<Square> path = new ArrayList<Square>();
//        for (Square vertex = target; vertex != null; vertex = vertex.parent) {
//            path.add(vertex);
//        }
//        Collections.reverse(path);
//        return path;
//    }




}

