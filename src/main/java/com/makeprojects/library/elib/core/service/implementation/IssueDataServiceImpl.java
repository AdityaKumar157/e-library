package com.makeprojects.library.elib.core.service.implementation;

import com.makeprojects.library.elib.core.service.definition.BookService;
import com.makeprojects.library.elib.core.service.definition.IssueDataService;
import com.makeprojects.library.elib.core.service.definition.MemberService;
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
public class IssueDataServiceImpl implements IssueDataService {

    private final IssueDataRepository issueDataRepository;
    private final MemberService memberService;
    private final BookService bookService;

    @Autowired
    public IssueDataServiceImpl(IssueDataRepository issueDataRepository, MemberService memberService, BookService bookService) {
        this.issueDataRepository = issueDataRepository;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    @Override
    public IssueData get(UUID id) {
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

    @Override
    public List<IssueData> getAll() {
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

    @Override
    public IssueData create(IssueData entity) {
        try {
            log.info("Adding issue data");
            entity.calculateAmountPaid();
            entity.calculateExpirationDate();
            IssueData savedIssueData = this.issueDataRepository.save(entity);
            log.info("Successfully added issue data with id {}", entity.getId());
            return savedIssueData;
        } catch (Exception e) {
            log.error("Exception while adding issue data: {}", e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public IssueData update(IssueData entity) {
        try {
            log.info("Updating issue data with id {}", entity.getId());
            IssueData updatedIssuedData = this.issueDataRepository.save(entity);
            log.info("Successfully updated issue data with id {}", updatedIssuedData.getId());
            return updatedIssuedData;
        } catch (Exception e) {
            log.error("Exception while updating issue data with id {}: {}", entity.getId(), e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public IssueData addIssueData(IssueDataDto issueDataDto) {
        Member member = this.memberService.get(issueDataDto.getMemberId());
        Book book = this.bookService.get((issueDataDto.getBookId()));

        if(member==null || book==null) {
            throw new RuntimeException();
        }

        IssueData issueData = IssueData.builder()
                .member(member)
                .book(book)
                .build();

        return this.create(issueData);
    }

    @Override
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

    @Override
    public List<IssueData> markExpStatusOfIssueDataByMemberId(UUID memberId) {
        try {
            log.info("Checking and updating issue data of member id {}", memberId);
            List<IssueData> issueDataList = this.getIssueDataByMemberId(memberId);
            List<IssueData> expiredIssueData = new ArrayList<>();
            for (IssueData issueData : issueDataList) {
                Boolean isExpired = isIssuedDataExpired(issueData.calculateExpirationDate());
                if(isExpired) {
                    issueData.setIssueStatus(IssueStatus.EXPIRED);
                    this.update(issueData);
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
