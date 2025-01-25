package com.makeprojects.library.elib.core.service.definition;

import com.makeprojects.ewallet.shared.core.definition.CRUDService;
import com.makeprojects.library.elib.entity.Member;

public interface MemberService extends CRUDService<Member> {

    Member getMemberByEmail(String email);
}
