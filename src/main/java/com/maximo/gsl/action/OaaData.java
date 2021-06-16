package com.maximo.gsl.action;

import com.maximo.gsl.bean.OaA;
import com.maximo.gsl.jdbc.Db2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/12 23:56
 * @description : com.maximo.gsl.action
 */
public class OaaData {

    public List<OaA> getOrA(Connection conn) throws SQLException {
        List<OaA> oaAS = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql());
            rs = ps.executeQuery();
            while (rs.next()) {
                OaA oaA = new OaA();
                oaA.setObjectName(rs.getString(1));
                oaA.setObjectNameId(rs.getString(2));
                oaA.setL_objectName(rs.getString(3));
                oaA.setL_objectNameId(rs.getString(4));
                oaA.setL_objectNameSeq(rs.getString(5));
                oaA.setAttributeName(rs.getString(6));
                oaAS.add(oaA);
            }
            conn.commit();

        } catch (SQLException e) {
            throw new SQLException("获取语言表出错\n"+e.getMessage());
        } finally {
            new Db2().closeRsPs(rs, ps);
        }
        return oaAS;
    }

    private String sql() {
        return "select a.TABLENAME                   数据表,\n" +
                "       c.SAMEASATTRIBUTE             数据表id,\n" +
                "       a.LANGTABLENAME               语言表,\n" +
                "       d.ATTRIBUTENAME               语言表id,\n" +
                "       e.SEQUENCENAME                语言表seq,\n" +
                "       listagg(b.ATTRIBUTENAME, ',') 语言字段\n" +
                "from MAXTABLE a,\n" +
                "     MAXATTRIBUTE b,\n" +
                "     MAXATTRIBUTE c,\n" +
                "     MAXATTRIBUTE d,\n" +
                "     MAXSEQUENCE e\n" +
                "where a.TABLENAME = b.OBJECTNAME\n" +
                "  and a.LANGTABLENAME is not null\n" +
                "  and b.MLINUSE = 1\n" +
                "  and b.PERSISTENT = 1\n" +
                "  and a.LANGTABLENAME = c.OBJECTNAME\n" +
                "  and c.ATTRIBUTENAME = 'OWNERID'\n" +
                "  and a.LANGTABLENAME = d.OBJECTNAME\n" +
                "  and a.LANGTABLENAME = e.TBNAME\n" +
                "  and e.NAME = d.ATTRIBUTENAME\n" +
                "group by a.TABLENAME, a.LANGTABLENAME, c.SAMEASATTRIBUTE, d.ATTRIBUTENAME, e.SEQUENCENAME\n" +
                "order by a.TABLENAME";
    }
}
