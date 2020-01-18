package com.alvis.exam.domain;

public class Test {
    private String testId;

    private String testTitle;

    private Integer testStatus;

    private Integer testType;

    private String testContext;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId == null ? null : testId.trim();
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle == null ? null : testTitle.trim();
    }

    public Integer getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Integer testStatus) {
        this.testStatus = testStatus;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public String getTestContext() {
        return testContext;
    }

    public void setTestContext(String testContext) {
        this.testContext = testContext == null ? null : testContext.trim();
    }
}