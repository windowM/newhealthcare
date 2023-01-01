package com.example.newhealthcare;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass    //createdDate,modifiedDate 도 컬럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) //BaseEntity에 Auditing 기능을 포함 시킨다.

//모든 Entity클래스들에게 상속시켜 시간값을 자동 할당 해주는 역할
public abstract class BaseEntity {

    @Column(name = "created_at",nullable = false, updatable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "updated_at")
    @LastModifiedDate
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}