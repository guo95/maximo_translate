package com.maximo.gsl.action;

import com.maximo.gsl.bean.AttrAndTranslate;
import com.maximo.gsl.bean.NeedToTranslate;
import com.maximo.gsl.bean.OaA;
import com.maximo.gsl.jdbc.Db2;
import com.maximo.gsl.translate.BaiduTranslate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/13 0:56
 * @description : com.maximo.gsl.action
 */
public class TranslateData {

    private static Map<String, String> MAP;
    private final FileAction fileAction;

    public TranslateData() {
        this.fileAction = new FileAction("map.txt");
        MAP = fileAction.readToMap();
    }
    public List<NeedToTranslate> getTranslateData(List<OaA> oaAS) throws Exception {
        Db2 db2 = new Db2("m.shuto.cn", "45000", "maximo", "Guosl@shuto.cn");
        Connection conn = db2.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<NeedToTranslate> needToTranslates = new ArrayList<>();
        try {
            for (OaA oaA : oaAS) {
                String a = oaA.getAttributeName();
                String[] split = a.split(",");
                int attrCount = split.length;
                StringBuilder attrs = new StringBuilder();
                for (String at : split) {
                    attrs.append("a.".concat(at).concat(","));
                }
                String sql = String.format(sql(), oaA.getObjectNameId(), attrs, oaA.getObjectNameId(), attrs, oaA.getObjectName(),
                        oaA.getObjectNameId(), oaA.getObjectName(), oaA.getL_objectName(), oaA.getObjectNameId(), oaA.getObjectNameId(),
                        oaA.getObjectNameId());
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    boolean mark = false;
                    for (int i = 0; i < attrCount; i++) {
                        String str = rs.getString(i + 2);
                        if (null == str || str.isEmpty() || "null".equals(str) || "~null~".equals(str)) {
                            mark = false;
                        } else {
                            mark = true;
                            break;
                        }
                    }
                    if (mark) {
                        NeedToTranslate needToTranslate = new NeedToTranslate();
                        needToTranslate.setL_objectName(oaA.getL_objectName());
                        needToTranslate.setL_objectNameId(oaA.getL_objectNameId());
                        needToTranslate.setL_objectNameSeq(oaA.getL_objectNameSeq());
                        needToTranslate.setOwnerId(rs.getString(1));
                        needToTranslate.setLangCode(rs.getString(attrCount + 2));
                        List<AttrAndTranslate> attrAndTranslates = new ArrayList<>();
                        for (int i = 0; i < attrCount; i++) {
                            AttrAndTranslate attrAndTranslate = new AttrAndTranslate();
                            attrAndTranslate.setAttributeName(split[i]);
                            String str = rs.getString(i + 2);
                            attrAndTranslate.setNeedTranslateData((null != str && !"null".equals(str) && !"~null~".equals(str)) ? str : "");
                            attrAndTranslate.setTranslateData(translate(attrAndTranslate, needToTranslate.getLangCode()));
                            attrAndTranslates.add(attrAndTranslate);
                        }
                        needToTranslate.setAttrAndTranslates(attrAndTranslates);
                        needToTranslates.add(needToTranslate);
                    }
                }
            }
            fileAction.closeFileAction();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            new Db2().closeAll(rs, ps, conn);
        }
        return needToTranslates;
    }

    private String sql() {
        return "select a.%s, --2\n" +
                "       %s   --3\n" +
                "       a.langcode\n" +
                "from (select a.%s, %s b.langcode --4,5\n" +
                "      from %s a,  --6\n" +
                "           (select 'ZH' langcode\n" +
                "            from SYSIBM.DUAL\n" +
                "            union\n" +
                "            select 'JA' langcode\n" +
                "            from SYSIBM.DUAL) b) a\n" +
                "where not exists(select 1\n" +
                "                 from (select a.%s, b.LANGCODE  --7\n" +
                "                       from %s a,  --8\n" +
                "                            %s b   --9\n" +
                "                       where a.%s = b.OWNERID) b   --10\n" +
                "                 where a.%s = b.%s  --11,12\n" +
                "                   and a.langcode = b.LANGCODE)";
    }

    private String translate(AttrAndTranslate attrAndTranslate, String langCode) throws Exception {
        String str = attrAndTranslate.getNeedTranslateData();
        if (str.isEmpty()) {
            return "";
        }
        String value = MAP.get(str.concat("-").concat(langCode));
        if (null == value) {
//            GoogleTranslate googleTranslate = new GoogleTranslate();
            BaiduTranslate baiduTranslate = new BaiduTranslate();
            String l = langCode.replace("JA", "jp");
            String translateStr = baiduTranslate.translate("auto", l, str);
            MAP.put(str.concat("-").concat(langCode), translateStr);
            fileAction.writeInFile("source:".concat(str).concat("-").concat(langCode).concat(";target:").concat(translateStr));
            return translateStr;
        } else {
            return value;
        }
    }
}
