package hello.core.singleton;

public class StatefulService {

   // private int price; // 상태를 유지하는 필드

    /*
    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 여기가 문제
    }
    */

    // 싱글톤에선 무상태로 만드는게 좋다
    // 무조건 공유필드를 조심해야함.
    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

    /*
    public int getPrice() {
        return price;
    }
    */
}
