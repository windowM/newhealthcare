package com.example.newhealthcare.entity;

import com.example.newhealthcare.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@DynamicInsert
//@DynamicUpdate
//@ToString(exclude = {"dandpList","diagnosisList"})
//@SequenceGenerator(
//        name = "customer_seq",
//        sequenceName = "customer_seq",
//        initialValue = 1,
//        allocationSize = 1
//)
public class Doctor extends BaseEntity {

//    @Column(name="sequence")
//    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
//    private Long sq;

    @Id
    @Column(name="doctor_id")
    private String doctorId;

    private String password;

    private String email;

    private String name;

    private String phone;

    private String major;

    private String code;


    //Doctor 1 : N DandP
    //cascadeType.Remove= 의사가 삭제될시 환자들도 모두 삭제 (어디 테이블의 환자까지 적용 될진 모름)
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "doctorId")
    private List<DandP> dandpList;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "doctorId")
    private List<Diagnosis> diagnosisList;

}
