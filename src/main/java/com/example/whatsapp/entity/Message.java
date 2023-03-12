package com.example.whatsapp.entity;

import com.example.whatsapp.general.MessageType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @JsonBackReference
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private User sender;

    @JsonBackReference
    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Group receiver;

    @Column
    private String text;

    @Column(nullable = false)
    private LocalDateTime date;
}
