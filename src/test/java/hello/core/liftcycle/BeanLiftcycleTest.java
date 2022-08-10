package hello.core.liftcycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLiftcycleTest {

    @Test
    public void LifeCycleTest() {
        // configurable~이 Annotationconfig~의 부모라 담을 수 있음
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        /*
        스프링 빈은 객체 생성 -> 의존관계 주입의 단계를 거친다. 따라서 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가
        완료된다. 따라서 초기화 작업은 의존관계 주입이 끝나고 나서 호출되어야 한다. 그런데 개발자가 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
        스프링이 의존관계 주입이 완료되면 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주고 스프링 컨테이너가 종료되기 직전에 소멸 골백을 준다.
        */

        // 빈에 설정정보를 사용, 외부 라이브러리 에서도 초기화, 종료 메서드를 적용할 수 있다.
        // 그리고 디스트로이 메서드는 디폴트가 인퍼드(추론)인데, close, shutdown이라는 이름의 메서드를 자동으로 호출해준다.
        @Bean  // (initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
