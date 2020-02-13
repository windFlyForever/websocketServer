package com.reman.medical.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Message {

    private String fromUser;

    private String toUser;

    private String content;


}
