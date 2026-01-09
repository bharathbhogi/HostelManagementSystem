package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "students")
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_no")
    private String roomNo;

    @Column(name = "phone")
    private String phone;

    // 🔹 Required by Hibernate
    public Student() {}

    // 🔹 Optional constructor
    public Student(String name, String roomNo, String phone) {
        this.name = name;
        this.roomNo = roomNo;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getPhone() {
        return phone;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
