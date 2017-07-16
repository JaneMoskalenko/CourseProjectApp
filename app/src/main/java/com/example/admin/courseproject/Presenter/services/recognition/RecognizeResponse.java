package com.example.admin.courseproject.Presenter.services.recognition;

public class RecognizeResponse {
    private String description;
    private Double confidence;

    public RecognizeResponse(String description, Double confidence) {
        this.description = description;
        this.confidence = confidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "On this image: " + description + " \n confidence: " + confidence;
    }
}
