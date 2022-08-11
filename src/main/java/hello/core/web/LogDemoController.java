package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    //private final ObjectProvider<MyLogger> myLoggerProvider;
    /*
    오류가 발생한다. 왜냐면 마이로거는 리퀘스트 스코프라 고객의 요청이 들어와야만 동작하는데, 스프링을 처음 띄울 시점에는 요청이 없기 떄문이다.
    -> 프로바이더 사용
     */

    /*
    프록시를 사용하면 씨지립을 이용한 내 클래스를 상속받은 가짜 프록시 객체를 만들어서 주입한다.
    이 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있다.
    가짜 프록시 객체는 실제 리퀘스트 스콥과는 관계가 없고 그냥 가짜에 단순 위임로직만 있는 싱글톤임.
     */

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass());
        //MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
