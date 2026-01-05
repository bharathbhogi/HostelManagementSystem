package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_id")
    private int studentId;


    @Column(precision = 10, scale = 2)
    private BigDecimal amount;


    @Column(name = "paid_date")
    private LocalDate paidDate;

    public Fee() {}

    public Fee(int studentId, BigDecimal amount) {
        this.studentId = studentId;
        this.amount = amount;
        this.paidDate = LocalDate.now();
    }

    public int getStudentId() {
        return studentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }
}
