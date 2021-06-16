package com.maximo.gsl.action;

import com.maximo.gsl.bean.AttrAndTranslate;
import com.maximo.gsl.bean.NeedToTranslate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/14 18:15
 * @description : com.maximo.gsl.action
 */
public class GenerateInsetSql {

    public void generate(List<NeedToTranslate> needToTranslates, Connection conn, boolean isCommit, String filePath) throws SQLException, IOException {
        FileAction fileAction = new FileAction(filePath, "out.sql");
        fileAction.clearInfoForFile();
        fileAction.writeInFile("-------------------- INSERT INTO LANGUAGE TABLES --------------------");
        try (Statement st = conn.createStatement()) {
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
                    if (isCommit) {
                        st.addBatch(sql);
                    } else {
                        fileAction.writeInFile(sql);
                    }
                }
            }
            if (isCommit) {
                st.executeBatch();
                conn.commit();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            fileAction.closeFileAction();
            conn.rollback();
            conn.close();
        }
    }

    private String sql(NeedToTranslate needToTranslate) {
        return String.format("insert into %s (%s, OWNERID, %sLANGCODE, ROWSTAMP) VALUES (%s.nextval, %s, %s'%s', MAXROWSTAMPSEQ.nextval);",
                needToTranslate.getL_objectName(), needToTranslate.getL_objectNameId(), needToTranslate.attrAndTran()[0],
                needToTranslate.getL_objectNameSeq(), needToTranslate.getOwnerId(), needToTranslate.attrAndTran()[1],
                needToTranslate.getLangCode());
    }
}
