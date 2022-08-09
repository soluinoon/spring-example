package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록되기 떄문에, (소스 열어보면 컴포넌트 어노테이션 되어있음)
// appconfig도 컴포넌트가 붙어있고, 빈에 등록되기 때문에 빼줘야 한다.
// 예제를 최대한 남기기 위해서 썻음 실무에선 안그럼
@ComponentScan(
        //basePackages = "hello.core.member", // 범위 지정, 멤버 하위만 찾 {,}로 여러개도 가능
        //basePackageClasses = AutoAppConfig.class, // 이 클래스의 패키지
        // 지정하지 않으면 컴포넌트 스캔이 붙은 클래스 패키지의 하위를 다 뒤짐
        // 패키지가 헬로 코어이므로 다 뒤짐. 권장사항은 지금처럼 설정 정보 클래스를 프로젝트 최상단에 두고 베이스를 지정 하지 않으면 내 프로젝트 다 뒤짐
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

// @붙은거 다 컨테이너에 등록해줌
// @Bean붙은거 없다.

// 컴포넌트 스캔은 컴포넌트 어노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록해줌.
public class AutoAppConfig {

    // 자동 vs 수동 충돌
    // 수동이 우선권 가지고 오버라이딩, 로그에 남
    // 최근엔 스프링 부트에서 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값 바꿈.
    /*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
     */
}
