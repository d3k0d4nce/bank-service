package ru.kishko.bankservice.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum UserSort {

    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    BIRTH_DAY_ASC(Sort.by(Sort.Direction.ASC, "birthDate")),
    BIRTH_DAY_DESC(Sort.by(Sort.Direction.DESC, "birthDate"));

    private final Sort sortValue;

}
