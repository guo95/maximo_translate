package com.maximo.gsl.bean;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/12 23:57
 * @description : com.maximo.gsl.bean
 */
public class OaA {

    private String objectName;
    private String objectNameId;
    private String l_objectName;
    private String l_objectNameId;
    private String l_objectNameSeq;
    private String attributeName;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getL_objectName() {
        return l_objectName;
    }

    public void setL_objectName(String l_objectName) {
        this.l_objectName = l_objectName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getObjectNameId() {
        return objectNameId;
    }

    public void setObjectNameId(String objectNameId) {
        this.objectNameId = objectNameId;
    }

    public String getL_objectNameId() {
        return l_objectNameId;
    }

    public void setL_objectNameId(String l_objectNameId) {
        this.l_objectNameId = l_objectNameId;
    }

    public String getL_objectNameSeq() {
        return l_objectNameSeq;
    }

    public void setL_objectNameSeq(String l_objectNameSeq) {
        this.l_objectNameSeq = l_objectNameSeq;
    }

    @Override
    public String toString() {
        return "OaA{" +
                "objectName='" + objectName + '\'' +
                ", objectNameId='" + objectNameId + '\'' +
                ", l_objectName='" + l_objectName + '\'' +
                ", l_objectNameId='" + l_objectNameId + '\'' +
                ", l_objectNameSeq='" + l_objectNameSeq + '\'' +
                ", attributeName='" + attributeName + '\'' +
                '}';
    }
}
