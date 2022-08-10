package hello.core.scan;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.AutoAppConfig;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    /*
    빈은 클래스 이름을 저장할 때 맨 앞글자를 소문자로 저장한다.
    ex) MemberServiceImpl -> memberServiceImpl
    직접 지정하고 싶으면 @Component("memberService2") 이런식으로 지정
     */

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}
