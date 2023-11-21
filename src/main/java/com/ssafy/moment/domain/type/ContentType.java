package com.ssafy.moment.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {

    C12(12),
    C14(14),
    C15(15),
    C25(25),
    C28(28),
    C32(32),
    C38(38),
    C39(39),
    ;

    private final int contentTypeId;

}
