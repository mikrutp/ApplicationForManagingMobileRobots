package sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Vertex {
    private int IDtaken;

    private List<Vertex> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Vertex, Integer> adjacentVertex = new HashMap<>();

    private int colVer;
    private int rowVer;
    private int IdVer;

    public int g_scores;
    public int f_scores = 0;
    public Vertex parent;

    private String imgSrc;
    private String cartSymbol;

    public void addDestination(Vertex destination, int distance) {
        if (distance < 0) {
            distance = distance * (-1);
        }
        adjacentVertex.put(destination, distance);
    }
    public Vertex(int id, int x, int y) {
        this.IdVer = id;
        this.colVer = x;
        this.rowVer = y;
    }

    public Vertex(int id, int name, int x, int y) {

        this.IdVer = id;
        this.IDtaken = name;
        this.colVer = x;
        this.rowVer = y;

    }


    public int getName() {
        return IDtaken;
    }

    public List<Vertex> getShortestPath() {
        return shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public Map<Vertex, Integer> getAdjacentVertex() {
        return adjacentVertex;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setShortestPath(List<Vertex> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public int getCol() {
        return colVer;
    }

    public int getRow() {
        return rowVer;
    }

    public int getID() {
        return IdVer;
    }

    public void printVertex() {

        System.out.println("-----------------------\n" + "Name: " + getName());
        System.out.println("Distance: " + getDistance());
        System.out.print("Shortest path: ");
        for (Vertex v : shortestPath) {
            System.out.print(v.getName() + ",");
        }
        System.out.println();
        System.out.print("Adjacent Vertex: ");
        for (Vertex v : adjacentVertex.keySet()) {
            System.out.print(v.getName() + ",");
        }
        System.out.println();
    }

    public String getImgSrc() {
        return imgSrc;
    }
    public String getCartSymbol() {
        return cartSymbol;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    public void setCartSymbol(String cartSymbol) {
        this.cartSymbol = cartSymbol;
    }



}
