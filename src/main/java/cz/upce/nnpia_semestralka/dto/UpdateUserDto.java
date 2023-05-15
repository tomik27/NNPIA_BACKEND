package cz.upce.nnpia_semestralka.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
    public class UpdateUserDto {

        private String username;

        private String password;

        private String email;

        private String role;
    }

