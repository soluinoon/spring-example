package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 스프링 부트 에플리케이션에 컴포넌트 스캔이 걸려있어서 빈 등록같은게 된다.
// 컴포넌트, 서비스, 레포지토리 ,컨트롤러는 컴포넌트가 어노테이션 되어있어서 컴포넌트 스캔이 된다.
// 알아두면 좋은 것은 어노테이션은 상속관계가 없다! 자바가 지원하는게 아니라 스프링이 찾아주는거임
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(CoreApplication.class, args);
	}

}
