package com.marscolony.reviews.cmd.api.dto;

import com.marscolony.reviews.core.api.dto.BaseResponse;

public class CreateReviewResponse extends BaseResponse {
    private String id;

    public CreateReviewResponse(String id, String message) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
