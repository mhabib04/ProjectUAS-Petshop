package com.example.projectuas_petshop.model.adapter;

public class GetData {
    private int id_animal;
    private String type;
    private String breed;
    private int price;
    private int age;

    public GetData(int id_animal, String type, String breed, int price, int age) {
        this.id_animal = id_animal;
        this.type = type;
        this.breed = breed;
        this.price = price;
        this.age = age;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
