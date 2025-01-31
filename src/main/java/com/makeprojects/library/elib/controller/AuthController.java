package com.makeprojects.library.elib.controller;

import com.makeprojects.library.elib.core.service.definition.MemberService;
import com.makeprojects.library.elib.dto.AuthDto;
import com.makeprojects.library.elib.dto.AuthResponse;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto authDto) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(userDetails, jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addMember(@RequestBody Member member) {
        Member memberToAdd = this.memberService.create(member);
        UserDetails userDetails = new User(memberToAdd.getUserName(), memberToAdd.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(memberToAdd.getRole())));

        String jwtToken = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(userDetails, jwtToken));
    }
}
