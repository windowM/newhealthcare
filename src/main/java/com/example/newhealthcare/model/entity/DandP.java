package com.example.newhealthcare.model.entity;

import com.example.newhealthcare.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@SequenceGenerator(
        name = "customer_seq",
        sequenceName = "customer_seq",
        initialValue = 1,
        allocationSize = 1
)
public class DandP extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @Column(name="connect_sequence")
    private Long connectSq;

    //DandP N:1 Doctor
    @ManyToOne
    @JoinColumn(name="doctor")
    private Doctor doctorId;

    //DandP N:1 Patient
    @ManyToOne
    @JoinColumn(name="patient")
    private Patient patientId;

    private String code;
}

