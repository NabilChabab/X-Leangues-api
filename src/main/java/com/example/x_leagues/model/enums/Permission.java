package com.example.x_leagues.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    JURY_READ("jury:read"),
    JURY_UPDATE("jury:update"),
    JURY_CREATE("jury:create"),
    MEMBER_READ("member:read");

    @Getter
    private final String permission;
}
