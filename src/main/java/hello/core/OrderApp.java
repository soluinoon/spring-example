package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        //MemberService memberService = new MemberServiceImpl();
//        MemberService memberService = appConfig.memberService();
//        //OrderService orderService = new OrderServiceImpl();
//        OrderService orderService = appConfig.orderService();

        // 어플리케이션 컨텍스트 -> 스프링 컨테이너
        // 기존에는 앱컨픽을 사용해서 직접 객체를 생성하고 di를 했지만 컨테이너를 사용
        // 스프링 컨테이너는 컨피규레이선이 붙은 앱컨픽을 설정정보로 사용한다. 여기서 빈이라 적힌 메서드를 모두 호출
        // 반환된 객체를 스프링 컨테이너에 등록. 이렇게 등록된 객체를 스프링 빈이라 함. 메서드 이름을 기준으로 함.
        // 어플리케이션 컨텍스트는 인터페이스임!!
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 20000);

        System.out.println("order = " + order);
        System.out.println("order = " + order.calculatePrice());
    }
}
