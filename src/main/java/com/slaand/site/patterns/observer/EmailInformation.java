package com.slaand.site.patterns.observer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailInformation {

    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String message;
    @NotNull
    private MessageType type;
    private String name;

}
