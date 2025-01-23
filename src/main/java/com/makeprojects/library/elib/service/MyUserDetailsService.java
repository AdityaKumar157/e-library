package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MyUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private static final String PASSWORD = "$2a$12$hUudb7V6/OVhheFWRaj/ZOAFXYWrNg8ghvpWFXFTzvBUc4ynwnaIC";
//    private List<User> userList = Arrays.asList(
//            new User("member", PASSWORD, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"))),
//            new User("librarian", PASSWORD, Collections.singletonList(new SimpleGrantedAuthority("ROLE_LIBRARIAN")))
//    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return this.userList.stream().filter(user -> user.getUsername().equals(username))
//                .findFirst()
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<Member> optionalMember = this.memberRepository.findByUserName(username);

        if(optionalMember.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Member not found with username '{0}'.", username));
        }

        Member member = optionalMember.get();

        return User.builder()
                .username(member.getUserName())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .build();
    }
}
