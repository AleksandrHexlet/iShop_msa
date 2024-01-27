package com.sprng.notification.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class ExceptionMessageFullFail {

    @Id
    int id;
    private String exceptionReason;
    private LocalDateTime exceptionTime;

    private StaffStock exceptionBody;

    public String getExceptionReason() {
        return exceptionReason;
    }

    public ExceptionMessageFullFail(String exceptionReason, LocalDateTime exceptionTime, StaffStock exceptionBody) {
        this.exceptionReason = exceptionReason;
        this.exceptionTime = exceptionTime;
        this.exceptionBody = exceptionBody;
        this.id = (int)(Math.random()*100000);
    }

    public LocalDateTime getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(LocalDateTime exceptionTime) {
        this.exceptionTime = exceptionTime;
    }

    public StaffStock getExceptionBody() {
        return exceptionBody;
    }

    public void setExceptionBody(StaffStock exceptionBody) {
        this.exceptionBody = exceptionBody;
    }

    public ExceptionMessageFullFail() {
    }
}
