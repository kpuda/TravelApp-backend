package com.travelapp.backend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WrapperListObject {

    private int status;
    private List<?> list;
}
