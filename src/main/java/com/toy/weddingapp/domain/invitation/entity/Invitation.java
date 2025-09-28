package com.toy.weddingapp.domain.invitation.entity;

import com.toy.weddingapp.common.constant.Status;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Invitation {

    @GeneratedValue
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "weddings_id")
    private Weddings weddings;

    @Column(length = 300)
    private String url;

    @Column(length = 500)
    private String shortUrl;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300)
    private String message;

    @Column(length = 300)
    private String templateId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

}
