package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    @OneToOne
    // 외래 키 주인만이 외래 키를 등록, 수정, 삭제 할 수 있으며, 주인이 아닌 쪽은 오직 외래 키를 읽기만 가능하다.
    // @JoinColumn은 외래 키의 주인이 활용하는 애터네이션이다.
    @JoinColumn(name = "user_id")
    // 상대 엔티티의 데이터타입과 필드명을 적는다.
    private User user;
}