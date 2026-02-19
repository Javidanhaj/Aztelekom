package com.aztelekom.internship.domain.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


}
