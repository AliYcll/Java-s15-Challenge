package com.workintech.library.service.member;

import com.workintech.library.model.Library;
import com.workintech.library.model.Member;

import java.util.Map;

public class MemberService {

    private final Library library;

    public MemberService(Library library) {
        this.library = library;
    }

    public void addMember(Member member) {
        Map<Integer, Member> members = library.getMembers();
        members.put(member.getId(), member);
    }
    public Member findMemberById(int id) {
        Map<Integer, Member> members = library.getMembers();
        return members.get(id);
    }


}
