package com.junior.usuario.infraistructure.entity;

import  jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "endereco")

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private long numero;
    @Column(name = "complemento", length = 15)
    private String complemento;
    @Column(name = "cidade", length = 25)
    private String cidade;
    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "cep", length = 9)
    private String cep;


}
