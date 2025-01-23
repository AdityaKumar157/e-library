package com.makeprojects.library.elib.entity;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.makeprojects.library.elib.enums.IssueStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
@Builder
public class IssueData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @NotNull
    //@JsonIgnore     // this will not show as JSON object of member in response body
    @JsonIncludeProperties({"id", "firstName", "lastName"})     // this will show object of member with these three properties only
    private Member member;

    @ManyToOne
    @NotNull
    //@JsonIgnore     // this will not show as JSON object of book in response body
    @JsonIncludeProperties({"id", "name", "author"})     // this will show object of book with these three properties only
    private Book book;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant expirationDate;

    @NotNull
    private double amountPaid;

    @NotNull
    @Builder.Default
    private IssueStatus issueStatus = IssueStatus.ISSUED;

//    @JsonProperty(value = "memberId")       // this will be shown in response body with "memberId" as key
//    public UUID getMemberId() {
//        return this.member.getId();
//    }
//
//    @JsonProperty(value = "bookId")       // this will be shown in response body with "bookId" as key
//    public UUID getBookId() {
//        return this.book.getId();
//    }

    public Instant calculateExpirationDate() {
        this.expirationDate = this.createdAt.plus(15, ChronoUnit.DAYS);
        return this.expirationDate;
    }

    public double calculateAmountPaid() {
        this.amountPaid = this.book.getPrice() * 0.05D;
        return this.amountPaid;
    }
}
