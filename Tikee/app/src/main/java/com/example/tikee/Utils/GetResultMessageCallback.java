package com.example.tikee.Utils;

import java.util.List;

public interface GetResultMessageCallback<T> {
    void setData(List<T> item);
}

