package org.dnd4.yorijori.domain.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Result<T> extends BaseResult<T>{
    private T data;

    public Result(T data){
        this.setData(data);
    }
}
