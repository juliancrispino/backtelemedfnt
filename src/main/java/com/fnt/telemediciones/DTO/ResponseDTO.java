package com.fnt.telemediciones.DTO;

import lombok.Data;

@Data
public class ResponseDTO {
    private boolean success;
    private String id;
    private String errorMsg;
    private Integer errorType;
    private Integer errorCode;
    private Object data;
}
