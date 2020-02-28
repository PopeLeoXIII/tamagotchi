package sample.Models;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Animation.PetSpriteAnimation;


public class Pet extends Pane {
    private Image sprite;
    private int count = 3;
    private int columns = 3;
    private int offsetY = 0;
    private int offsetX = 0;
    private int width = 32;
    private int height = 32;
    private String name;
    private PetSpriteAnimation animation;


    public String getName() {
        return name;
    }

    public PetSpriteAnimation getAnimation() {
        return animation;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;

    }

    Pet(Image image, String name){
        this.name = name;
        sprite = image;
        ImageView imageView = new ImageView(sprite);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        this.setLayoutY(230);
        animation = new PetSpriteAnimation(imageView, Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(imageView);

    }


    public void setScale(double x){
        x = Math.min(x, 2.5);
        this.setScaleX(x);
        this.setScaleY(x);
    }

    public void moveX(int x){
        boolean right = x > 0;
        for(int i = 0; i < Math.abs(x); i++) {
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
        }
    }
    public void moveY(int y) {
        boolean down = y > 0;
        for (int i = 0; i < Math.abs(y); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }

}
