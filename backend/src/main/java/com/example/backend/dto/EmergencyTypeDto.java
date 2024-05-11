package com.example.backend.dto;

public class EmergencyTypeDto {

    private String name;
    private String description;

    //getter and setter goes here

    public String getName(){
        return name;
    }

    public EmergencyTypeDto setName (String name){
        this.name = name;
        return this;
    }

   public String getDescription(){
        return description;
   }

   public EmergencyTypeDto setDescription(String description){
        this.description = description;
        return this;
   }

   @Override
    public String toString(){
        return "EmergencyTypeDto{" +
                ",  name='" + name + '\'' +
                ", description='" + description +'\'' +
                '}';
   }
}
