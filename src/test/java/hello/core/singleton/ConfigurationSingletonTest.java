package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;


public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 셋이 같은 인스턴스!! -> 스프링 컨테이너는 싱글톤을 보장
        // 왜 5번 호출이 안될까? 콜은 3번 뜨는게 확인됨.

        // 기대
        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.memberRepository
        // call AppConfig.orderService
        // call AppConfig.memberRepository

        // 출력값
        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.orderService
        // 멤버가 3번 호출되야 하는데, 1번만 호출됨
        // 자바 코드는 3번 호출되는게 맞음!!!
        // @Configuration 에 비밀이 있다

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        configurationDeep();
        //bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$c329221c
        // $$로 이상한게 붙어있음
        // 순수한 클래스라면 bean = class hello.core.AppConfig
        // 사실 내가 만든 클래스가 아니라 스프링이 cglib이라는 바이트코드 조작 라이브러리를 사용해 appconfig을 상속받은
        // 임의의 다른 클래스를 만들고 그 다른 클래스를 스프링 빈으로 등록한 것임.
        // 이 상속받은 클래스에서 스프링 빈이 존재하면 존재하는 빈을 반환하게 하고 없으면 생성해서 빈으로 등록하고 반환하는 코드가
        // 동적으로 만들어져 싱글톤을 보장함.
        }

        @Test
        void configurationDeep() {

        // appconfig도 빈으로 등록됨.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // @Configuration을 사용하지 않으면 빈으로 등록은 되지만 CGLIB이 사용이 안되어
        // 싱글톤이 보장이 안되기 때문에 멤버리파지토리를 3번 호출한다.
        // 1번은 등록, 2, 3번은 자바 코드 그대로
        // 결국 new 해서 주는것과 다름 없기 때문에 스프링 컨테이너에서 관리가 안된다.
        System.out.println("bean = " + bean.getClass());
    }
}
