package sample;

import java.io.*;
import java.util.Formatter;


public class CreateFile {
    int ID;
    int row;
    int col;
    String squaresImg;
    boolean isCarAbleToGo;
    String filePath;



//    public static void saveRecord(int ID, int row, int col, String squaresImg, boolean isCarAbleToGo, String filePath){
//
//        try {
//            FileWriter fw = new FileWriter(filePath, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            PrintWriter pw = new PrintWriter(bw);
//
//            pw.println(ID + "," + row + "," + col + "," + squaresImg + "," + isCarAbleToGo);
//            pw.flush();
//            pw.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
    public static void saveRecordv2(File file, String content){

        try {

            PrintWriter pw = new PrintWriter(file);

//            pw.println(ID + "," + row + "," + col + "," + squaresImg + "," + isCarAbleToGo);
            pw.println(content);
            pw.flush();
            pw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}

