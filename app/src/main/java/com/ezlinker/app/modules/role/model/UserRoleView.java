package com.ezlinker.app.modules.role.model;

import lombok.Data;

@Data
public class UserRoleView {

    private Long id;

    private Long userId;

    private String label;

    private String name;

    private Integer parent;

    private String description;

}
