package com.marscolony.bankacc.cmd.api.dto;

import com.marscolony.bankacc.core.api.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String id, String message) {
        super(message);
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
