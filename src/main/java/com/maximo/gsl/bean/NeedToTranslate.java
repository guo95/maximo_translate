package com.maximo.gsl.bean;

import java.util.List;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/13 20:54
 * @description : com.maximo.gsl.bean
 */
public class NeedToTranslate {

    private String l_objectName;
    private String l_objectNameId;
    private String L_objectNameSeq;
    private String ownerId;
    private String langCode;
    private List<AttrAndTranslate> attrAndTranslates;

    public String getL_objectName() {
        return l_objectName;
    }

    public void setL_objectName(String l_objectName) {
        this.l_objectName = l_objectName;
    }

    public String getL_objectNameId() {
        return l_objectNameId;
    }

    public void setL_objectNameId(String l_objectNameId) {
        this.l_objectNameId = l_objectNameId;
    }

    public String getL_objectNameSeq() {
        return L_objectNameSeq;
    }

    public void setL_objectNameSeq(String l_objectNameSeq) {
        L_objectNameSeq = l_objectNameSeq;
    }

    public List<AttrAndTranslate> getAttrAndTranslates() {
        return attrAndTranslates;
    }

    public void setAttrAndTranslates(List<AttrAndTranslate> attrAndTranslates) {
        this.attrAndTranslates = attrAndTranslates;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String[] attrAndTran() {
        StringBuilder attr = new StringBuilder();
        StringBuilder tran = new StringBuilder();
        for (AttrAndTranslate attrAndTranslate : this.attrAndTranslates) {
            attr.append(attrAndTranslate.getAttributeName()).append(", ");
            tran.append("'").append(attrAndTranslate.getTranslateData().replaceAll("'", "''")).append("'").append(", ");
        }
        return new String[]{attr.toString(), tran.toString()};
    }

    @Override
    public String toString() {
        return "NeedToTranslate{" +
                "l_objectName='" + l_objectName + '\'' +
                ", l_objectNameId='" + l_objectNameId + '\'' +
                ", L_objectNameSeq='" + L_objectNameSeq + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", langCode='" + langCode + '\'' +
                ", attrAndTranslates=" + attrAndTranslates +
                '}';
    }
}
