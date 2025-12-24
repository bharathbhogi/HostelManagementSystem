package model;

public class Student {
    private int id;
    private String name;
    private String roomNo;
    private String phone;

    public Student(String name, String roomNo, String phone) {
        this.name = name;
        this.roomNo = roomNo;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getRoomNo() { return roomNo; }
    public String getPhone() { return phone; }
}
