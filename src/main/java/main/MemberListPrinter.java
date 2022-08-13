package main;

import domain.Member;
import domain.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class MemberListPrinter {

    private MemberDao memberDao;
    private MemberPrinter memberPrinter;

    public MemberListPrinter(){
    }

    public void printAll(){
        Collection<Member> members = memberDao.selectAll();
        members.forEach(m-> memberPrinter.print(m));
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setMemberPrinter(MemberPrinter memberPrinter) {
        this.memberPrinter = memberPrinter;
    }
}
