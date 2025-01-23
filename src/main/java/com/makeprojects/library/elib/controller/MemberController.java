package com.makeprojects.library.elib.controller;

import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // Moved to AUthController
//    @PostMapping("/add")
//    public ResponseEntity<Member> addMember(@RequestBody Member member) {
//        Member addedMember = this.memberService.addMember(member);
//        return new ResponseEntity<>(addedMember, HttpStatus.CREATED);
//    }

    @PostMapping("/update")
    public ResponseEntity<Member> updateMember(@RequestBody Member member) {
        Member updatedMember = this.memberService.updateMember(member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = this.memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMemberById(@PathVariable UUID memberId) {
        Member member = this.memberService.getMemberById(memberId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Member> getMemberByEmail(@RequestParam String email) {
        Member member = this.memberService.getMemberByEmail(email);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }


}
