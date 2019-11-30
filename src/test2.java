import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class test2 extends JFrame{
    private JButton registerButton;
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel close;
    private JSeparator separator1;
    private JSeparator separator2;
    private JSeparator seperator3;
    private JSeparator seperator4;
    private JSeparator seperator5;
    private JSeparator seperator6;
    private JTextField Imie;
    private JTextField Nazwisko;
    private JTextField Email;
    private JPasswordField RePassword;
    private JTextField Ulica;
    private JTextField NrDomu;
    private JTextField KodPocztowy;
    private JTextField Miasto;
    private JTextField NrTelefonu;
    private JSeparator seperator7;
    private JSeparator seperator8;
    private JSeparator seperator9;
    private JSeparator seperator10;
    private JSeparator seperator11;
    private int pX, pY;
    private boolean [] isChanged;


    public test2(boolean nightLayout)
    {


        isChanged = new boolean [9];
        add(rootPanel);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setTitle(test.studioName);
        setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);

        rootPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.RED));

        if(nightLayout)
        {
            rootPanel.setBackground(Color.decode("#434343"));
            dragPanel.setBackground(Color.decode("#434343"));
        }
        else
        {
            rootPanel.setBackground(Color.WHITE);
            dragPanel.setBackground(Color.WHITE);
        }
        ResourceBundle bundle = ResourceBundle.getBundle("jezyk");


        //setSize(400, 500);
        dragPanel.setFocusable(true);

        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        //registerButton.setContentAreaFilled(false);


        textField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        Imie.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        Nazwisko.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        passwordField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        passwordField1.setEchoChar((char) 0);
        RePassword.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        RePassword.setEchoChar((char) 0);
        Ulica.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        NrDomu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        KodPocztowy.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        Miasto.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        NrTelefonu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));


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
                if(textField1.getText().equals(bundle.getString("Rejestracja.userName"))) {
                    textField1.setText("");
                    isChanged[0] = true;
                }
                separator1.setForeground(Color.decode("#D9D9D9"));
                separator1.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(textField1.getText().equals("")) {
                    textField1.setText(bundle.getString("Rejestracja.userName"));
                    isChanged[0] = false;
                }
                separator1.setForeground(Color.decode("#BB1407"));
                separator1.setBackground(Color.decode("#BB1407"));
            }
        });
        Imie.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Imie.getText().equals(bundle.getString("Rejestracja.firstName"))) {
                    Imie.setText("");
                    isChanged[1] = true;
                }
                seperator3.setForeground(Color.decode("#D9D9D9"));
                seperator3.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(Imie.getText().equals("")) {
                    Imie.setText(bundle.getString("Rejestracja.firstName"));
                    isChanged[1] = false;
                }
                seperator3.setForeground(Color.decode("#BB1407"));
                seperator3.setBackground(Color.decode("#BB1407"));
            }
        });
        Nazwisko.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Nazwisko.getText().equals(bundle.getString("Rejestracja.lastName"))) {
                    Nazwisko.setText("");
                    isChanged[2] = true;
                }
                seperator4.setForeground(Color.decode("#D9D9D9"));
                seperator4.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(Nazwisko.getText().equals("")) {
                    Nazwisko.setText(bundle.getString("Rejestracja.lastName"));
                    isChanged[2] = false;
                }
                seperator4.setForeground(Color.decode("#BB1407"));
                seperator4.setBackground(Color.decode("#BB1407"));
            }
        });



        passwordField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(passwordField1.getText().equals(bundle.getString("Rejestracja.password"))) {
                    passwordField1.setText("");
                    isChanged[4] = true;
                }
                passwordField1.setEchoChar('•');
                separator2.setForeground(Color.decode("#D9D9D9"));
                separator2.setBackground(Color.decode("#D9D9D9"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(passwordField1.getText().equals("")) {
                    passwordField1.setEchoChar((char) 0);
                    passwordField1.setText(bundle.getString("Rejestracja.password"));
                    isChanged[4] = false;
                }
                separator2.setForeground(Color.decode("#BB1407"));
                separator2.setBackground(Color.decode("#BB1407"));
            }
        });
        RePassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(RePassword.getText().equals(bundle.getString("Rejestracja.password"))) {
                    RePassword.setText("");
                }
                RePassword.setEchoChar('•');
                seperator6.setForeground(Color.decode("#D9D9D9"));
                seperator6.setBackground(Color.decode("#D9D9D9"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(RePassword.getText().equals("")) {
                    RePassword.setEchoChar((char) 0);
                    RePassword.setText(bundle.getString("Rejestracja.password"));
                }
                seperator6.setForeground(Color.decode("#BB1407"));
                seperator6.setBackground(Color.decode("#BB1407"));
            }
        });
        Ulica.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Ulica.getText().equals(bundle.getString("Rejestracja.street"))) {
                    Ulica.setText("");
                    isChanged[3] = true;
                }
                seperator7.setForeground(Color.decode("#D9D9D9"));
                seperator7.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(Ulica.getText().equals("")) {
                    Ulica.setText(bundle.getString("Rejestracja.street"));
                    isChanged[3] = false;
                }
                seperator7.setForeground(Color.decode("#BB1407"));
                seperator7.setBackground(Color.decode("#BB1407"));
            }
        });

        NrDomu.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(NrDomu.getText().equals(bundle.getString("Rejestracja.houseNumber"))) {
                    NrDomu.setText("");
                    isChanged[5] = true;
                }
                seperator8.setForeground(Color.decode("#D9D9D9"));
                seperator8.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(NrDomu.getText().equals("")) {
                    NrDomu.setText(bundle.getString("Rejestracja.houseNumber"));
                    isChanged[5] = false;
                }
                seperator8.setForeground(Color.decode("#BB1407"));
                seperator8.setBackground(Color.decode("#BB1407"));
            }
        });

        KodPocztowy.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(KodPocztowy.getText().equals(bundle.getString("Rejestracja.postalCode"))) {
                    KodPocztowy.setText("");
                    isChanged[6] = true;
                }
                seperator9.setForeground(Color.decode("#D9D9D9"));
                seperator9.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(KodPocztowy.getText().equals("")) {
                    KodPocztowy.setText(bundle.getString("Rejestracja.postalCode"));
                    isChanged[6] = false;
                }
                seperator9.setForeground(Color.decode("#BB1407"));
                seperator9.setBackground(Color.decode("#BB1407"));
            }
        });

        Miasto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(Miasto.getText().equals(bundle.getString("Rejestracja.city"))) {
                    Miasto.setText("");
                    isChanged[7] = true;
                }
                seperator10.setForeground(Color.decode("#D9D9D9"));
                seperator10.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(Miasto.getText().equals("")) {
                    Miasto.setText(bundle.getString("Rejestracja.city"));
                    isChanged[7] = false;
                }
                seperator10.setForeground(Color.decode("#BB1407"));
                seperator10.setBackground(Color.decode("#BB1407"));
            }
        });

        NrTelefonu.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(NrTelefonu.getText().equals(bundle.getString("Rejestracja.mobile"))) {
                    NrTelefonu.setText("");
                    isChanged[8] = true;
                }
                seperator11.setForeground(Color.decode("#D9D9D9"));
                seperator11.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(NrTelefonu.getText().equals("")) {
                    NrTelefonu.setText(bundle.getString("Rejestracja.mobile"));
                    isChanged[8] = false;
                }
                seperator11.setForeground(Color.decode("#BB1407"));
                seperator11.setBackground(Color.decode("#BB1407"));
            }
        });





        registerButton.setFocusPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);
        registerButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (registerButton.getModel().isPressed()) {
                    registerButton.setBackground(Color.decode("#7B1207"));
                } /*else if (registerButton.getModel().isRollover()) {
                    registerButton.setBackground(Color.RED);
                } */else {
                    registerButton.setBackground(Color.decode("#BB1407"));
                }
            }
        });


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mypass = String.copyValueOf(passwordField1.getPassword());
                String myrepass = String.copyValueOf(RePassword.getPassword());
                if(mypass.equals(myrepass)) {
                    if(isChanged[0] && isChanged[1] && isChanged[2] && isChanged[3] && isChanged[4] && isChanged[5] && isChanged[6] && isChanged[7] && isChanged[8] ) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery("select * from klienci");
                            int admin =1;
                            if (resultSet != null)
                            {
                                resultSet.last();
                                if(resultSet.getRow()>0)
                                    admin = 0;
                            }


                            int res = statement.executeUpdate("INSERT INTO `klienci`(`id`, `login`, `haslo`, `imie`, `nazwisko`, `ulica`, `nrdomu`, `kodpocztowy`, `miasto`, `telefon`, `admin`) VALUES (0,'"
                                    + textField1.getText() + "','" + mypass + "','" + Imie.getText() + "','" + Nazwisko.getText() + "','" + Ulica.getText() +
                                    "','" + NrDomu.getText() + "','" + KodPocztowy.getText()+ "','" + Miasto.getText() +  "','" + NrTelefonu.getText() + "','" +admin + "')");
                    /*ResultSet resultSet = statement.executeQuery("select * from klienci where login='"+textField1.getText()+"' and haslo='"+mypass+"'");
                    while(resultSet.next())
                    {
                        System.out.println(resultSet.getString("imie") + " " + resultSet.getString("nazwisko"));
                    }*/
                            if(res > 0){
                                // SQL SUCCESSFUL ADD DATA
                                setVisible(false);
                            }
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                    }else{
                        System.out.println("field(s) empty");
                    }
                }else{
                    System.out.println("passwords are different");
                }
            }
        });
    }



}
