package com.maximo.gsl.bean;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/13 20:57
 * @description : com.maximo.gsl.bean
 */
public class AttrAndTranslate {

    private String attributeName;
    private String needTranslateData;
    private String translateData;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getTranslateData() {
        return translateData;
    }

    public void setTranslateData(String translateData) {
        this.translateData = translateData;
    }

    public String getNeedTranslateData() {
        return needTranslateData;
    }

    public void setNeedTranslateData(String needTranslateData) {
        this.needTranslateData = needTranslateData;
    }

    @Override
    public String toString() {
        return "AttrAndTranslate{" +
                "attributeName='" + attributeName + '\'' +
                ", needTranslateData='" + needTranslateData + '\'' +
                ", translateData='" + translateData + '\'' +
                '}';
    }
}
