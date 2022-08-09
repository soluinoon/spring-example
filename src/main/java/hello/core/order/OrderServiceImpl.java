package hello.core.order;

import hello.core.discount.DiscountPolicy;
//import hello.core.discount.FixDiscountPolicy;
//import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    //dip 위반, 잘 만든 것 같지만 인터페이스 뿐만 아니라 실제 구현클래스에도 의존하고 있음.
    //그래서 정책 바꾸려면 소스코드 변경해야 함. -> ocp 위반
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //아래 처럼 바꾸면 되는데 테스트 돌리면 할당이 안되어서 널이 들어가서 오류발생
    //-> 누군가 DiscountPolicy의 구현 객체를 주입해주어야 한다.

    // 이제는 디스카운트폴리시 인터페이스에만 의존함.
    private final DiscountPolicy discountPolicy;

    // 받아서 할당시킴.
    // 어떤 구현객체가 들어올지 알 수 없음.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
