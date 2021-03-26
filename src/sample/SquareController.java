package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class SquareController {
    @FXML
    private ImageView Img;
    @FXML
    private ImageView possibleCartImg;

    private Square square;

    private GivenSquare givenSquare;

    public int sizeImg;

    @FXML
    private void click() throws IOException {
        givenSquare.onClickListener(square);
    }

    public void setData(Square vertex, GivenSquare givenSquare, int sizeimg){
        sizeImg = sizeimg;
        this.square = vertex;
        this.givenSquare = givenSquare;
        Image image = new Image(getClass().getResourceAsStream(vertex.getImgSrc()));
        Img.setFitHeight(sizeimg);
        Img.setFitWidth(sizeimg);
        Img.setImage(image);


    }
    public void setCartIfNeed(Square vertex, GivenSquare givenSquare) {
        this.square = vertex;
        this.givenSquare = givenSquare;
        Image cartImage = new Image(getClass().getResourceAsStream(vertex.getCartSymbol()));
        possibleCartImg.setFitHeight(20);
        possibleCartImg.setFitWidth(20);
        possibleCartImg.setImage(cartImage);
    }
}
