package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {
    
    @Test
    void AutoWiredOption() {
        // 스프링 빈으로 등록
       ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }
    
    static class TestBean {
        /*
        만약 폴스 옵션을 주지 않으면 멤버가 빈으로 등록되지 않았기 때문에 오류가 발생한다.
        멤버는 스프링 빈이 아님!!
         */
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        /*
        들어오지만 널로 들어옴
         */
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        /*
        옵셔널은 옵셔널로 감쌈. 널이면 엠프티 값이 있으면 값으로.
         */
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
        /*
        너러블과 옵셔널은 스프링 전반에 사용 가능하다. 생성자 자동 주입에서 특정 필드에만 사용해도 됨!!
         */
    }
}
