package com.makeprojects.library.elib.core.service.definition;

import com.makeprojects.ewallet.shared.core.definition.Service;

import java.util.List;
import java.util.UUID;

public interface IssueDataMemberService<T, R> extends Service {

    T addIssueData(R issueDataDto);

    List<T> getIssueDataByMemberId(UUID memberId);

    List<T> markExpStatusOfIssueDataByMemberId(UUID memberId);
}
