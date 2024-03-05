package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class ManyToOneTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 단방향 테스트")
    void test1() {
        User user = new User();
        user.setName("Robbie");

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
        food.setUser(user); // 외래 키(연관 관계) 설정

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);
        food2.setUser(user); // 외래 키(연관 관계) 설정

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패")
    void test2() {

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
        User user = new User();
        user.setName("Robbie");
        user.getFoodList().add(food);
        user.getFoodList().add(food2);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);

        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
    void test3() {

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);

        Food food2 = new Food();
        food2.setName("양념 치킨");
        food2.setPrice(20000);

        // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드 생성하고
        // 해당 메서드에 외래 키(연관 관계) 설정 food.setUser(this); 추가
        User user = new User();
        user.setName("Robbie");
        user.addFoodList(food);
        user.addFoodList(food2);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트")
    void test4() {
        User user = new User();
        user.setName("Robbert");

        Food food = new Food();
        food.setName("고구마 피자");
        food.setPrice(30000);
        food.setUser(user); // 외래 키(연관 관계) 설정

        Food food2 = new Food();
        food2.setName("아보카도 피자");
        food2.setPrice(50000);
        food2.setUser(user); // 외래 키(연관 관계) 설정

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }

    @Test
    @DisplayName("N대1 조회 : Food 기준 user 정보 조회")
    void test5() {
        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 위와 같이 food 객체를 가져왔다. user를 알려면 객체로 조회할 수 있다. food 쪽에 user 객체를 갖고 있다.

        // 음식 정보 조회
        System.out.println("food.getName() = " + food.getName());

        // 음식을 주문한 고객 정보 조회
        // Entity에 @Getter가 있기 때문에 getUser 등을 할 수 있는 것이다.
        // getUser로 user 객체가 반환이 된다!
        // 그래서 그 user 객체에 대해 .getName()을 한 것이다!
        System.out.println("food.getUser().getName() = " + food.getUser().getName());

        // food 가져왔다.
        // food에는 User Entity type의 필드가 있다.
        // 엔티티에서 상대 엔티티를 조회하기 위해서 상대 엔티티 '필드 타입'을 갖고 있는 것이다!
        // 관계를 맺는다는 건 결국엔 상대 엔티티를 조회하기 위함이다.



    }

    @Test
    @DisplayName("N대1 조회 : User 기준 food 정보 조회")
    void test6() {
        User user = userRepository.findById(1L).orElseThrow(NullPointerException::new);
        // 고객 정보 조회
        System.out.println("user.getName() = " + user.getName());

        // 해당 고객이 주문한 음식 정보 조회
        List<Food> foodList = user.getFoodList();
        for (Food food : foodList) {
            System.out.println("food.getName() = " + food.getName());
            System.out.println("food.getPrice() = " + food.getPrice());
        }
    }
}