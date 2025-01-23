package com.makeprojects.library.elib.repository;

import com.makeprojects.library.elib.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmailLike(String email);

    Optional<Member> findByUserName(String userName);
}
