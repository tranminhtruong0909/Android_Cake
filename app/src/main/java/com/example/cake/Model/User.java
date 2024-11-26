package com.example.cake.Model;

public class User {
    private String uidd;
    private String name;
    private String address;
    private String phoneNumber;
    private int age;
    private String email;
    private String role;

    // Constructor mặc định (không có tham số)
    public User() {
        // Constructor mặc định cần thiết cho Firebase
    }

    // Constructor với tất cả các tham số (để khởi tạo đối tượng)
    public User(String uidd, String name, String address, String phoneNumber, int age, String email, String role) {
        this.uidd = uidd;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
        this.role = role;
    }

    // Getter và Setter cho tất cả các thuộc tính
    public String getUidd() {
        return uidd;
    }

    public void setUidd(String uidd) {
        this.uidd = uidd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
