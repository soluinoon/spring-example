package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {

                                                                        // 프로토타입 빈을 넣어버려서 컴포넌트 스캔 바로 시킴
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        /*
        싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행되고, 프로토타입 스코프 빈은 조회할 때 ㅅ생성되고 초기화 메서드도 실행됨.
         */
        System.out.println("find PrototypeBean1");
        PrototypeBean PrototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find PrototypeBean2");
        PrototypeBean PrototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + PrototypeBean1);
        System.out.println("prototypeBean2 = " + PrototypeBean2);
        // isSameAS -> == 비교
        Assertions.assertThat(PrototypeBean1).isNotSameAs(PrototypeBean2);
        ac.close();
        // 디스트로이 호출 안됨.

    }

    @Scope("prototype")
    // 컴포넌트 스캔 안써도 위에서 바로 넣어서 컴포넌트 스캔
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
