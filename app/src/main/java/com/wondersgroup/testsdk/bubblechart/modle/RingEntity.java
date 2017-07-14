package com.wondersgroup.testsdk.bubblechart.modle;

/**
 * Created by liyaqing on 2017/4/1.
 */

public class RingEntity {
    private int ringColor;
    private Float ringData;
    private String ringText;
    private int ringTextColor;

    public RingEntity(int ringColor, Float ringData, String ringText, int ringTextColor) {
        this.ringColor = ringColor;
        this.ringData = ringData;
        this.ringText = ringText;
        this.ringTextColor = ringTextColor;
    }

    public int getRingColor() {
        return ringColor;
    }

    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    public Float getRingData() {
        return ringData;
    }

    public void setRingData(Float ringData) {
        this.ringData = ringData;
    }

    public String getRingText() {
        return ringText;
    }

    public void setRingText(String ringText) {
        this.ringText = ringText;
    }

    public int getRingTextColor() {
        return ringTextColor;
    }

    public void setRingTextColor(int ringTextColor) {
        this.ringTextColor = ringTextColor;
    }
}
