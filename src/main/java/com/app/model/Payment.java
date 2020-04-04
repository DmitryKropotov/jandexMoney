package com.app.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="payment")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "senderId")
    private int senderId;
    @Column(name = "receiverId")
    private int receiverId;
    @Column(name = "amount")
    private int amount;
}
