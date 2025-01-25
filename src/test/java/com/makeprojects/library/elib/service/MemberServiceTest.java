package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.ELibraryApplication;
import com.makeprojects.library.elib.core.service.definition.MemberService;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.enums.SubscriptionStatus;
import com.makeprojects.library.elib.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest(classes = ELibraryApplication.class)
public class MemberServiceTest {

    private final MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    static Member member = Member.builder()
            .id(UUID.randomUUID())
            .firstName("TestFirstName")
            .lastName("TestLastName")
            .email("testEmail@test.com")
            .mobileNumber("1234567893")
            .subscriptionStatus(SubscriptionStatus.INACTIVE)
            .build();


}
