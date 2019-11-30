import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;


public class ListaZakupow extends JFrame{
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JLabel close;
    private JButton signUpButton;
    private JPanel mainPanel;
    private int pX, pY;
    private Connection connection;
    private Statement statement;


    public ListaZakupow(boolean nightLayout, test3 owner, int idUzytkwonika)
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
            mainPanel.setBackground(Color.decode("#434343"));
        }
        else
        {
            rootPanel.setBackground(Color.WHITE);
            dragPanel.setBackground(Color.WHITE);
            mainPanel.setBackground(Color.WHITE);
        }



        //dragPanel.setBackground(Color.WHITE);
        //setSize(400, 500);
        dragPanel.setFocusable(true);

        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        //signUpButton.setContentAreaFilled(false);



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


            try
            {
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
                statement = connection.createStatement();


                JPanel jp= new JPanel(new BorderLayout());
                //jPanel.setBackground(Color.decode("#434343"));
                jp.setBackground(Color.decode("#D9D9D9"));
                jp.setPreferredSize(new Dimension(300, 60));
                jp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                jp.setName("musicPanel");


                ResultSet resultSet = statement.executeQuery("select nazwa from piosenka, kupione where piosenka.id = kupione.piosenka and kupione.klient="+idUzytkwonika);
                int icrem=1;


                while(resultSet.next())
                {


                    JPanel jPanel= new JPanel(new BorderLayout());
                    //jPanel.setBackground(Color.decode("#434343"));
                    jPanel.setBackground(Color.decode("#D9D9D9"));
                    jPanel.setPreferredSize(new Dimension(250, 45));
                    jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    jPanel.setName("musicPanel"+icrem);



                    JLabel jLabel = new JLabel(String.valueOf(icrem));
                    jLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                    jLabel.setVerticalAlignment(JLabel.CENTER);
                    jLabel.setHorizontalAlignment(JLabel.LEFT);
                    jLabel.setVerticalTextPosition(JLabel.EAST);
                    icrem++;

                    JLabel jLabel1 = new JLabel(resultSet.getString("nazwa"));
                    jLabel1.setFont(new Font("SansSerif", Font.BOLD, 14));
                    jLabel1.setVerticalAlignment(JLabel.CENTER);
                    jLabel1.setHorizontalAlignment(JLabel.CENTER);
                    jLabel1.setVerticalTextPosition(JLabel.CENTER);

                    jPanel.add(jLabel1, BorderLayout.CENTER);

                    jPanel.add(jLabel, BorderLayout.WEST);

                    mainPanel.add(jPanel);
                }
            }
            catch (Exception exc)
            {
                exc.printStackTrace();
            }






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
                dane d;
                if(idUzytkwonika == -1)
                    d = new dane(nightLayout);
                dispose();
            }
        });

    }





}
