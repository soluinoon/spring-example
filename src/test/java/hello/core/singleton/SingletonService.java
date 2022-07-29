package hello.core.singleton;

public class SingletonService {

    // static이라 클래스 레벨로 올라가서 하나만 설명됨
    // 자바 실행할 때 인스턴스 하나 가지고 있음.
    private static final SingletonService instance = new SingletonService();

    // 생성 못하고 얘로만 꺼낼 수 있음.
    // 이 메서드 호출하면 항상 같은 인스턴스를 반환함.
    // 딱 하나만 있음.
    public static SingletonService getInstance() {
        return instance;
    }
    //생성자를 프라이빗으로 줘서 다른 곳에서 못 만들도록!! 진짜 중요함.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
