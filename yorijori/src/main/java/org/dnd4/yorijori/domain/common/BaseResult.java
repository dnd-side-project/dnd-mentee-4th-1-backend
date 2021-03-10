package org.dnd4.yorijori.domain.common;

import lombok.Data;

import java.util.Date;

@Data
public class BaseResult<T> {
    private Date timestamp;
    private String status;
    private String error;
    private String message;
    private String path;

    public BaseResult(){
        this.setTimestamp(new Date());
        this.setStatus("200");
        this.setError("");
        this.setMessage("");
        this.setPath("/");
    }
}
