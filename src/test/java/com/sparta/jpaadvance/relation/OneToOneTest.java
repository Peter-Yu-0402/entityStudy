//package com.sparta.jpaadvance.relation;
//
//import com.sparta.jpaadvance.entity.Food;
//import com.sparta.jpaadvance.entity.User;
//import com.sparta.jpaadvance.repository.FoodRepository;
//import com.sparta.jpaadvance.repository.UserRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@Transactional
//@SpringBootTest
//public class OneToOneTest {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    FoodRepository foodRepository;
//
//    @Test
//    @Rollback(value = false) // 테스트에서는 @Transactional 에 의해 자동 rollback 됨으로 false 설정해준다.
//    @DisplayName("1대1 단방향 테스트")
//    void test1() {
//
//        User user = new User();
//        user.setName("Robbie");
//
//        // 외래 키의 주인인 Food Entity user 필드에 user 객체를 추가해 줍니다.
//        Food food = new Food();
//        food.setName("후라이드 치킨");
//        food.setPrice(15000);
//        // 상기 코드를 외래 키 설정, 연관 관계 설정이라고 한다.
//        food.setUser(user); // 외래 키(연관 관계) 설정
//        // 외래 키 주인만이 외래 키를 등록, 수정, 삭제 할 수 있으며, 주인이 아닌 쪽은 오직 외래 키를 읽기만 가능하다.
//
//        userRepository.save(user);
//        foodRepository.save(food);
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패")
//    void test2() {
//        Food food = new Food();
//        food.setName("고구마 피자");
//        food.setPrice(30000);
//
//        // 외래 키의 주인이 아닌 User 에서 Food 를 저장해보겠습니다.
//        // 할 수는 있는 거잖아. 효과는 없겠지만..!
//        User user = new User();
//        user.setName("Robbie");
//        user.setFood(food);
//
//        userRepository.save(user);
//        foodRepository.save(food);
//
//        // 확인해 보시면 user_id 값이 들어가 있지 않은 것을 확인하실 수 있습니다.
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("1대1 양방향 테스트 : 외래 키 저장 실패 -> 성공")
//    void test3() {
//        Food food = new Food();
//        food.setName("고구마 피자");
//        food.setPrice(30000);
//
//        // 외래 키의 주인이 아닌 User 에서 Food 를 저장하기 위해 addFood() 메서드 추가
//        // 외래 키(연관 관계) 설정 food.setUser(this); 추가
//        User user = new User();
//        user.setName("Robbie");
//        user.addFood(food);
//
//        userRepository.save(user);
//        foodRepository.save(food);
//    }
//
//    @Test
//    @Rollback(value = false)
//    @DisplayName("1대1 양방향 테스트")
//    void test4() {
//        User user = new User();
//        user.setName("Robbert");
//
//        Food food = new Food();
//        food.setName("고구마 피자");
//        food.setPrice(30000);
//        food.setUser(user); // 외래 키(연관 관계) 설정
//
//        userRepository.save(user);
//        foodRepository.save(food);
//    }
//
//    // 여기까지 다음의 사실을 증명하기 위해서였다. : 외래 키의 주인만이 외래 키를 컨트롤할 수 있다.
//    // -------------조회---------------
//
//    // 외래 키의 주인은 아니여도, 외래 키를 조회할 수는 있다. 아 조회 정도는 할 수 있잖아!
//
//
//    @Test
//    @DisplayName("1대1 조회 : Food 기준 user 정보 조회")
//    void test5() {
//        Food food = foodRepository.findById(1L).orElseThrow(NullPointerException::new);
//        // 음식 정보 조회
//        System.out.println("food.getName() = " + food.getName());
//
//        // 음식을 주문한 고객 정보 조회
//        System.out.println("food.getUser().getName() = " + food.getUser().getName());
//    }
//
//    @Test
//    @DisplayName("1대1 조회 : User 기준 food 정보 조회")
//    void test6() {
//        User user = userRepository.findById(1L).orElseThrow(NullPointerException::new);
//        // 고객 정보 조회
//        System.out.println("user.getName() = " + user.getName());
//
//        // 해당 고객이 주문한 음식 정보 조회
//        Food food = user.getFood();
//        System.out.println("food.getName() = " + food.getName());
//        System.out.println("food.getPrice() = " + food.getPrice());
//    }
//}