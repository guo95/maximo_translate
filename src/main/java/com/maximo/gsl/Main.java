package com.maximo.gsl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField ip,port,userName,passWord,filePath,allInfo;
    private JRadioButton commit,sql;
    private JCheckBox cb1,cb2,cb3;
    private JComboBox<String > tran_if;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    Main frame=new Main();     // 创建一个窗口
                    frame.setVisible(true);                                    // 让该窗口实例可见
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//      CourseSelectionFrame frame=new CourseSelectionFrame();
//      frame.setVisible(true);
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
        userName.setColumns(10);
        JLabel label_passWord=new JLabel("passWord");
        panel_2.add(label_passWord);
        passWord=new JPasswordField();
        passWord.setColumns(10);
        panel_2.add(passWord);

        // 第三行
        JPanel panel_3=new JPanel();
        panel.add(panel_3);
        tran_if =new JComboBox<String >();
        tran_if.setModel(new DefaultComboBoxModel<String>(new String[]{"谷歌","百度","有道","微软"}));
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

        // 添加选课信息
        JPanel panelSouth=new JPanel();
        contentPane.add(panelSouth,BorderLayout.SOUTH);
        JLabel labe=new JLabel("选课信息");
        labe.setHorizontalAlignment(SwingConstants.LEFT);
        panelSouth.add(labe);
        allInfo=new JTextField();
        allInfo.setColumns(30);
        panelSouth.add(allInfo);
        //添加标题
        JPanel panelNorth=new JPanel();
        contentPane.add(panelNorth,BorderLayout.NORTH);
        JLabel labelTitle=new JLabel("一丰项目英文翻译为中文日文");
        labelTitle.setForeground(Color.DARK_GRAY);
        labelTitle.setFont(new Font("宋体", Font.BOLD, 20));
        panelNorth.add(labelTitle);

        // 给确定按钮添加事件处理代码
        jbOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder info=new StringBuilder();
                String ipText = ip.getText();
                String portText = port.getText();
                String un = userName.getText();
                String pw = passWord.getText();
                String theWay;
                if(commit.isSelected()){
                    theWay="直接提交";
                }else {
                    theWay="导出SQL";
                }
                info.append(ipText).append(portText).append(un).append(pw).append(theWay);
                allInfo.setText(info.toString());
            }
        });

        // 给重填按钮设置事件处理代码
        jbRest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ip.setText("localhost");
                port.setText("50000");
                sql.setSelected(true);
                filePath.setText("c:\\maximo_translate");
                tran_if.setSelectedIndex(1);
                allInfo.setText("");
            }
        });
    }
}
