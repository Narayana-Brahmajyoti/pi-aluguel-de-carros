package com.DH.PI.dtoOut;

import com.DH.PI.eNums.StatusEmail;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailDTO {

    @NotBlank
    private String   ownerRef ;
    @NotBlank
    @Email
    private String   emailFrom ;
    @NotBlank
    @Email
    private String   emailTo ;
    @NotBlank
    private String   subject ;
    @NotBlank
    private String  text ;


}
