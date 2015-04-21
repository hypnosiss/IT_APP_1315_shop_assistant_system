package pl.pwr.shopassistant.apiutils;

import lombok.Data;

@Data
public class ResponseDTO {
    private Integer resultCode;
    private String errorMessage;

    private Object data;
}
