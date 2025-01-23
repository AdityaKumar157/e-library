package com.makeprojects.library.elib.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IssueDataDto {

    private UUID memberId;
    private UUID bookId;
}
