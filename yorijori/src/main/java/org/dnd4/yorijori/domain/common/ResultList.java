package org.dnd4.yorijori.domain.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResultList<T> extends BaseResult<T>{
    private List<T> list;

    public ResultList(List<T> data){
        this.setList(data);
    }
}
