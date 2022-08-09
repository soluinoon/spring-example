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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 실제 동작에 필요한 구현객체 만듬

// 설정정보 컨피규레이션
@Configuration
public class AppConfig {

    // 스프링 컨테이너는 싱글톤을 보장해주지만, 과연 아래는?
    // @Bean memberService() -> new new MemoryMemberRepository();
    // @Bean orderService() -> new new MemoryMemberRepository();
    // 2번 호출되는데 싱글톤이 깨지나?

    // 빈으로 스프링 컨테이너에 저장
    @Bean
    public MemberService memberService() {
        // 임플 생성자에서 메모리 멤버 리파지토리가 들어가게 만듬
        // 생성자 주입
        // 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입해준다.
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    // 중복 제거되고 간결해짐. 어떤 역할과 구현 클래스인지 다 알 수 있다.
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
        //return new FixDiscountPolicy();
    }
}
