package com.maximo.gsl.action;

import com.maximo.gsl.bean.AttrAndTranslate;
import com.maximo.gsl.bean.NeedToTranslate;

import java.util.List;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/14 18:15
 * @description : com.maximo.gsl.action
 */
public class GenerateInsetSql {

    public static void main(String[] args) {
        try {
            new GenerateInsetSql().generate(new TranslateData().getTranslateData(new OaaData().getOrA()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generate(List<NeedToTranslate> needToTranslates) {
        FileAction fileAction = new FileAction("out.sql");
        fileAction.clearInfoForFile();
        fileAction.writeInFile("-------------------- INSERT INTO LANGUAGE TABLES --------------------");
        for (NeedToTranslate needToTranslate : needToTranslates) {
            boolean mark = false;
            List<AttrAndTranslate> attrAndTranslates = needToTranslate.getAttrAndTranslates();
            for (AttrAndTranslate attrAndTranslate : attrAndTranslates) {
                if (!attrAndTranslate.getTranslateData().equals(attrAndTranslate.getNeedTranslateData())) {
                    mark = true;
                    break;
                }
            }
            if (mark) {
                String sql = sql(needToTranslate);
                System.out.println(sql);
                fileAction.writeInFile(sql);
            }
        }
        fileAction.closeFileAction();
    }

    private String sql(NeedToTranslate needToTranslate) {
        return String.format("insert into %s (%s, OWNERID, %sLANGCODE) VALUES (%s.nextval, %s, %s'%s');",
                needToTranslate.getL_objectName(), needToTranslate.getL_objectNameId(), needToTranslate.attrAndTran()[0],
                needToTranslate.getL_objectNameSeq(), needToTranslate.getOwnerId(), needToTranslate.attrAndTran()[1],
                needToTranslate.getLangCode());
    }
}
