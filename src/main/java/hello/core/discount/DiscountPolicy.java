package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     *
     * @return 할인대상금액 (얼마가 할인됐는지)
     */
    int discount(Member member, int price);

}
