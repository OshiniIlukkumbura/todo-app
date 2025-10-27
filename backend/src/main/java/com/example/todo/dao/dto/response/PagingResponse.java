package com.example.todo.dao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponse {
    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("total_records")
    private Long totalRecords;
}
