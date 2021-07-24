package network.dto;

import data.Player;

import java.io.Serializable;

public class PlayerEditInfo implements Serializable {

    String name;
    String country;
    int age;
    int number;
    String position;
    double height;
    double salary;
    int id;
    int clubId;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public PlayerEditInfo(Player p) {
        name = p.getName();
        country = p.getCountry();
        position = p.getPosition();
        age = p.getAge();
        number = p.getNumber();
        height = p.getHeight();
        salary = p.getSalary();
        id = p.getId();
        clubId = p.getClub().getId();
    }

    public PlayerEditInfo(){

    }
}
