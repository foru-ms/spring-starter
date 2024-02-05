package com.example.forum.thread.dto;

import com.example.forum.thread.Thread;

import java.util.List;
public record GetAllThreadResponseDto(List<Thread> threads, int count) {}
