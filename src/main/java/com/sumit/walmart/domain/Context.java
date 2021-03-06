package com.sumit.walmart.domain;

import com.sumit.walmart.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Context {
    @Builder.Default
    private List<String> filteredCriteria = new ArrayList<>();
    private Subject subject;
}
