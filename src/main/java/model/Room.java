package model;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_no")
    private String roomNo;

    private String status;

    private int capacity;

    public Room() {}

    public String getRoomNo() {
        return roomNo;
    }

    public String getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
