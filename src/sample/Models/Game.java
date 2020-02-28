package sample.Models;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import sample.FileIO.Register;
import sample.GameStatus;
import sample.Main;
import sample.Animation.PetSpriteAnimation;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Game {
    public Image getGraveSprite() {
        return graveSprite;
    }

    public String getPetName() {return petName;}

    public Long getBirthdayDate() {
        return birthdayDate;
    }

    public Long getLastFeed() {
        return lastFeed;
    }

    public Pet getPet() {
        return pet;
    }

    public Long getStateGod() {
        return stateGod;
    }

    public Long getStateBad() {
        return stateBad;
    }

    public Long getStateDead() {
        return stateDead;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Image getFoodSprite() {
        return foodSprite;
    }

    public Register getRegister() {
        return register;
    }

    public PetSpriteAnimation getPetAnimation() {
        return pet.getAnimation();
    }

    public Image getPetSprite() {
        return pet.getSprite();
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public Tooltip getTooltip() { return tooltip; }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

    private Pet pet;
    private Long lastFeed = 0L;
    private Long stateGod;
    private Long stateBad;
    private Long stateDead;
    private Long birthdayDate = 0L;
    private GameStatus gameStatus;
    private Image graveSprite;
    private Image foodSprite;
    private Image background;
    private Register register;
    private ArrayList<String> arrayList;
    private String petName;
    private Double multiplier = 1d;
    private Tooltip tooltip;
    private Long tooltipDalay = 60000L;
    private Long lastTooltipUpdate;

    public void updateTooltip(){
        long d = new Date().getTime();
        if(d - getLastFeed() > getStateGod()) {
            getTooltip().setText("doesn't look very cool");
        } else {
            if(d - lastTooltipUpdate > tooltipDalay)
                getTooltip().setText(petName + " is okay");
        }
    }
    public void updateTooltip(String s){
        getTooltip().setText(s);
        lastTooltipUpdate = new Date().getTime();
    }

    public Game(ArrayList<String> list) {
        this.register = Main.register;
        Date date = new Date();
        Image petSprite = null;
        try {
            background = new Image(new FileInputStream("bg.png"));
            petSprite = new Image(new FileInputStream(list.get(0)));
            foodSprite = new Image(new FileInputStream(list.get(6)));
            lastFeed = date.getTime();
            multiplier = 1d;
            stateGod = Long.parseLong(list.get(1));
            stateBad = Long.parseLong(list.get(2)) + stateGod;
            stateDead = Long.parseLong(list.get(3)) + stateBad;
            petName = list.get(4);
            birthdayDate = date.getTime();
            graveSprite = new Image(new FileInputStream(list.get(5)));
            foodSprite = new Image(new FileInputStream(list.get(6)));


            arrayList = new ArrayList<>(Arrays.asList(list.get(0),
                                                      lastFeed.toString(),
                                                      stateGod.toString(),
                                                      stateBad.toString(),
                                                      stateDead.toString(),
                                                      petName, birthdayDate.toString(),
                                                      list.get(5), list.get(6),
                                                      multiplier.toString()));



        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        gameStatus = GameStatus.STILL_ALIVE;
        this.pet = new Pet(petSprite, petName);
        tooltip = new Tooltip("excellent");
        tooltip.setFont(new Font(18));
        Tooltip.install(pet, tooltip);
        updateTooltip("excellent");
        feedPet();
    }

    public Game(){
        this.register = Main.register;

        Image petSprite = null;
        try {
            background = new Image(new FileInputStream("bg.png"));
            arrayList = register.readTxtFile("save.txt");
            if(arrayList == null || arrayList.size() < 10)
                throw new FileNotFoundException("Wrong type of save file");
            petSprite = new Image(new FileInputStream(arrayList.get(0)));

            lastFeed = Long.parseLong(arrayList.get(1));
            graveSprite = new Image(new FileInputStream(arrayList.get(7)));
            stateGod = Long.parseLong(arrayList.get(2));
            stateBad = Long.parseLong(arrayList.get(3)) + stateGod;
            stateDead = Long.parseLong(arrayList.get(4)) + stateBad;
            petName = arrayList.get(5);
            birthdayDate = Long.parseLong(arrayList.get(6));
            multiplier = Double.parseDouble(arrayList.get(9));
            foodSprite = new Image(new FileInputStream(arrayList.get(8)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        long d = new Date().getTime();
        if(d - lastFeed > stateDead ){
            gameStatus = GameStatus.MAKE_NEW;
        } else {
            if(d - lastFeed > stateBad) {
                gameStatus = GameStatus.ALREADY_DEAD;
            } else {
                gameStatus = GameStatus.STILL_ALIVE;
                this.pet = new Pet(petSprite, petName);
                int day = (int)(d - birthdayDate) / 86400000;
                pet.setScale(1.0 + day / 4.0);


                tooltip = new Tooltip();
                tooltip.setFont(new Font(18));
                Tooltip.install(pet, tooltip) ;
                updateTooltip("excellent");
            }
        }


    }
    public void feedPet(){
        Date date = new Date();
        int day = (int)(date.getTime() - birthdayDate) / 86400000;
        pet.setScale(1.0 + day / 4.0);
        arrayList.set(1, "" + date.getTime());
        register.writeTxtFile(arrayList, "save.txt");
    }

}
