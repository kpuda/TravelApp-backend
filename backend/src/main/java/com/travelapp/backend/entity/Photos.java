package com.travelapp.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Photos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private byte[] picture;
}
