import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;


public class OknoDodawaniaTworcow extends JFrame{
    private JButton signUpButton;
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel close;
    private JComboBox comboBox1;
    private JSeparator separator1;
    private JSeparator separator2;
    private JLabel CreateAccount;
    private JLabel SkipLogin;
    private int pX, pY;
    private String filepath = "";
    private boolean _succ = false;
    private Connection connection;
    private Statement statement;


    public OknoDodawaniaTworcow(boolean nightLayout, test3 owner, int idTworcy, int idPlyty)
    {

        add(rootPanel);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setTitle(test.studioName);
        setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);
        //rootPanel.setBackground(Color.WHITE);
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



        //dragPanel.setBackground(Color.WHITE);
        //setSize(400, 500);
        dragPanel.setFocusable(true);

        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        //signUpButton.setContentAreaFilled(false);


        textField1.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));


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

        if(idTworcy != 0) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from tworca where id=" + idTworcy);
                resultSet.next();
                textField1.setText(resultSet.getString("nazwa"));


            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }


        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
                    PreparedStatement ps;
                    if(idTworcy == 0) {
                        ps = connection.prepareStatement("insert into tworca(id,nazwa) values(?,?)");
                        ps.setInt(1, 0);
                        ps.setString(2, textField1.getText());
                    }
                    else {
                        ps = connection.prepareStatement("update tworca set nazwa=? where id=?");
                        ps.setString(1, textField1.getText());
                        ps.setInt(2, idTworcy);
                    }

                    int res = ps.executeUpdate();
                    //JOptionPane.showMessageDialog(null, "Data Inserted");
                    if(res > 0){
                        // SQL SUCCESSFUL ADD DATA
                        if(idTworcy != 0)
                            owner.reloadPiosenki(idPlyty, idTworcy);

                        dispose();
                    }

                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
            }
        });

    }




}
