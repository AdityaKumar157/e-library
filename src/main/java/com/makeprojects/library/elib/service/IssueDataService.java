package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.dto.IssueDataDto;
import com.makeprojects.library.elib.entity.Book;
import com.makeprojects.library.elib.entity.IssueData;
import com.makeprojects.library.elib.entity.Member;
import com.makeprojects.library.elib.enums.IssueStatus;
import com.makeprojects.library.elib.repository.IssueDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class IssueDataService {

    private final IssueDataRepository issueDataRepository;
    private final MemberService memberService;
    private final BookService bookService;

    @Autowired
    public IssueDataService(IssueDataRepository issueDataRepository, MemberService memberService, BookService bookService) {
        this.issueDataRepository = issueDataRepository;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    public IssueData addIssueData(IssueDataDto issueDataDto) {
        Member member = this.memberService.getMemberById(issueDataDto.getMemberId());
        Book book = this.bookService.getBookById((issueDataDto.getBookId()));

        if(member==null || book==null) {
            throw new RuntimeException();
        }

        IssueData issueData = IssueData.builder()
                .member(member)
                .book(book)
                .build();

        return this.addIssueData(issueData);
    }

    public IssueData addIssueData(IssueData issueData) {
        try {
            log.info("Adding issue data");
            issueData.calculateAmountPaid();
            issueData.calculateExpirationDate();
            IssueData savedIssueData = this.issueDataRepository.save(issueData);
            log.info("Successfully added issue data with id {}", issueData.getId());
            return savedIssueData;
        } catch (Exception e) {
            log.error("Exception while adding issue data: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    public IssueData updateIssueData(IssueData issueData) {
        try {
            log.info("Updating issue data with id {}", issueData.getId());
            IssueData updatedIssuedData = this.issueDataRepository.save(issueData);
            log.info("Successfully updated issue data with id {}", updatedIssuedData.getId());
            return updatedIssuedData;
        } catch (Exception e) {
            log.error("Exception while updating issue data with id {}: {}", issueData.getId(), e.getLocalizedMessage());
            throw e;
        }
    }

    public List<IssueData> markExpStatusOfIssueDataByMemberId(UUID memberId) {
        try {
            log.info("Checking and updating issue data of member id {}", memberId);
            List<IssueData> issueDataList = this.getIssueDataByMemberId(memberId);
            List<IssueData> expiredIssueData = new ArrayList<>();
            for (IssueData issueData : issueDataList) {
                Boolean isExpired = isIssuedDataExpired(issueData.calculateExpirationDate());
                if(isExpired) {
                    issueData.setIssueStatus(IssueStatus.EXPIRED);
                    this.updateIssueData(issueData);
                    expiredIssueData.add(issueData);
                }
            }
            log.info("Total {} issue data expired for member id {}.", expiredIssueData.size(), memberId);
            return expiredIssueData;
        } catch (Exception e) {
            log.error("Exception while checking and updating status of issue data of member id {}: {}", memberId, e.getLocalizedMessage());
            throw e;
        }
    }

    public List<IssueData> getAllIssueData() {
        try {
            log.info("Getting all issue data");
            List<IssueData> issueDataList = this.issueDataRepository.findAll();
            log.info("Successfully get all issue data with count {}", issueDataList.size());
            return issueDataList;
        } catch (Exception e) {
            log.error("Exception while getting all issue data: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    public IssueData getIssueDataById(UUID id) {
        try {
            log.info("Getting issue data with id {}", id);
            Optional<IssueData> optionalIssueData = this.issueDataRepository.findById(id);
            IssueData issueData = optionalIssueData.orElseGet(null);
            log.info("Successfully fetched IssueData with id {}", issueData.getId());
            return issueData;
        } catch (Exception e) {
            log.error("Exception while fetching IssueData wit id {}: {}", id, e.getLocalizedMessage());
            throw e;
        }
    }

    public List<IssueData> getIssueDataByMemberId(UUID memberId) {
        try {
            log.info("Getting issue data list of member id {}", memberId);
            List<IssueData> issueDataList = this.issueDataRepository.findByMemberId(memberId);
            log.info("Successfully fetched total {} IssueData with member id {}", issueDataList.size(), memberId);
            return issueDataList;
        } catch (Exception e) {
            log.error("Exception while fetching IssueData list with member id {}: {}", memberId, e.getLocalizedMessage());
            throw e;
        }
    }

    private Boolean isIssuedDataExpired(Instant expirationDate) {
        if(expirationDate == null) {
            throw new IllegalArgumentException("expirationDate cannot be null.");
        }

        if(Instant.now().isAfter(expirationDate)) {
            return true;
        }

        return false;
    }
}
