package com.makeprojects.library.elib.core.service.implementation;

import com.makeprojects.library.elib.core.service.definition.MemberService;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder)
    {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member get(UUID id) {
        try {
            log.info("Getting member with id.");
            Optional<Member> optionalMember = this.memberRepository.findById(id);
            Member member = optionalMember.orElse(null);
            log.info("Member with id {}", member.getId());
            return member;
        } catch (Exception e) {
            log.error("Exception while getting member wit id {}: {}", id, e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public List<Member> getAll() {
        try {
            log.info("Getting all members");
            List<Member> members = this.memberRepository.findAll();
            log.info("Successfully get all members with count {}", members.size());
            return members;
        } catch (Exception e) {
            log.error("Exception while getting all members: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public Member create(Member entity) {
        try {
            log.info("Adding member");
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
            log.info("Encode password.");
            Member savedMember = this.memberRepository.save(entity);
            log.info("Successfully saved member with id {}", savedMember.getId());
            return savedMember;
        } catch (Exception e) {
            log.error("Exception while saving member: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public Member update(Member entity) {
        try {
            log.info("Updating member with id {}.", entity.getId());
            Member updatedMember = this.memberRepository.save(entity);
            log.info("Successfully updated member with id {}", updatedMember.getId());
            return updatedMember;
        } catch (Exception e) {
            log.error("Exception while updating member: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Member getMemberByEmail(String email) {
        try {
            log.info("Getting member with email.");
            Optional<Member> optionalMember = this.memberRepository.findByEmailLike(email);
            Member member = optionalMember.orElse(null);
            log.info("Member with email {}", member.getEmail());
            return member;
        } catch (Exception e) {
            log.error("Exception while getting member with email {}: {}", email, e.getLocalizedMessage());
            throw e;
        }
    }
}
