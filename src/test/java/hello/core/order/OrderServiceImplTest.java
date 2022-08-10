package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    /*
    생성자 주입을 사용하자. 그 이유는
    1. 불변 - 게터세터가 없고, 생성자 주입은 객체를 생설할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없어서 불변하게 설계 가능
    2. 누락 - 오류를 테스트할 때 오토와이어드가 의존관계가 없으면 오류가 발생하지만, 생성자는 돌아는 간다.
    3. 파이널 - 필드에 파이널을 사용할 수 있어서 컴파일 시점에서 오류를 잡아낼 수 있다.

    정리하자면, 프레임 워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살릴 수 있다.
     */
    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        /*
        널포 익셉션이 뜬다. 가짜라도 만들어서 넣어줘야함.
         */
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}