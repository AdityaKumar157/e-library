package com.makeprojects.library.elib.controller;

import com.makeprojects.library.elib.ELibraryApplication;
import com.makeprojects.library.elib.core.service.definition.MemberService;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.enums.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest(classes = ELibraryApplication.class)
public class MemberControllerTest {

    @MockBean
    private MemberService memberService;
    private final MemberController memberController;

    @Autowired
    public MemberControllerTest(MemberController memberController) {
        this.memberController = memberController;
    }

    static Member member = Member.builder()
            .id(UUID.randomUUID())
            .firstName("TestFirstName")
            .lastName("TestLastName")
            .email("testEmail@test.com")
            .mobileNumber("1234567893")
            .subscriptionStatus(SubscriptionStatus.INACTIVE)
            .build();

    //@Test
//    void addMember_whenMemberIsPassed() {
//        Mockito.when(this.memberService.create(member))
//                .thenReturn(member);
//
//        ResponseEntity<Member> responseEntity = this.memberController.addMember(member);
//
//        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        Assertions.assertEquals(member, responseEntity.getBody());
//    }
}
