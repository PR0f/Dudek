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


public class OknoEdytowaniaPlyt extends JFrame{
    private JButton signUpButton;
    private JPanel rootPanel;
    private JPanel dragPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel close;
    private JButton getFile;
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
    private boolean changedImage = false;


    public OknoEdytowaniaPlyt(boolean nightLayout, test3 owner, int ID)
    {

        add(rootPanel);
        setUndecorated(true);
        setLocationRelativeTo(null);

        setTitle(test.studioName);
        setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);
        //rootPanel.setBackground(Color.WHITE);
        rootPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.RED));

        ArrayList<Integer> comboBox1ID = new ArrayList<>();


        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studio", "root", "");
            System.out.println(ID);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from plyta where id="+ID);
            resultSet.next();
            java.sql.Blob blob = resultSet.getBlob("zdj");
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            Image imageSized = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(imageSized);
            getFile.setIcon(icon);

            textField1.setText(resultSet.getString("nazwa"));

            int IDoftworca =resultSet.getInt("tworca");
            int ite=0;

            ResultSet _resultSet = statement.executeQuery("select * from tworca");
            while(_resultSet.next())
            {
                int temp = _resultSet.getInt("id");
                comboBox1ID.add(temp);


                comboBox1.addItem(_resultSet.getString("nazwa"));
                if(IDoftworca == temp)
                    comboBox1.setSelectedIndex(ite);
                ite++;
            }


        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

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



        getFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    filepath = selectedFile.getAbsolutePath();
                    changedImage = true;
                }
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No Data");
                }


            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    if(changedImage) {

                        PreparedStatement ps = connection.prepareStatement("update plyta set nazwa=?, zdj=?, tworca=?  where id=?");
                        InputStream is = new FileInputStream(new File(filepath));
                        ps.setString(1, textField1.getText());
                        ps.setBlob(2, is);
                        ps.setInt(3, comboBox1ID.get(comboBox1.getSelectedIndex()));
                        ps.setInt(4, ID);
                        int res = ps.executeUpdate();
                        //JOptionPane.showMessageDialog(null, "Data Inserted");
                        if (res > 0) {
                            // SQL SUCCESSFUL ADD DATA
                            owner.reloadPlyty();
                            owner.reloadPiosenki(ID, comboBox1ID.get(comboBox1.getSelectedIndex()));

                            dispose();
                        }
                    }else
                    {
                        PreparedStatement ps = connection.prepareStatement("update plyta set nazwa=?, tworca=? where id=?");
                        ps.setString(1, textField1.getText());
                        ps.setInt(2, comboBox1ID.get(comboBox1.getSelectedIndex()));
                        ps.setInt(3, ID);
                        int res = ps.executeUpdate();
                        //JOptionPane.showMessageDialog(null, "Data Inserted");
                        if (res > 0) {
                            // SQL SUCCESSFUL ADD DATA
                            owner.reloadPlyty();
                            owner.reloadPiosenki(ID, comboBox1ID.get(comboBox1.getSelectedIndex()));

                            dispose();
                        }
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
