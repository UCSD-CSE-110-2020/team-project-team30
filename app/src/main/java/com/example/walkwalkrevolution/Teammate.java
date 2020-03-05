package com.example.walkwalkrevolution;

public class Teammate {
    private String firstName;
    private String lastName;
    private String iconInitials;
    private String email;

    //only firstName required, can pass in empty string for lastName
    public Teammate(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        createIconInitials();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}

    public void createIconInitials(){
        if(lastName.length() == 0){
            iconInitials = String.valueOf(firstName.charAt(0));
        }
        else{
            iconInitials = String.valueOf(firstName.charAt(0)) + String.valueOf(lastName.charAt(0));
        }
    }

    public String getIconInitials(){
        return this.iconInitials;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
