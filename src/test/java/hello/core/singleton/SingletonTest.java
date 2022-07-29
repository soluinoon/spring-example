package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 di 컨테이너")
    void pureContainer() {
        // 앱컨픽 가서 모두 싱글톤으로 바꿀 필요가 없음.
        // 스프링 컨테이너가 알아서 싱글톤으로 관리해준다
        AppConfig appConfig = new AppConfig();

        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2.
        MemberService memberService2 = appConfig.memberService();

        //참조값 다른 것 확인
        //System.out.println("memberService1 = " + memberService1);
        //System.out.println("memberService2 = " + memberService2);

        // 이러면 요청이 올떄마다 객체가 생성해야함 -> 웹 어플리케이션은 요청이 많다.
        // 해결을 위해 객체를 하나만 만들고 공유해서 쓴게 만든다!! -> 싱글톤 패턴
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);

    }

/*
    public static void main(String[] args) {
        SingletonService singletonService1 = new SingletonService();
    }

 */
    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("1 = " + singletonService1);
        System.out.println("2 = " + singletonService2);

        // 진짜 인스턴스가 같은지 비교
        assertThat(singletonService1).isSameAs(singletonService2);
        // same == -> 객체 인스턴스가 같은지
        // equal -> equls
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        // 앱컨픽 가서 모두 싱글톤으로 바꿀 필요가 없음.
        // 스프링 컨테이너가 알아서 싱글톤으로 관리해준다
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        //2.
        //참조값 같은 것 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 이러면 요청이 올떄마다 객체가 생성해야함 -> 웹 어플리케이션은 요청이 많다.
        // 해결을 위해 객체를 하나만 만들고 공유해서 쓴게 만든다!! -> 싱글톤 패턴

        // 싱글톤
        // 효율적임. 근데 빈이 싱글톤만 지원하는 것은 아니다. 99퍼는 싱글톤만 씀
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
