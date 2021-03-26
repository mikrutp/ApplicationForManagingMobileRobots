package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class Controller implements Initializable {

    @FXML
    private TabPane tabWithMapAndTask;

    @FXML
    private ImageView rightBigSquareImage;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane secondgrid;

    @FXML
    private ComboBox<String> choiceBox;

    @FXML
    private Button saveRobotStartingPoint;

    @FXML
    private Button smallSize;

    @FXML
    private Button mediumSize;

    @FXML
    private Button largeSize;

    @FXML
    private TextField whereToSetCart;

    @FXML
    private TextField howManyCol;

    @FXML
    private Button createFirstMap;

    @FXML
    private TextField howManyRow;

    @FXML
    private Button saveZadaniaFields;

    @FXML
    private MenuItem menuNewMap;

    @FXML
    private TextField realSizeOfSquare;

    @FXML
    private Tab tabTasksID;

    @FXML
    private Tab tabMapID;

    @FXML
    private Tab fileTabID;

    @FXML
    private Tab editTabID;

    @FXML
    private Tab helpTabID;

    @FXML
    private Tab elseTabID;


    @FXML
    void setChangeSizeLarge() {
        sizeOfSquares.clear();
        setLargeAreaToChangeRework();


    }

    @FXML
    void setChangeSizeMedium() {
        sizeOfSquares.clear();
        setMiddleAreaToChangeRework();

    }

    @FXML
    void setChangeSizeSmall() {
        sizeOfSquares.clear();

    }

    @FXML
    void setSaveRobotStartingPoint() {
        refreshRobotsPositionOnMap(chosenSmallSquare);
        algorithmStartingID = chosenSmallSquare.getID();


    }

    @FXML
    private AnchorPane anchorid;

    @FXML
    void saveShortestPathButtonAction(ActionEvent event) {
        Window stage = anchorid.getScene().getWindow();
        readWriteFile.WriteRecord(stage, squares);
    }

    @FXML
    void menuNewMapAction() throws IOException {
        final FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);


        if (file != null) {
            System.out.println("Path : " + file.getAbsolutePath());
        }
        givenSquare = this::setChosenSquare;
        initSecondGridPane(file.getAbsolutePath());
        initSecondMapRework(sizeOfColumnsInReadFile, sizeOfRowsInReadFile);

    }

    @FXML
    void saveMapToFile() {
        Window stage = anchorid.getScene().getWindow();
        int numOfRows = parseInt(howManyRow.getText());
        int numOfCol = parseInt(howManyCol.getText());
        readWriteFile.WriteRecord(numOfCol, numOfRows, stage, squares);
    }

    @FXML
    void createFirstMapAction() throws IOException {

        int numOfRows = parseInt(howManyRow.getText());
        int numOfCol = parseInt(howManyCol.getText());
        if(numOfCol < 55) {
            sizeImage = Math.round(1350 / numOfCol);
        }else{
            sizeImage = 20;
        }

        initParameters(numOfCol, numOfRows);
        initMapRework(numOfCol, numOfRows, sizeImage);

    }

    @FXML
    void setSaveButtonAction() {

        String destinationIDString = String.valueOf(chosenSmallSquare.getID());
        System.out.println("ID of chosen square is : " + chosenSmallSquare.getID());

        String shortestPath = "";

        Graph graph = createVertexMapRework();

        Graph shortestPathToAllOthersFromGivenVertex = Graph.calculateShortestPathFromSource(graph, tempSquare);
        shortestPathToAllOthersFromGivenVertex.printGraph();

        for (Square s : shortestPathToAllOthersFromGivenVertex.getSquareFromGraph()) {
            if (destinationIDString.equals(s.getName())) {
                s.getShortestPath();

                for (Square a : s.getShortestPath()) {
                    System.out.println("IDD = " + a.getID() + " Coll = " + a.getCol() + " Roww = " + a.getRow());

                    shortestPath = shortestPath + " " + a.getID();

                    refreshRobotsPositionOnMap(a);
//                    wait(100);

                    refreshGivenSquareOnMapOnSecondGrid(newMapSquares.get(a.getID()));


                }
                Window stage = anchorid.getScene().getWindow();
                readWriteFile.WriteRecord(stage, s.getShortestPath());
            }
        }
        System.out.println("Shortest path to destination is:" + shortestPath);
        shortestPath = "";


    }


    private FXMLLoader fxmlLoader;
    private AnchorPane anchorPane;
    SquareController squareController;
    private Square chosenSmallSquare;
    private List<Square> squares = new ArrayList<>();
    private List<Square> sizeOfSquares = new ArrayList<>();
    private List<Square> newMapSquares = new ArrayList<>();

    private Image image;
    private GivenSquare givenSquare;

    private Square tempSquare;
    private int sizeImage;
    private int sizeOfColumnsInReadFile;
    private int sizeOfRowsInReadFile;
    private int algorithmStartingID;



    private ReadWriteFile readWriteFile = new ReadWriteFile();


    ObservableList<String> comboBoxFields = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBoxParameters();
        setDisableValueOfTabs();
    }



    private void initParameters(int colNumberWanted, int rowNumberWanted) {
        initGridPaneRework(colNumberWanted, rowNumberWanted);                    //-------------tu zmiana


//        String a = "Pole ładowania";
//        String b = "Ściana";
//        String c = "Niebezpieczeństwo";
//        String d = "Strefa wolna";
//        String e = "  ";
//        comboBoxFields.addAll(e, b, c, d);
//        choiceBox.setItems(comboBoxFields);

        givenSquare = this::setImageBasedOnChosenSquare;
//        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::setComboBoxHandler);

    }
    private void initComboBoxParameters(){
        String b = "Ściana";
        String c = "Niebezpieczeństwo";
        String d = "Strefa wolna";
        String e = "  ";
        comboBoxFields.addAll(e, b, c, d);
        choiceBox.setItems(comboBoxFields);

//        givenSquare = this::setImageBasedOnChosenSquare;
        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::setComboBoxHandler);
    }

    //-------------------------------------------REWORKed 1st MAP-------------------------------
    private void initGridPaneRework(int colNumberWanted, int rowNumberWanted) {
        Square square;
        squares.clear();
        int column = 0;
        int row = 1;
        for (int i = 0; i < (colNumberWanted * rowNumberWanted); i++) {      //-----
            if (column == colNumberWanted) {      //-------
                column = 0;
                row++;
            }
            square = new Square(i, row, column);
            column++;


            square.setImgSrc("/img/green.png");
            square.setIsClearToGo(true);


            squares.add(square);

        }
    }

    private void initMapRework(int colNumberWanted, int rowNumberWanted, int sizeImage) throws IOException {
        int column = 0;
        int row = 1;

        grid.getChildren().clear();

        for (int i = 0; i < squares.size(); i++) {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/Square.fxml"));
            anchorPane = fxmlLoader.load();

            squareController = fxmlLoader.getController();
            squareController.setData(squares.get(i), givenSquare, sizeImage);

            if (column == colNumberWanted) {       //----
                column = 0;
                row++;
            }
            grid.add(anchorPane, column++, row);

            grid.setMinWidth(Region.USE_COMPUTED_SIZE);   //------------zmiany w rozmiarach komorek
            grid.setPrefWidth(sizeImage * colNumberWanted);
            grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(sizeImage * rowNumberWanted);
            grid.setMaxHeight(Region.USE_COMPUTED_SIZE);


        }
    }
    //-------------------------------------------REWORKed 2nd MAP-------------------------------

    private void initSecondMapRework(int colNumberWanted, int rowNumberWanted) throws IOException {
        int column = 0;
        int row = 1;

        secondgrid.getChildren().clear();

        for (int i = 0; i < newMapSquares.size(); i++) {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/Square.fxml"));
            anchorPane = fxmlLoader.load();

            if(colNumberWanted < 55) {
                sizeImage = Math.round(1350 / colNumberWanted);
            }else{
                sizeImage = 20;
            }

            squareController = fxmlLoader.getController();
            squareController.setData(newMapSquares.get(i), givenSquare, sizeImage);

            if (column == colNumberWanted) {       //----
                column = 0;
                row++;
            }
            secondgrid.add(anchorPane, column++, row);

            secondgrid.setMinWidth(Region.USE_COMPUTED_SIZE);
            secondgrid.setPrefWidth(sizeImage * colNumberWanted);
            secondgrid.setMaxWidth(Region.USE_PREF_SIZE);

            secondgrid.setMinHeight(Region.USE_COMPUTED_SIZE);
            secondgrid.setPrefHeight(sizeImage * rowNumberWanted);
            secondgrid.setMaxHeight(Region.USE_PREF_SIZE);


        }
    }


    //----------------------------------------------------second grid funciotns

    private void initSecondGridPane(String path) {
        Square square;
        readWriteFile.ReadRecord(path);

        boolean flag = false;

        newMapSquares = new ArrayList<>();

        for (String[] s : readWriteFile.listOfSquaresValues) {
            if (!flag) {
                sizeOfColumnsInReadFile = parseInt(s[0]);
                sizeOfRowsInReadFile = parseInt(s[1]);
                flag = true;

            } else {
                System.out.println("a: " + s[0] + " b: " + s[1] + " c: " + s[2] + " d: " + s[3] + " d: " + s[4]);
                square = new Square(parseInt(s[0]), parseInt(s[1]), parseInt(s[2]));

                square.setImgSrc(s[3]);
                square.setIsClearToGo(parseBoolean(s[4]));

                System.out.println("ID: " + square.getID() + " ROW: " + square.getRow() + " COL: " + square.getCol() + " IMG: " + square.getImgSrc() + " CLEARTOGO: " + square.getIsClearToGo());
                newMapSquares.add(square);
            }

        }

    }

    private void setComboBoxHandler(ObservableValue<? extends String> observableValue, String oldSquare, String newSquare) {

        if (newSquare != null) {
            switch (newSquare) {
                case "  ":


                    break;
                case "Pole ładowania":
                    image = new Image(getClass().getResourceAsStream("/img/battery.png"));
                    rightBigSquareImage.setImage(image);
                    chosenSmallSquare.setImgSrc("/img/battery.png");
                    refreshGivenSquareOnMap(chosenSmallSquare);

                    for (Square s : sizeOfSquares) {
                        s.setImgSrc("/img/battery.png");
                        refreshGivenSquareOnMap(s);
                    }
                    sizeOfSquares.clear();
                    Platform.runLater(() -> choiceBox.setValue(" "));
                    break;

                case "Ściana":
                    image = new Image(getClass().getResourceAsStream("/img/wall.png"));
                    rightBigSquareImage.setImage(image);
                    chosenSmallSquare.setImgSrc("/img/wall.png");

                    squares.get(chosenSmallSquare.getID()).setIsClearToGo(false);

                    refreshGivenSquareOnMap(chosenSmallSquare);


                    for (Square s : sizeOfSquares) {
                        s.setImgSrc("/img/wall.png");
                        squares.get(s.getID()).setIsClearToGo(false);
                        refreshGivenSquareOnMap(s);
                    }
                    sizeOfSquares.clear();
                    Platform.runLater(() -> choiceBox.setValue(" "));
                    break;

                case "Niebezpieczeństwo":
                    image = new Image(getClass().getResourceAsStream("/img/danger.png"));
                    rightBigSquareImage.setImage(image);
                    chosenSmallSquare.setImgSrc("/img/danger.png");

                    squares.get(chosenSmallSquare.getID()).setIsClearToGo(false);

                    refreshGivenSquareOnMap(chosenSmallSquare);

                    for (Square s : sizeOfSquares) {
                        s.setImgSrc("/img/danger.png");
                        squares.get(s.getID()).setIsClearToGo(false);
                        refreshGivenSquareOnMap(s);
                    }
                    sizeOfSquares.clear();
                    Platform.runLater(() -> choiceBox.setValue(" "));

                    break;

                case "Strefa wolna":
                    image = new Image(getClass().getResourceAsStream("/img/green.png"));
                    rightBigSquareImage.setImage(image);
                    chosenSmallSquare.setImgSrc("/img/green.png");
                    refreshGivenSquareOnMap(chosenSmallSquare);

                    for (Square s : sizeOfSquares) {
                        s.setImgSrc("/img/green.png");
                        refreshGivenSquareOnMap(s);
                    }
                    sizeOfSquares.clear();
                    Platform.runLater(() -> choiceBox.setValue(" "));
                    break;
            }
        }
    }

    private void setImageBasedOnChosenSquare(Square square) {
        image = new Image(getClass().getResourceAsStream(square.getImgSrc()));
        chosenSmallSquare = square;
        rightBigSquareImage.setImage(image);
        sizeOfSquares.clear();
    }
    private void setChosenSquare(Square square) {
        chosenSmallSquare = square;
    }


    private void refreshGivenSquareOnMap(Square square) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/Square.fxml"));
            anchorPane = fxmlLoader.load();
            squareController = fxmlLoader.getController();
            squareController.setData(square, givenSquare, sizeImage);  //-----------------------------------------------------------------------------

            System.out.println("ID: " + square.getID() +
                    " col: " + square.getCol() + " row: " + square.getRow());
            grid.add(anchorPane, square.getCol(), square.getRow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void refreshGivenSquareOnMapOnSecondGrid(Square square) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/Square.fxml"));
            anchorPane = fxmlLoader.load();
            squareController = fxmlLoader.getController();
            squareController.setData(square, givenSquare, sizeImage);  //-----------------------------------------------------------------------------

            System.out.println("ID: " + square.getID() +
                    " col: " + square.getCol() + " row: " + square.getRow());
            secondgrid.add(anchorPane, square.getCol(), square.getRow());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMiddleAreaToChangeRework() {  //---changed
        if (chosenSmallSquare != null) {
            int rowToSizeOfSquares = chosenSmallSquare.getRow();
            int colToSizeOfSquares = chosenSmallSquare.getCol();

            for (int i = 0; i < squares.size(); i++) {
                if (squares.get(i).getRow() == rowToSizeOfSquares - 1 && squares.get(i).getCol() >= colToSizeOfSquares - 1 && squares.get(i).getCol() <= colToSizeOfSquares + 1) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares && squares.get(i).getCol() >= colToSizeOfSquares - 1 && squares.get(i).getCol() <= colToSizeOfSquares + 1) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares + 1 && squares.get(i).getCol() >= colToSizeOfSquares - 1 && squares.get(i).getCol() <= colToSizeOfSquares + 1) {
                    sizeOfSquares.add(squares.get(i));
                }
            }
        }
    }


    private void setLargeAreaToChangeRework() { //--changed
        if (chosenSmallSquare != null) {
            int rowToSizeOfSquares = chosenSmallSquare.getRow();
            int colToSizeOfSquares = chosenSmallSquare.getCol();

            for (int i = 0; i < squares.size(); i++) {
                if (squares.get(i).getRow() == rowToSizeOfSquares - 2 && squares.get(i).getCol() >= colToSizeOfSquares - 2 && squares.get(i).getCol() <= colToSizeOfSquares + 2) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares - 1 && squares.get(i).getCol() >= colToSizeOfSquares - 2 && squares.get(i).getCol() <= colToSizeOfSquares + 2) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares && squares.get(i).getCol() >= colToSizeOfSquares - 2 && squares.get(i).getCol() <= colToSizeOfSquares + 2) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares + 1 && squares.get(i).getCol() >= colToSizeOfSquares - 2 && squares.get(i).getCol() <= colToSizeOfSquares + 2) {
                    sizeOfSquares.add(squares.get(i));
                } else if (squares.get(i).getRow() == rowToSizeOfSquares + 2 && squares.get(i).getCol() >= colToSizeOfSquares - 2 && squares.get(i).getCol() <= colToSizeOfSquares + 2) {
                    sizeOfSquares.add(squares.get(i));
                }
            }
        }
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    private void refreshRobotsPositionOnMap(Square square) {
        if (square != null) {
            System.out.println("ID: " + square.getID() +
                    " col: " + square.getCol() + " row: " + square.getRow());
            try {
                square.setCartSymbol("/img/cart.png");
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/Square.fxml"));
                anchorPane = fxmlLoader.load();
                squareController = fxmlLoader.getController();
                squareController.setCartIfNeed(square, givenSquare);

                System.out.println("ID: " + square.getID() +
                        " col: " + square.getCol() + " row: " + square.getRow());
                secondgrid.add(anchorPane, square.getCol(), square.getRow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public Graph createVertexMapRework() {

        int k = 1;
        int j = 0;
        Square[] vertexesFloor0 = new Square[newMapSquares.size()];//---changed

        int maxColNumber = 0;
        for (int i = 0; i < newMapSquares.size(); i++) {
            if (maxColNumber < newMapSquares.get(i).getCol()) {
                maxColNumber = newMapSquares.get(i).getCol();
            }
        }
        System.out.println("MAX COL NUMBER: " + maxColNumber);
        int maxRowNumber = 0;
        for (int i = 0; i < newMapSquares.size(); i++) {
            if (maxRowNumber < newMapSquares.get(i).getRow()) {
                maxRowNumber = newMapSquares.get(i).getRow();
            }
        }
        System.out.println("Max rOW NUMBER: " + maxRowNumber);
        for (int i = 0; i < newMapSquares.size(); i++) {         //----changed
            if (j == (maxColNumber + 1)) {         //----changed
                j = 0;
                k++;
            }
            vertexesFloor0[i] = new Square(i + 1, k, j);
            vertexesFloor0[i].setIsClearToGo(newMapSquares.get(i).getIsClearToGo()); //-------------------------------------------------
            j++;
            System.out.println("vertex ID: " + vertexesFloor0[i].getID() + "vertex COL: " + vertexesFloor0[i].getCol() + "vertex ROW: " + vertexesFloor0[i].getRow());
        }

        for (int i = 0; i < newMapSquares.size(); i++) {          //--changed
            if (vertexesFloor0[i].getIsClearToGo()) {
                if(vertexesFloor0[i].getRow() == 1 && vertexesFloor0[i].getCol() > 0 && vertexesFloor0[i].getCol() < maxColNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[maxColNumber + i + 1], 1);
                } else if (vertexesFloor0[i].getRow() == maxRowNumber && vertexesFloor0[i].getCol() > 0 && vertexesFloor0[i].getCol() < maxColNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                } else if (vertexesFloor0[i].getCol() == 0 && vertexesFloor0[i].getRow() > 1 && vertexesFloor0[i].getRow() < maxRowNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + maxColNumber + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                } else if(vertexesFloor0[i].getCol() == maxColNumber && vertexesFloor0[i].getRow() > 1 && vertexesFloor0[i].getRow() < maxRowNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + maxColNumber + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                } else if(vertexesFloor0[i].getCol() == 0 && vertexesFloor0[i].getRow() == 1){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + maxColNumber + 1], 1);
                } else if(vertexesFloor0[i].getCol() == maxColNumber && vertexesFloor0[i].getRow() == 1){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + maxColNumber + 1], 1);
                } else if(vertexesFloor0[i].getCol() == 0 && vertexesFloor0[i].getRow() == maxRowNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                } else if(vertexesFloor0[i].getCol() == maxColNumber && vertexesFloor0[i].getRow() == maxRowNumber){
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                }else{
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + maxColNumber + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - maxColNumber - 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i + 1], 1);
                    vertexesFloor0[i].addDestination(vertexesFloor0[i - 1], 1);
                }
            }
        }

        tempSquare = vertexesFloor0[algorithmStartingID];

        Graph graph = new Graph();

        for (Square s : vertexesFloor0) {
            System.out.println("row: " + s.getRow() + " col: " + s.getCol());
            graph.addSquare(s);
        }
        return graph;
    }
    public void setDisableValueOfTabs(){
        tabWithMapAndTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
        {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1)
            {
                if ("tabTasksID".equals(t1.getId()))
                {
                    System.out.println(" Jestes w tab Tasks");
                    fileTabID.setDisable(true);
                    editTabID.setDisable(true);
                    helpTabID.setDisable(false);
                    elseTabID.setDisable(false);
                }
                if ("tabMapID".equals(t1.getId()))
                {
                    System.out.println(" Jestes w tab Map");
                    fileTabID.setDisable(false);
                    editTabID.setDisable(false);
                    helpTabID.setDisable(true);
                    elseTabID.setDisable(true);

                }
            }
        });
    }
}