package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();



    @Test
    // junit 5부터 지원
    @DisplayName("VIP는 10퍼 할인이 적용되야 한다.")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        // 스태틱으로 올린다
        assertThat(discount).isEqualTo(1000);

    }

    @Test
    // junit 5부터 지원
    @DisplayName("VIP가 아니라면 할인이 적용되면 안된다.")
    void vip_x() {
        //given
        Member member = new Member(2L, "memberBASIC", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(0);

    }

}