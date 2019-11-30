import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class test extends JFrame{
    private JButton signUpButton;
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel close;
    private JSeparator separator1;
    private JSeparator separator2;
    private JLabel CreateAccount;
    private JLabel SkipLogin;
    private int pX, pY;
    private boolean nightLayout;
    private Connection connection;
    private Statement statement;
    static String studioName;


    public test(boolean nightLayout)
    {
        this.nightLayout = nightLayout;

        add(rootPanel);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setTitle("STUDIO");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from studio");
            resultSet.next();
            studioName = resultSet.getString("nazwa");
            setTitle(studioName);
        } catch (SQLException e) {
            e.printStackTrace();
        }




        setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);

        //
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



        //
        //setSize(400, 500);
        dragPanel.setFocusable(true);

        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        //signUpButton.setContentAreaFilled(false);


        textField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        passwordField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        passwordField1.setEchoChar((char) 0);


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
                if(textField1.getText().equals(bundle.getString("Logowanie.userName")))
                    textField1.setText("");
                separator1.setForeground(Color.decode("#D9D9D9"));
                separator1.setBackground(Color.decode("#D9D9D9"));

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(textField1.getText().equals("")) {
                    textField1.setText(bundle.getString("Logowanie.userName"));
                }
                separator1.setForeground(Color.decode("#BB1407"));
                separator1.setBackground(Color.decode("#BB1407"));
            }
        });

        passwordField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                String mypass = String.copyValueOf(passwordField1.getPassword());
                if(mypass.equals(bundle.getString("Logowanie.password"))) {
                    passwordField1.setText("");
                }
                passwordField1.setEchoChar('•');
                separator2.setForeground(Color.decode("#D9D9D9"));
                separator2.setBackground(Color.decode("#D9D9D9"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                String mypass = String.copyValueOf(passwordField1.getPassword());
                if(mypass.equals("")) {
                    passwordField1.setEchoChar((char) 0);
                    passwordField1.setText(bundle.getString("Logowanie.password"));
                }
                separator2.setForeground(Color.decode("#BB1407"));
                separator2.setBackground(Color.decode("#BB1407"));
            }
        });


        signUpButton.setFocusPainted(false);

        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(true);

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

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String mypass = String.copyValueOf(passwordField1.getPassword());
                    ResultSet resultSet = statement.executeQuery("select * from klienci where login='"+textField1.getText()+"' and haslo='"+mypass+"'");
                    resultSet.next();
                    Uzytkownik uzytkownik = new Uzytkownik();
                    uzytkownik.id = resultSet.getInt("id");
                    uzytkownik.imie = resultSet.getString("imie");
                    uzytkownik.nazwisko = resultSet.getString("nazwisko");
                    uzytkownik.ulica = resultSet.getString("ulica");
                    uzytkownik.nrdomu = resultSet.getString("nrdomu");
                    uzytkownik.kodPocztowy = resultSet.getString("kodPocztowy");
                    uzytkownik.miasto = resultSet.getString("miasto");
                    uzytkownik.telefon = resultSet.getString("telefon");
                    uzytkownik.admin = resultSet.getInt("admin");
                    test3 _tes3 = new test3(nightLayout, uzytkownik);
                    dispose();
                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
            }
        });


        CreateAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        test2 _test2 = new test2(nightLayout);
                    }
                });
            }
        });

        SkipLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        test3 _test3 = new test3(nightLayout, null);
                        dispose();
                    }
                });
            }
        });
    }



}
