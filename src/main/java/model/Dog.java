package model;

import annotations.SaveFieldLworm;

import java.io.Serializable;

public class Dog implements Serializable {
    @SaveFieldLworm
    private String name;
    @SaveFieldLworm
    private String breed;
    @SaveFieldLworm
    private double coin;
    @SaveFieldLworm
    private int age;

    public Dog(){}
    public Dog(String name, String breed, long coin, int age) {
        this.name = name;
        this.breed = breed;
        this.coin = coin;
        this.age = age;
    }

    public void bark(){
        System.out.println("bark");
    }
    public void bark(String barkSound){
        System.out.println(barkSound);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*
    @Override
    public String toString(){
        return String.format("Name: %s Breed: %s Crypto-Wallet: $%f age: %d",name,breed,coin,age);
    }
    */

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", coin=" + coin +
                ", age=" + age +
                '}';
    }
}
