package com.example.forum.thread;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Tag {
    private String id;
    private String name;
    private String description;
    private String color;
    private List<Thread> threads;
}
