import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;


public class Ustawienia extends JFrame{
    private JButton signUpButton;
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel close;
    private JComboBox languageCmb;
    private JButton dodajAdmina;
    private JComboBox desginCmb;
    private JButton adminGiveUp;
    private JComboBox dodajAdminaCmb;
    private JLabel dodajAdminaLabel;
    private JLabel adminGiveUpLabel;
    private JLabel studioNameLabel;
    private JLabel designLabel;
    private JLabel LanguageLabel;
    private JComboBox menuCmb;
    private JLabel menuLabel;
    private JSeparator separator1;
    private JSeparator separator2;
    private JLabel CreateAccount;
    private JLabel SkipLogin;
    private int pX, pY;
    private String filepath = "";
    private boolean _succ = false;
    private Connection connection;
    private Statement statement;

    private boolean ISADMIN = true;


    public Ustawienia(boolean nightLayout, test3 owner, boolean isADMIN, int idUzytkownika)
    {

        add(rootPanel);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setTitle(test.studioName);
        setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);
        //rootPanel.setBackground(Color.WHITE);
        rootPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.RED));

        this.ISADMIN = isADMIN;

        desginCmb.addItem("Light");
        desginCmb.addItem("Dark");


        languageCmb.addItem("English");
        languageCmb.addItem("Polski");

        menuCmb.addItem("tiny");
        menuCmb.addItem("big");

        if(nightLayout)
        {
            rootPanel.setBackground(Color.decode("#434343"));
            dragPanel.setBackground(Color.decode("#434343"));
            studioNameLabel.setForeground(Color.decode("#D9D9D9"));
            designLabel.setForeground(Color.decode("#D9D9D9"));
            LanguageLabel.setForeground(Color.decode("#D9D9D9"));
            menuLabel.setForeground(Color.decode("#D9D9D9"));
            dodajAdminaLabel.setForeground(Color.decode("#D9D9D9"));
            adminGiveUpLabel.setForeground(Color.decode("#D9D9D9"));

            desginCmb.setSelectedIndex(1);
        }
        else
        {
            rootPanel.setBackground(Color.WHITE);
            dragPanel.setBackground(Color.WHITE);
            studioNameLabel.setForeground(Color.decode("#434343"));
            designLabel.setForeground(Color.decode("#434343"));
            LanguageLabel.setForeground(Color.decode("#434343"));
            menuLabel.setForeground(Color.decode("#434343"));
            dodajAdminaLabel.setForeground(Color.decode("#434343"));
            adminGiveUpLabel.setForeground(Color.decode("#434343"));

            desginCmb.setSelectedIndex(0);
        }

        if(Locale.getDefault().toString().equals("pl"))
            languageCmb.setSelectedIndex(1);
        else
            languageCmb.setSelectedIndex(0);

        if(!ISADMIN)
        {
            dodajAdmina.setVisible(false);
            dodajAdminaCmb.setVisible(false);
            dodajAdminaLabel.setVisible(false);
            adminGiveUp.setVisible(false);
            adminGiveUpLabel.setVisible(false);
        }


        //dragPanel.setBackground(Color.WHITE);
        //setSize(400, 500);
        dragPanel.setFocusable(true);

        signUpButton.setFocusPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(true);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        dodajAdmina.setFocusPainted(false);
        dodajAdmina.setContentAreaFilled(false);
        dodajAdmina.setOpaque(true);
        dodajAdmina.setFocusPainted(false);
        dodajAdmina.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        adminGiveUp.setFocusPainted(false);
        adminGiveUp.setContentAreaFilled(false);
        adminGiveUp.setOpaque(true);
        adminGiveUp.setFocusPainted(false);
        adminGiveUp.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));


        textField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        textField1.setText(test.studioName);


        close.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                dispose(); // close window
                setVisible(false);
            }
        });
        dragPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();

            }


        });

        dragPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {

                setLocation(getLocation().x + me.getX() - pX,
                        getLocation().y + me.getY() - pY);
            }
        });




        ComponentResizer cr = new ComponentResizer();


        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);


        cr.registerComponent(this);
        cr.setSnapSize(new Dimension(10, 10));
        cr.setMaximumSize(rootPanel.getMaximumSize());
        cr.setMinimumSize(rootPanel.getMinimumSize());





        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(textField1.getText().equals("Name"))
                    textField1.setText("");

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(textField1.getText().equals("")) {
                    textField1.setText("Name");
                }
            }
        });



        signUpButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (signUpButton.getModel().isPressed()) {
                    signUpButton.setBackground(Color.decode("#7B1207"));
                } /*else if (signUpButton.getModel().isRollover()) {
                    signUpButton.setBackground(Color.RED);
                } */else {
                    signUpButton.setBackground(Color.decode("#BB1407"));
                }
            }
        });

        dodajAdmina.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (dodajAdmina.getModel().isPressed()) {
                    dodajAdmina.setBackground(Color.decode("#7B1207"));
                } /*else if (dodajAdmina.getModel().isRollover()) {
                    dodajAdmina.setBackground(Color.RED);
                } */else {
                    dodajAdmina.setBackground(Color.decode("#BB1407"));
                }
            }
        });

        adminGiveUp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (adminGiveUp.getModel().isPressed()) {
                    adminGiveUp.setBackground(Color.decode("#7B1207"));
                } /*else if (dodajAdmina.getModel().isRollover()) {
                    dodajAdmina.setBackground(Color.RED);
                } */else {
                    adminGiveUp.setBackground(Color.decode("#BB1407"));
                }
            }
        });


        ArrayList<Integer> KlientID = new ArrayList<Integer>();
        ArrayList<Integer> AdminID = new ArrayList<Integer>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from klienci where admin=0");
            while(resultSet.next())
            {
                dodajAdminaCmb.addItem(resultSet.getString("login"));
                KlientID.add(resultSet.getInt("id"));
            }



        } catch (Exception exc) {
            exc.printStackTrace();
        }

        if(dodajAdminaCmb.getItemCount() == 0) {
            dodajAdminaLabel.setVisible(false);
            dodajAdminaCmb.setVisible(false);
            dodajAdmina.setVisible(false);
        }



        dodajAdmina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = dodajAdminaCmb.getSelectedIndex();
                if(selected > -1) {
                    try {

                        PreparedStatement ps = connection.prepareStatement("update klienci set admin=? where id=?");
                        ps.setInt(1, 1);
                        ps.setInt(2, KlientID.get(selected));
                        int res = ps.executeUpdate();
                        if (res > 0) {
                            dodajAdminaCmb.removeItemAt(selected);
                        }
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        adminGiveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    ResultSet resultSet = statement.executeQuery("select * from klienci where admin=1");
                    resultSet.last();
                    if(resultSet.getRow()>1) {

                        PreparedStatement ps = connection.prepareStatement("update klienci set admin=? where id=?");
                        ps.setInt(1, 0);
                        ps.setInt(2, idUzytkownika);
                        int res = ps.executeUpdate();
                        if (res > 0) {
                            dispose();
                            owner.reloadAdminRight(false);
                        }
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }

            }
        });



        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(desginCmb.getSelectedIndex() == 0)//Light
                {
                    owner.setLightDesign();
                }
                else if(desginCmb.getSelectedIndex() == 1)//Dark
                {
                    owner.setDarkDesign();
                }

                if(languageCmb.getSelectedIndex() == 0)//English
                    Locale.setDefault(new Locale("en"));
                else if(languageCmb.getSelectedIndex() == 1)//PL
                    Locale.setDefault(new Locale("pl"));

                if(menuCmb.getSelectedIndex() == 0)//tiny
                {
                    owner.menuLongPanel.setVisible(false);
                    owner.initQuickMenu();
                }
                else if(menuCmb.getSelectedIndex() == 1)//big
                {
                    owner.menuPanel.setVisible(false);
                    owner.initLongMenu();
                }




                if(test.studioName != textField1.getText()) {
                    test.studioName = textField1.getText();
                    try {

                        PreparedStatement ps = connection.prepareStatement("update studio set nazwa=?");
                        ps.setString(1, textField1.getText());
                        int res = ps.executeUpdate();
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
                dispose();
            }
        });

    }




}
