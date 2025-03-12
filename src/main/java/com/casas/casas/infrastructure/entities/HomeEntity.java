package com.casas.casas.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "casa")
@NoArgsConstructor
@AllArgsConstructor
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false) // Esto crea la columna id_category como FK
    private CategoryEntity category; // Relación directa a la entidad CategoryEntity
}
