package com.wondersgroup.testsdk.bubblechart.modle;

/**
 * Created by liyaqing on 2017/5/15.
 */

public class HashOval {
    private int chartColor;
    private int chartW;
    private int chartH;
    private String text;
    private int textColor;
    private int textSize;

    public HashOval(int chartColor, int chartW, int chartH, String text, int textColor, int textSize) {
        this.chartColor = chartColor;
        this.chartW = chartW;
        this.chartH = chartH;
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

    public int getChartW() {
        return chartW;
    }

    public void setChartW(int chartW) {
        this.chartW = chartW;
    }

    public int getChartH() {
        return chartH;
    }

    public void setChartH(int chartH) {
        this.chartH = chartH;
    }
}
