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


public class test3 extends JFrame{
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JLabel close;
    protected JPanel menuPanel;
    private JButton testButton;
    private JButton testbutton2;
    private JPanel mainPanel;
    protected JPanel menuLongPanel;
    private JTextField serach;
    private JLabel L1;
    private JLabel L2;
    private JLabel L3;
    private JPanel mainPanel2;
    private JButton btnTworca;
    private JButton btnPlyta;
    private JButton btnPiosenka;
    private JButton btnSettings;
    private JLabel L4;
    private JButton btnSklep;
    private JLabel L5;
    private int pX, pY;
    private int startX, startY, endX, endY, thatJ;
    private ArrayList<JPanel> musicPanels;
    private ArrayList<JButton> musicButtons;
    private ArrayList<JLabel> longmenulabel;
    private ArrayList<JPanel> songsPanels;
    private Connection connection;
    private Statement statement;


    protected boolean ISADMIN = false;
    protected boolean nightLayout;
    private test3 owner = this;
    private int idPlyty = 0;
    private int idTworcy = 0;
    private int idUzytkownika = -1;


    public test3(boolean _nightLayout, Uzytkownik uzytkownik)
    {



        this.nightLayout = _nightLayout;
        add(rootPanel);
        init();
        //initLongMenu();
        initQuickMenu();

        if(uzytkownik != null) {
            idUzytkownika = uzytkownik.id;
            if (uzytkownik.admin == 1)
                ISADMIN = true;
            else {
                ISADMIN = false;
            }
        }else{
            ISADMIN = false;
        }
        reloadAdminRight(ISADMIN);


        try{

            ResultSet r_s1 = statement.executeQuery("select * from plyta");
            r_s1.next();
            idPlyty = r_s1.getInt("id");
            ResultSet r_s2 = statement.executeQuery("select * from tworca");
            r_s2.next();
            idTworcy = r_s2.getInt("id");
            loadPiosenka(idPlyty, idTworcy);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }




        btnPlyta.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                OknoDodawaniaPlyt oknoDodawaniaPlyt = new OknoDodawaniaPlyt(nightLayout, owner);
            }
        });
        btnPiosenka.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(idPlyty !=0) {
                    OknoDodawaniaUtworow oknoDodawaniaUtworow = new OknoDodawaniaUtworow(nightLayout, owner, idPlyty, 0, idTworcy);
                }
            }
        });
        btnTworca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(idTworcy !=0) {
                    OknoDodawaniaTworcow oknoDodawaniaTworcow = new OknoDodawaniaTworcow(nightLayout, owner, 0, idPlyty);
                }
            }
        });
        btnSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Ustawienia ustawienia = new Ustawienia(nightLayout, owner, ISADMIN, idUzytkownika);
            }
        });

        btnSklep.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ListaZakupow listaZakupow = new ListaZakupow(nightLayout, owner, idUzytkownika);
            }
        });



        L2.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                OknoDodawaniaPlyt oknoDodawaniaPlyt = new OknoDodawaniaPlyt(nightLayout, owner);
            }
        });
        L3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(idPlyty !=0) {
                    OknoDodawaniaUtworow oknoDodawaniaUtworow = new OknoDodawaniaUtworow(nightLayout, owner, idPlyty, 0, idTworcy);
                }
            }
        });
        L1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(idTworcy !=0) {
                    OknoDodawaniaTworcow oknoDodawaniaTworcow = new OknoDodawaniaTworcow(nightLayout, owner, 0, idPlyty);
                }
            }
        });
        L4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Ustawienia ustawienia = new Ustawienia(nightLayout, owner, ISADMIN, idUzytkownika);
            }
        });

        L5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ListaZakupow listaZakupow = new ListaZakupow(nightLayout, owner, idUzytkownika);
            }
        });




    }

    public void reloadAdminRight(boolean isAdmin)
    {
        ISADMIN = isAdmin;
        if (!ISADMIN){
            btnTworca.setVisible(false);
            btnPiosenka.setVisible(false);
            btnPlyta.setVisible(false);
            L1.setVisible(false);
            L2.setVisible(false);
            L3.setVisible(false);
            L5.setVisible(true);
            btnSklep.setVisible(true);
        }else
        {
            btnTworca.setVisible(true);
            btnPiosenka.setVisible(true);
            btnPlyta.setVisible(true);
            L1.setVisible(true);
            L2.setVisible(true);
            L3.setVisible(true);
            L5.setVisible(false);
            btnSklep.setVisible(false);
        }
        reloadPlyty();
        reloadPiosenki(idPlyty, idTworcy);
    }


    public void reloadPlyty()
    {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        loadPlyty();
        repaint();
    }

    public void reloadPiosenki(int idPlyty, int idTworcy)
    {

        mainPanel2.removeAll();
        mainPanel2.revalidate();
        mainPanel2.repaint();
        if(idPlyty != 0)
            loadPiosenka(idPlyty, idTworcy);
        repaint();
    }

    private void loadPlyty()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from plyta");
            while(resultSet.next())
            {
                int ID = resultSet.getInt("id");
                int IDTworcy = resultSet.getInt("tworca");

                JPanel jPanel= new JPanel(new BorderLayout());
                //jPanel.setBackground(Color.decode("#434343"));
                jPanel.setBackground(Color.decode("#D9D9D9"));
                jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 20, 5));
                jPanel.setName("musicPanel"+resultSet.getInt("id"));
                //musicPanels.add(jPanel);
                JButton jButton = new JButton();

                java.sql.Blob blob = resultSet.getBlob("zdj");
                InputStream in = blob.getBinaryStream();
                BufferedImage image = ImageIO.read(in);

                //ImageIcon imagIcon =  new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/"+ImgSource[i])));
                //Image image = imagIcon.getImage();
                Image imageSized = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(imageSized);
                jButton.setIcon(icon);
                jButton.setFocusPainted(false);
                jButton.setBorder(null);
                jButton.setContentAreaFilled(false);
                jButton.setOpaque(true);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //mainPanel.removeAll();
                        //mainPanel.revalidate();
                        //mainPanel.repaint();
                        reloadPiosenki(ID,IDTworcy);
                        idPlyty = ID;

                    }
                });

                JLabel jLabel = new JLabel(resultSet.getString("nazwa"));
                jLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                jLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
                jLabel.setVerticalAlignment(JLabel.CENTER);
                jLabel.setHorizontalAlignment(JLabel.CENTER);
                jLabel.setVerticalTextPosition(JLabel.CENTER);

                if(ISADMIN) {
                    JLabel jLabel1 = new JLabel("x");
                    jLabel1.setFont(new Font("SansSerif", Font.BOLD, 14));
                    jLabel1.setBorder(BorderFactory.createEmptyBorder(10, 4, 0, 5));
                    jLabel1.setForeground(Color.RED);
                    jLabel1.setVerticalAlignment(JLabel.CENTER);
                    jLabel1.setHorizontalAlignment(JLabel.RIGHT);
                    jLabel1.setVerticalTextPosition(JLabel.CENTER);
                    jLabel1.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            mainPanel.remove(jPanel);
                            mainPanel.revalidate();
                            mainPanel.repaint();

                            try
                            {
                                ResultSet resultSet = statement.executeQuery("select * from plyta where tworca="+IDTworcy);
                                if (resultSet != null)
                                {
                                    resultSet.last();
                                    if(resultSet.getRow()==1) {
                                        PreparedStatement stmt = connection.prepareStatement("delete from tworca where id=?");
                                        stmt.setInt(1, IDTworcy);
                                        stmt.executeUpdate();
                                    }
                                }

                                PreparedStatement stmt = connection.prepareStatement("delete from plyta where id=?");
                                stmt.setInt(1, ID);
                                stmt.executeUpdate();



                                System.out.println("Record deleted successfully");
                            } catch (SQLException ee) {
                                ee.printStackTrace();
                            }
                        }
                    });

                    JLabel jLabel2 = new JLabel(":");
                    jLabel2.setFont(new Font("SansSerif", Font.BOLD, 14));
                    jLabel2.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 4));
                    jLabel2.setForeground(Color.RED);
                    jLabel2.setVerticalAlignment(JLabel.CENTER);
                    jLabel2.setHorizontalAlignment(JLabel.RIGHT);
                    jLabel2.setVerticalTextPosition(JLabel.CENTER);
                    jLabel2.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {

                            OknoEdytowaniaPlyt oknoEdytowaniaPlyt = new OknoEdytowaniaPlyt(nightLayout, owner, ID);
                        }
                    });


                    jPanel.add(jLabel1, BorderLayout.EAST);
                    jPanel.add(jLabel2, BorderLayout.WEST);
                }

                jPanel.add(jButton, BorderLayout.NORTH);
                jPanel.add(jLabel, BorderLayout.CENTER);

                mainPanel.add(jPanel);

            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }

    private void loadPiosenka(int idPlyty, int idTworcy)
    {
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

            ResultSet _rs = statement.executeQuery("select * from tworca where id="+idTworcy);
            _rs.next();
            JLabel jl = new JLabel(_rs.getString("nazwa"));
            jl.setFont(new Font("SansSerif", Font.BOLD, 18));
            jl.setVerticalAlignment(JLabel.CENTER);
            jl.setHorizontalAlignment(JLabel.CENTER);
            jl.setVerticalTextPosition(JLabel.CENTER);

            JLabel j2 = new JLabel(":");
            j2.setFont(new Font("SansSerif", Font.BOLD, 18));
            j2.setForeground(Color.RED);
            j2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    OknoDodawaniaTworcow oknoDodawaniaTworcow = new OknoDodawaniaTworcow(nightLayout, owner, idTworcy, idPlyty);
                }
            });

            jp.add(jl, BorderLayout.CENTER);
            if(ISADMIN)
                jp.add(j2, BorderLayout.EAST);
            mainPanel2.add(jp);


            ArrayList<Integer> kupionePiosenkiId = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select * from kupione where klient="+idUzytkownika);
            while(rs.next())
            {
                kupionePiosenkiId.add(rs.getInt("piosenka"));
            }


            ResultSet resultSet = statement.executeQuery("select * from piosenka where plyta="+idPlyty);
            int icrem = 1;



            while(resultSet.next())
            {
                int ID = resultSet.getInt("id");


                JPanel jPanel= new JPanel(new BorderLayout());
                //jPanel.setBackground(Color.decode("#434343"));
                jPanel.setBackground(Color.decode("#D9D9D9"));
                jPanel.setPreferredSize(new Dimension(250, 45));
                jPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                jPanel.setName("musicPanel"+ID);
                songsPanels.add(jPanel);



                JLabel buyLabel = new JLabel(" + ");
                buyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                buyLabel.setForeground(Color.RED);
                buyLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        try
                        {
                            int res = statement.executeUpdate("INSERT INTO `kupione`(`id`, `klient`, `piosenka`) VALUES (0, '" + idUzytkownika + "','" +ID + "')");
                        } catch (SQLException ee) {
                            ee.printStackTrace();
                        }
                        buyLabel.setVisible(false);
                    }
                });

                JPanel underPanel = new JPanel(new BorderLayout());
                underPanel.setBackground(Color.decode("#D9D9D9"));
                JLabel underJLabel1 = new JLabel(": ");
                underJLabel1.setFont(new Font("SansSerif", Font.BOLD, 14));
                underJLabel1.setForeground(Color.RED);
                underJLabel1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        OknoDodawaniaUtworow oknoDodawaniaUtworow = new OknoDodawaniaUtworow(nightLayout, owner, idPlyty, ID, idTworcy);
                    }
                });

                JLabel underJLabel2 = new JLabel(" x");
                underJLabel2.setFont(new Font("SansSerif", Font.BOLD, 14));
                underJLabel2.setForeground(Color.RED);
                underJLabel2.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        mainPanel2.remove(jPanel);
                        mainPanel2.revalidate();
                        mainPanel2.repaint();
                        try
                        {
                            PreparedStatement stmt = connection.prepareStatement("delete from piosenka where id=?");

                            stmt.setInt(1, ID);
                            stmt.executeUpdate();

                            System.out.println("Record deleted successfully");
                        } catch (SQLException ee) {
                            ee.printStackTrace();
                        }
                    }
                });
                underPanel.add(underJLabel1, BorderLayout.WEST);
                underPanel.add(underJLabel2, BorderLayout.EAST);



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

                if(ISADMIN)
                    jPanel.add(underPanel, BorderLayout.EAST);
                else {
                    boolean kupioneJuz = false;
                    for(int i=0; i<kupionePiosenkiId.size(); i++) {
                        if (kupionePiosenkiId.get(i).equals(ID))
                            kupioneJuz = true;
                    }

                    if(!kupioneJuz)
                        jPanel.add(buyLabel, BorderLayout.EAST);
                }

                mainPanel2.add(jPanel);

            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

    }


    private void init(){
        setUndecorated(true);
        setLocationRelativeTo(null);
        setTitle(test.studioName);

        musicPanels = new ArrayList<>();
        musicButtons = new ArrayList<>();
        songsPanels = new ArrayList<>();

        setBackground(Color.RED);

        if(nightLayout)
        {
            setDarkDesign();
        }
        else
        {
            setLightDesign();

        }

        //rootPanel.setBackground(Color.RED);
        //rootPanel.setBackground(Color.decode("#D9D9D9"));
        rootPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Color.RED));

        //mainPanel.setBackground(Color.decode("#D9D9D9"));

        //dragPanel.setBackground(Color.decode("#D9D9D9"));
        //setSize(400, 500);
        dragPanel.setFocusable(true);




        //mainPanel.setVisible(false);
        //mainPanel2.setVisible(true);

        //menuPanel.setBackground(Color.decode("#D9D9D9"));
        menuPanel.setBackground(Color.RED);
        menuLongPanel.setBackground(Color.RED);

        serach.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        menuPanel.setVisible(false);
        menuLongPanel.setVisible(false);

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
        //cr.setMaximumSize(mainPanel.getMaximumSize());
        //cr.setMinimumSize(mainPanel.getMinimumSize());
    }

    protected void initLongMenu()
    {
        menuLongPanel.setVisible(true);

        longmenulabel = new ArrayList<>();
        longmenulabel.add(L1);
        longmenulabel.add(L2);
        longmenulabel.add(L3);
        longmenulabel.add(L4);
        longmenulabel.add(L5);
        for(int i=0; i<longmenulabel.size(); i++)
        {
            longmenulabel.get(i).setForeground(Color.decode("#434343"));
        }

        for(int i=0; i<longmenulabel.size(); i++)
        {
            final int ii = i;
            longmenulabel.get(i).addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    longmenulabel.get(ii).setForeground(Color.decode("#D9D9D9"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    longmenulabel.get(ii).setForeground(Color.decode("#434343"));
                }
            });
        }
    }

    protected void initQuickMenu()
    {
        menuPanel.setVisible(true);
        String[] sciezkiDoIcon = {
                "/icons/icons8-search-256-2.png",
                "/icons/icons8-user-male-256.png",
                "/icons/icons8-microsoft-groove-500.png",
                "/icons/icons8-itunes-512.png",
                "/icons/shopping-bag.png",
                "/icons/icons8-settings-384.png"
        };

        ArrayList<JButton> jButtons = new ArrayList<>();
        jButtons.add(testbutton2);
        jButtons.add(btnTworca);
        jButtons.add(btnPlyta);
        jButtons.add(btnPiosenka);
        jButtons.add(btnSklep);
        jButtons.add(btnSettings);

        for(int i=0; i<jButtons.size(); i++)
        {
            ImageIcon imagIcon1 =  new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(sciezkiDoIcon[i])));
            Image image1 = imagIcon1.getImage();
            Image imageSized1 = image1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon icon1 = new ImageIcon(imageSized1);
            jButtons.get(i).setIcon(icon1);
            jButtons.get(i).setFocusPainted(false);
            jButtons.get(i).setBorder(null);
            jButtons.get(i).setContentAreaFilled(false);
            jButtons.get(i).setOpaque(true);
            int finalI = i;
            jButtons.get(i).addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent evt) {
                    if (jButtons.get(finalI).getModel().isPressed()) {
                        jButtons.get(finalI).setBackground(Color.decode("#7B1207"));
                    } /*else if (signUpButton.getModel().isRollover()) {
                    signUpButton.setBackground(Color.RED);
                } */else {
                        jButtons.get(finalI).setBackground(Color.RED);
                    }
                }
            });
        }
    }


    protected void setLightDesign(){
        rootPanel.setBackground(Color.WHITE);
        dragPanel.setBackground(Color.WHITE);
        mainPanel.setBackground(Color.WHITE);
        mainPanel2.setBackground(Color.WHITE);
        nightLayout = false;
    }

    protected void setDarkDesign(){
        rootPanel.setBackground(Color.decode("#434343"));
        dragPanel.setBackground(Color.decode("#434343"));
        mainPanel.setBackground(Color.decode("#434343"));
        mainPanel2.setBackground(Color.decode("#434343"));
        nightLayout = true;
    }





}
