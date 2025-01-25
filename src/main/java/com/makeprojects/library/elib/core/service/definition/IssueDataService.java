package com.makeprojects.library.elib.core.service.definition;

import com.makeprojects.ewallet.shared.core.definition.CRUDService;
import com.makeprojects.library.elib.dto.IssueDataDto;
import com.makeprojects.library.elib.entity.IssueData;

public interface IssueDataService extends CRUDService<IssueData>, IssueDataMemberService<IssueData, IssueDataDto> {
}
