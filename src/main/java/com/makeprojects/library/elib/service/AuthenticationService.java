package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.dto.AuthDto;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.exceptions.IncorrectCredentialsException;
import com.makeprojects.library.elib.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(AuthDto authDto) {
        log.info("Trying to login user with username {}", authDto.getUsername());
        Optional<Member> optionalMember = this.memberRepository.findByUserName(authDto.getUsername());

        if(optionalMember.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Member not found with username '{0}'.", authDto.getUsername()));
        }

        Member member = optionalMember.get();
        log.info("Member exists with username {}", member.getUserName());

        if(!this.passwordEncoder.matches(authDto.getPassword(), member.getPassword())) {
            String errorMsg = String.format(Locale.ENGLISH, "Password is incorrect for username %s", member.getUserName());
            log.error(errorMsg);
            throw new IncorrectCredentialsException(errorMsg);
        }

        log.info("Username and password matches for user {}.", member.getUserName());
        return member;
    }

    public Member signUp(Member member) {
        try {
            log.info("Adding member");
            member.setPassword(this.passwordEncoder.encode(member.getPassword()));
            log.info("Encode password.");
            Member savedMember = this.memberRepository.save(member);
            log.info("Successfully saved member with id {}", savedMember.getId());
            return savedMember;
        } catch (Exception e) {
            log.error("Exception while saving member: {}", e.getLocalizedMessage());
            throw e;
        }
    }

}
