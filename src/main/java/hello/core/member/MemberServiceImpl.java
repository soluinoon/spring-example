package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // 멤버 서비스 임플이 멤버 레포지터리에도 의존하고 메모리멤버레포지토리에도 의존하고 있어서 문제 -> dip 위반
    private final MemberRepository memberRepository;

    // 의존관계 주입 (DI)
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
}
