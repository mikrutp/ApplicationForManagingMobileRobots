package sample;

import java.io.File;
import java.io.IOException;

public class MakeAfile {



    public static void makeAfile(String filePathString){

        try {
        File file = new File(filePathString);

        if(!file.exists()){

                file.createNewFile();

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
