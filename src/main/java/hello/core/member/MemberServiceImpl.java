package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 오토와이어 없으면 의존관계 주입이 안됨. 임플이 빈으로 바로 들어가버림.
//
public class MemberServiceImpl implements MemberService {

    // 멤버 서비스 임플이 멤버 레포지터리에도 의존하고 메모리멤버레포지토리에도 의존하고 있어서 문제 -> dip 위반
    private final MemberRepository memberRepository;

    // 의존관계 주입 (DI)
    @Autowired
    // 멤버 리파지토리에 맞는 타입을 찾아서 빈에 등록시킴. 지금은 메모리 멤버 리파지토리.
    // (ac.getBean(MemberRepository.class)와 비슷
    /*
    오토 와이어는 기본 조회 전략으로 타입이 같은 빈을 찾아서 주입한다.
    생성자가 많아도 알아서 해줌.
     */
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return (memberRepository.findById(memberId));
    }

    // 테스트 용도 (스프링 컨테이너가 싱글톤인지)
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
