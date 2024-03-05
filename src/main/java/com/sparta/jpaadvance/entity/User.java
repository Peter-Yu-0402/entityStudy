package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // 외래 키의 주인이 아니니까 mappedBy가 있다. mappedBy는 노예의 징표!
    // 여기서 mappedBy의 속성값, "user"는 '주인이 갖는 상대 엔티티의 필드명'이다.
    @OneToOne(mappedBy = "user")
    private Food food;


    // 이 메서드는 매우 중요하다.
    // '외래 키의 주인이 아닌 엔티티'가 외래 키 설정을 할 수 있는 방법이기 때문이다.
    public void addFood(Food food) {
        this.food = food;
        // this는 자기 자신의 객체, 이 경우엔 User user
        food.setUser(this);
    }
}