package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscountPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final을 찾아서 생성자를 만들어준다. 엄청 간결해짐. 롬복이 자바의 어노테이션 프로세서 라는 기능을 사용해서
                            // 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
public class OrderServiceImpl implements OrderService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 필드에 바로 생성자 주입 해버리면 변경이 불가능해 테스트가 불가능하다!! 쓰지말자. 테스트 코드, Configuration 에서만 쓰자
    private final MemberRepository memberRepository;

    //dip 위반, 잘 만든 것 같지만 인터페이스 뿐만 아니라 실제 구현클래스에도 의존하고 있음.
    //그래서 정책 바꾸려면 소스코드 변경해야 함. -> ocp 위반
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //아래 처럼 바꾸면 되는데 테스트 돌리면 할당이 안되어서 널이 들어가서 오류발생
    //-> 누군가 DiscountPolicy의 구현 객체를 주입해주어야 한다.

    // 이제는 디스카운트폴리시 인터페이스에만 의존함.
    // 파이널 이라 무조건 값을 할당해야 함(필수)!! 이것이 생성자 주입방법, 불변, 필수 의존관계에 사용됨
    private final DiscountPolicy discountPolicy;

    /*
    세터를 이용한 생성자 주입, 파이널을 빼고 오토와이어드를 붙여준다. 스프링 빈을 등록한 뒤에, 의존관계 주입을 오토와이어드를 통해 진행
    생성자를 이용하면 자바코드상 new ~impl(Mem~, dis~)를 통해 빈을 등록하기 때문에 등록과 동시에 주입이 된다.
    수정자 주입은 선택, 변경 가능성이 있는 의존관계에 사용됨.
    오토와이어드는 주입할 대상이 없으면 오류가 발생하지만,
    오토와이어드 옆에 (required = false)를 등록해서 진행하면 오류가 발생하지 않는다. (디폴트는 트루)
     */
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

//     받아서 할당시킴.
//     어떤 구현객체가 들어올지 알 수 없음.
   @Autowired // 오토와이어드 생성자 1개라면 생략할 수 있음.
    // 디스카운트 폴리시 타입이 빈에서 2개 발견됨. 오류!
    public OrderServiceImpl(MemberRepository memberRepository, /*@Qualifier("mainDiscountPolicy")*/ @MainDiscountPolicy DiscountPolicy discountPolicy /*rateDiscountPolicy*/) {
        // 만약 퀄리파이어가 퀄리파이어로 찾지 못하면, 스프링 빈엣서 이름으로 찾는다. 근데 퀄리파이어는 퀄리파이어만 찾는게 좋다.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
        // this.discountPolicy = rateDiscountPolicy; // 롬복은 타입 다음에 이름으로 찾는데, 이러면 이름으로 레이트를 찾는다.
    }

    /*
    메서드 이용 방법, 필드 여러개 주입 받을 수 있지만, 자주 안쓰인다
    당연한 얘기지만 오더서비스 임플이 빈이기 때문에 오토와이어드가 동작하는거다!!!!
     */
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//
//    }
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        // 잘만들어짐. 오더서비스 입장에선 디스카운트 폴리시에 모든걸 알아서 해달라고 함. 단일체계원칙 잘지킴
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 과연 멤버서비스임플과 다를까?
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
