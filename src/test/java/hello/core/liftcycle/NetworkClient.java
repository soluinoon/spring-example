package hello.core.liftcycle;

// 스프링 전용 인터페이스라 의존, 초기화 소멸 메서드의 이름 변경불가, 내가 코드를 고칠 수 없는 외부 라이브러리에 적용 못함. 비효율

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }
    /*
    객체의 생성과 초기화는 분리하는게 좋다.
     */
    public  void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }
    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    /*
    패키지가 자바 엑스이므로 스프링이 아닌 다른 컨테이너에서도 잘 작동. 따라서 스프링이 아닌 다른 컨테이너 에서도 잘 작동.
    유일한 단점은 외부 라이브러리에는 적용하지 못함. 이 때는 빈의 설정정보를 사용하자.
    그냥 포스트컨스트럭트, 프리디스트로이 애노테이션을 사용하면 된다.
     */
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
