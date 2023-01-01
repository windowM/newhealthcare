package com.example.newhealthcare.entity;

import com.example.newhealthcare.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"doctorId","patientId"})
public class DandP extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="connect_sequence", nullable = false)
    private Long connectSq;

    //DandP N:1 Doctor
    @ManyToOne
    private Doctor doctorId;

    //DandP N:1 Patient
    @ManyToOne
    private Patient patientId;

    private String code;

    private Long usable;
}

