package data;

import java.io.Serializable;

public class Player implements Serializable {
    String name;
    String country;
    Club club;
    int age;
    int number;
    String position;
    double height;
    double salary;
    boolean isBeingSold = false;
    int id;
    double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isBeingSold() {
        return isBeingSold;
    }

    public void setBeingSold(boolean beingSold) {
        isBeingSold = beingSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void showPlayerInfo(){

        System.out.printf("  %25s %20s %15s ",name,club.getName(),country);
        System.out.printf("%15s , %-3d %3d yrs %5.2f meters %10.2f $\n",position,number,age,height,salary);
        //System.out.println("----------------------------------------------------------------------------------------------------------------------------");


    }


}
