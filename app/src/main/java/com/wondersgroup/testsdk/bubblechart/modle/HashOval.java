package com.wondersgroup.testsdk.bubblechart.modle;

/**
 * Created by liyaqing on 2017/5/15.
 */

public class HashOval {
    private int chartColor;

    private String text;
    private int textColor;
    private int textSize;

    public HashOval(int chartColor,String text, int textColor, int textSize) {
        this.chartColor = chartColor;
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
    }

    public HashOval() {
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

}
