package sample;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFile {
    public List<String[]> listOfSquaresValues;


    public void ReadRecord(String path) {
        String line;
        listOfSquaresValues = new ArrayList<>();
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 5) {
                        System.out.println("ID: " + values[0] + " row " + values[1] + " col: " + values[2] + " img: " + values[3] + " canCartGo :" + values[4]);
                    }
                    listOfSquaresValues.add(values);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void WriteRecord(int numOfCol, int numOfRows, Window stage, List<Square> squares){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("Map");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            String content = "";

            if (file != null) {
                content +=  numOfCol + "," + numOfRows + "\n";
                for (Square a : squares) {
                    content += a.getID() + "," + a.getRow() + "," + a.getCol() + "," + a.getImgSrc() + "," + a.getIsClearToGo() + "\n";
                }
                content = content.trim();
                CreateFile.saveRecordv2(file, content);
            }
        } catch (Exception e) {

        }
    }
    public void WriteRecord(Window stage, List<Square> squares){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Dialog");
        fileChooser.setInitialFileName("Path");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", "*.txt"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            String content = "";
            int cost = 0;

            if (file != null) {
                for (Square a : squares) {
                    content += a.getID() + "," + a.getRow() + "," + a.getCol() + "\n";
                    cost += 1;
                }
                content += "Cost = " + cost;
                content = content.trim();
                CreateFile.saveRecordv2(file, content);
            }
        } catch (Exception e) {

        }
    }
}


