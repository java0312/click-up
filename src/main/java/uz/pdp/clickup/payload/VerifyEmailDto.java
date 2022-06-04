package uz.pdp.clickup.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class VerifyEmailDto {
    @Email
    private String email;

    @NotBlank
    private String code;
}
