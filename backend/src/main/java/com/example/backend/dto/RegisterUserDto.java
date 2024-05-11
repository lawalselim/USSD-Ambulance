package com.example.backend.dto;

public class RegisterUserDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;


    //hard codding lombok getter and setter
    public String getEmail() {
        return email;
    }
    public RegisterUserDto setEmail(String email){
        this.email  = email;
        return this;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public RegisterUserDto setphoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }
    public String getPassword(){
        return password;
    }

    public RegisterUserDto setPassword ( String password){
        this.password = password;
        return this;
    }

    public String  getName(){
        return name;
    }

    public RegisterUserDto setName(String name){
        this.name =name;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ",phoneNumber='" + phoneNumber+ '\''+
                ", password='" + password + '\'' +
                '}';
    }
}
