package com.example.pfa.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(Long id, String firstName, String lastName, String username, String email, List<String> roles) {
}
