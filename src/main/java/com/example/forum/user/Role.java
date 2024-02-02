package com.example.forum.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Role {
    private String id;
    private String name;
    private String description;
    private String color;
    private Object extendedData;
    private String instanceId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date updatedAt;

}
