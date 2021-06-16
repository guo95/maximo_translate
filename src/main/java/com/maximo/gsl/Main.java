package com.maximo.gsl;

import com.maximo.gsl.action.GenerateInsetSql;
import com.maximo.gsl.action.OaaData;
import com.maximo.gsl.action.TranslateData;
import com.maximo.gsl.jdbc.Db2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : guosl
 * @version : 1.0
 * @date : 2021/6/15 15:06
 * @description : com.maximo.gsl
 */
public class Main extends JFrame{

    // 定义组件
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField ip,port,userName,passWord,filePath;
    private JRadioButton commit,sql;
    private JCheckBox cb1,cb2,cb3;
    private JComboBox<String > tran_if;
    private JTextArea outMsg;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try{
                // 创建一个窗口
                Main frame=new Main();
                // 让该窗口实例可见
                frame.setVisible(true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 窗口属性的设置，内部组件的初始化
     */
    public Main(){
        // 标题
        setTitle("Maximo翻译语言表");
        // 设置关闭时推出JVM
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小
        setSize(500,400);
        // 设置窗体居中
        setLocationRelativeTo(null);
        // 内容面板
        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        // 设置布局
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        // 5行1列的表格布局
        JPanel panel=new JPanel(new GridLayout(5, 1,5,10));
        panel.setBorder(new TitledBorder(null,"",TitledBorder.LEADING ,TitledBorder.TOP,null,null));
        // 给panel添加边框
        contentPane.add(panel,BorderLayout.CENTER);
        // 第一行
        JPanel panel_1=new JPanel();
        panel.add(panel_1);
        JLabel label_ip=new JLabel("DB2:ip");
        panel_1.add(label_ip);
        ip=new JTextField();
        panel_1.add(ip);
        ip.setColumns(10);
        ip.setText("localhost");
        JLabel label_port=new JLabel("DB2:port");
        panel_1.add(label_port);
        port=new JTextField();
        port.setColumns(10);
        port.setText("50000");
        panel_1.add(port);

        // 第二行
        JPanel panel_2=new JPanel();
        panel.add(panel_2);
        JLabel label_userName=new JLabel("userName");
        panel_2.add(label_userName);
        userName=new JTextField();
        panel_2.add(userName);
        userName.setText("maximo");
        userName.setColumns(10);
        JLabel label_passWord=new JLabel("passWord");
        panel_2.add(label_passWord);
        passWord=new JPasswordField();
        passWord.setColumns(10);
        panel_2.add(passWord);

        // 第三行
        JPanel panel_3=new JPanel();
        panel.add(panel_3);
        tran_if =new JComboBox< >();
        tran_if.setModel(new DefaultComboBoxModel<>(new String[]{"谷歌","百度","有道","微软"}));
        tran_if.setToolTipText("接口");
        tran_if.setSelectedIndex(1);
        panel_3.add(tran_if);
        // 二选一
        sql=new JRadioButton("导出SQL");
        panel_3.add(sql);
        sql.setSelected(true);
        // 设置单选按钮中，默认选中的按钮
        commit=new JRadioButton("直接修改");
        panel_3.add(commit);
        // 单选按钮需要加入到同一个ButonGroup中才能生效
        ButtonGroup bts=new ButtonGroup();
        bts.add(sql);
        bts.add(commit);

        //第四行
        JPanel panel_4=new JPanel();
        panel.add(panel_4);

        JLabel label_3=new JLabel("文件目录：");
        panel_4.add(label_3);
        filePath=new JTextField();
        panel_4.add(filePath);
        filePath.setText("c:\\maximo_translate");
        filePath.setColumns(30);

        // 第五行
        JPanel panel_5=new JPanel();
        panel.add(panel_5);
        JButton jbOk=new JButton("确定");
        panel_5.add(jbOk);
        JButton jbRest=new JButton("重填");
        panel_5.add(jbRest);

        JButton dbTest=new JButton("测试数据库连接");
        panel_5.add(dbTest);

        // 添加输出信息
        JPanel panelSouth=new JPanel();
        contentPane.add(panelSouth,BorderLayout.SOUTH);
        outMsg = new JTextArea();
        outMsg.setColumns(42);
        outMsg.setRows(3);
        outMsg.setLineWrap(true);
        panelSouth.add(outMsg);

        //添加标题
        JPanel panelNorth=new JPanel();
        contentPane.add(panelNorth,BorderLayout.NORTH);
        JLabel labelTitle=new JLabel("一丰项目英文翻译为中文日文");
        labelTitle.setForeground(Color.DARK_GRAY);
        labelTitle.setFont(new Font("宋体", Font.BOLD, 20));
        panelNorth.add(labelTitle);

        // 给确定按钮添加事件处理代码
        jbOk.addActionListener(e -> {
            long start = System.currentTimeMillis();
            String msg;
            String ipText = ip.getText();
            String portText = port.getText();
            String un = userName.getText();
            String pw = passWord.getText();
            String fp = filePath.getText();
            // ture 时为直接提交
            boolean isCommit = commit.isSelected();
            Db2 db2 = new Db2(ipText, portText, un, pw);
            Connection conn = null;
            try {
                conn = db2.getConnection();
                new GenerateInsetSql().generate(new TranslateData(fp).getTranslateData(new OaaData().getOrA(conn), conn), conn, isCommit, fp);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                long end = System.currentTimeMillis();
                msg = "翻译完成，共耗时".concat(String.valueOf((end-start)/1000)).concat("秒");
                if (null != conn) {
                    try {
                        conn.close();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
                outMsg.setText(msg);
            }
        });

        // 给重填按钮设置事件处理代码
        jbRest.addActionListener(e -> {
            ip.setText("localhost");
            port.setText("50000");
            sql.setSelected(true);
            filePath.setText("d:\\maximo_translate");
            tran_if.setSelectedIndex(1);
            outMsg.setText("");
        });

        // 数据库测试
        dbTest.addActionListener(e -> {
            String ipText = ip.getText();
            String portText = port.getText();
            String un = userName.getText();
            String pw = passWord.getText();
            Db2 db2 = new Db2(ipText, portText, un, pw);
            String msg;
            try {
                Connection conn = db2.getConnection();
                msg = "数据库连接成功";
                conn.rollback();
                conn.close();
            } catch (SQLException exception) {
                msg = exception.getMessage();
            }
            outMsg.setText(msg);
        });

    }
}
