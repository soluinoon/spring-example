package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 실제 동작에 필요한 구현객체 만듬
public class AppConfig {

    public MemberService memberService() {
        // 임플 생성자에서 메모리 멤버 리파지토리가 들어가게 만듬
        // 생성자 주입
        // 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입해준다.
        return new MemberServiceImpl(memberRepository());
    }

    // 중복 제거되고 간결해짐. 어떤 역할과 구현 클래스인지 다 알 수 있다.
    private MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
        //return new FixDiscountPolicy();
    }
}
