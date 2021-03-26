package sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Square {

    private String imgSrc;
    private int ID;
    private int row;
    private int col;
    private boolean isClearToGo;
    private String cartSymbol;

    private String name;


    private List<Square> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Square, Integer> adjacentVertex = new HashMap<>();

    private int colVer;
    private int rowVer;
    private int IdVer;

    public int g_scores;
    public int f_scores = 0;
    public Square parent;

    public Square(int ID, int row, int col) {
        this.ID = ID;
        this.row = row;
        this.col = col;
        this.name = String.valueOf(ID);
    }


    public String getImgSrc() {
        return imgSrc;
    }

    public int getID() {
        return ID;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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



    public String getName() {
        return name;
    }

    public List<Square> getShortestPath() {
        return shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public Map<Square, Integer> getAdjacentSquare() {
        return adjacentVertex;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setShortestPath(List<Square> shortestPath) {
        this.shortestPath = shortestPath;
    }


    public void printSquare() {

        System.out.println("-----------------------\n" + "Name: " + getName());
        System.out.println("Distance: " + getDistance());
        System.out.print("Shortest path: ");
        for (Square s : shortestPath) {
            System.out.print(s.getName() + ",");
        }
        System.out.println();
        System.out.print("Adjacent Vertex: ");
        for (Square s : adjacentVertex.keySet()) {
            System.out.print(s.getName() + ",");
        }
        System.out.println();
    }

    public void addDestination(Square destination, int distance) {
        if (distance < 0) {
            distance = distance * (-1);
        }
        adjacentVertex.put(destination, distance);
    }


    public boolean getIsClearToGo() {
        return isClearToGo;
    }

    public void setIsClearToGo(boolean isClearToGo) {
        this.isClearToGo = isClearToGo;
    }
}
