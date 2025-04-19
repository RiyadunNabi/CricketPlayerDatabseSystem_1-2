package com.example.myjavafxproject.cricketdatabase;

import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String country;
    private int age;
    private double height;
    private String club;
    private String position;
    private Integer jerseyNumber;
    private int weeklySalary;
    private String askingPrice;
    public Player(String name, String country, int age, double height, String club, String position, Integer jerseyNumber, int weeklySalary) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.club = club;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.weeklySalary = weeklySalary;
    }
    
    public String getName() {return name;}
    public String getCountry() {return country;}
    public int getAge() {return age;}
    public double getHeight() {return height;}
    public String getClub() {return club;}
    public String getPosition() {return position;}
    public int getWeeklySalary() {return weeklySalary;}
    public Integer getJerseyNumber() {return jerseyNumber;}
    public String getAskingPrice(){ return askingPrice; }
    

    public void setName(String name) {this.name = name;}
    public void setCountry(String country) {this.country = country;}
    public void setAge(int age) {this.age = age;}
    public void setHeight(double height) {this.height = height;}
    public void setClub(String club) {this.club = club;}
    public void setPosition(String position) {this.position = position;}
    public void setJerseyNumber(Integer jerseyNumber) {this.jerseyNumber = jerseyNumber;}
    public void setWeeklySalary(int weeklySalary) {this.weeklySalary = weeklySalary;}
    public void setAskingPrice(String askingPrice){ this.askingPrice=askingPrice;}

    public String toCSV()
    {
        return String.format("%s,%s,%d,%.2f,%s,%s,%s,%d", name,country,age,height,club,position,jerseyNumber != -1 ? jerseyNumber : "", weeklySalary);
    }
    public void showPlayerInfo()
    {
        System.out.println("-----------------------------------------");
        System.out.println(   "Name          : "+ name +
                            "\nCountry       : " + country+
                            "\nAge           : "+age+
                            "\nHeight        : "+height+
                            "\nClub          : "+club+
                            "\nPosition      : "+position+
                            "\nJersey Number : "+ jerseyNumber+
                            "\nWeekly Salary : "+weeklySalary);
        System.out.println("-----------------------------------------");
    }

}
