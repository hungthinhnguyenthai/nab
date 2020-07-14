package com.thinhnguyen.nab.service.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseRequestDto implements Serializable {

    @NotNull
    @NotBlank(message="Please enter your phone number")
    String phone;

    @NotNull
    @NotBlank(message="Please select your package")
    String dataType;
}
