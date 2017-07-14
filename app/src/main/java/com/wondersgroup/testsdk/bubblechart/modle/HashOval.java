package com.wondersgroup.testsdk.bubblechart.modle;

/**
 * Created by liyaqing on 2017/5/15.
 */

public class HashOval {
    private int chartColor;

    private String text;
    private int textColor;
    private int textSize;
    private int isAlpha;//是否隐藏,0是，1否


    public HashOval() {
    }

    public HashOval(int chartColor, String text, int textColor, int textSize, int isAlpha) {
        this.chartColor = chartColor;
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
        this.isAlpha = isAlpha;
    }

    public int getChartColor() {
        return chartColor;
    }

    public void setChartColor(int chartColor) {
        this.chartColor = chartColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIsAlpha() {
        return isAlpha;
    }

    public void setIsAlpha(int isAlpha) {
        this.isAlpha = isAlpha;
    }
}
