package hou.edu.vn.ngvtuan.admin_foodapp.Model;


public class FoodModel {
    String imageFood,nameFood,descFood,typeFood,timeCooking;
    int priceFood,quantitySold;

    public FoodModel() {
    }

    public FoodModel(String imageFood, String nameFood, String descFood, String typeFood, String timeCooking, int priceFood, int quantitySold) {
        this.imageFood = imageFood;
        this.nameFood = nameFood;
        this.descFood = descFood;
        this.typeFood = typeFood;
        this.timeCooking = timeCooking;
        this.priceFood = priceFood;
        this.quantitySold = quantitySold;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescFood() {
        return descFood;
    }

    public void setDescFood(String descFood) {
        this.descFood = descFood;
    }

    public String getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(String typeFood) {
        this.typeFood = typeFood;
    }

    public String getTimeCooking() {
        return timeCooking;
    }

    public void setTimeCooking(String timeCooking) {
        this.timeCooking = timeCooking;
    }

    public int getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(int priceFood) {
        this.priceFood = priceFood;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
