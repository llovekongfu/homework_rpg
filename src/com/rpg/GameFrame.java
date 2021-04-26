package com.rpg;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JFrame implements KeyListener, ActionListener, MouseListener {
    private JPanel mainPanel = null;
    private Hunter hunter = null;
    private GameLevel curLevel;
    private int map[][];
    private Position position[][] = new Position[20][16];
    private Dark dark[][] = new Dark[20][16];
    private Note note = null;
    private int treasureNum;
    private JButton driveButton = new JButton();
    private JButton darkButton = new JButton();
    private Font tfont = new Font("??", 1, 30);
    private JLabel treasureNumLabel = new JLabel();

    private boolean isDriveDark = false;
    private boolean isLookMap = true;

    private JLabel time;
    private Timer timer;
    private int hour = 0, mnt = 0, scd = 0;
    private int lookTime;

    public GameFrame() {
        boolean isLoad = DataUtil.loadData();
        if (!isLoad) {
            JOptionPane.showMessageDialog(this, "data load fail!");
            System.exit(0);
        }
        initAll();
        setVisible(true);
        setSize(1317, 847);
        setTitle("Maze game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        //Put the program in the middle of the screen
        Dimension frameSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int center_X = screenSize.width / 2;
        int center_Y = screenSize.height / 2;
        setLocation(center_X - frameSize.width / 2, center_Y - frameSize.height / 2);
    }

    /**
     * Add time monitor to calculate time
     */
    public void initTime() {
        ActionListener keepTime = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = "";

                scd++;

                if (scd >= 60) {
                    mnt++;
                    scd = 0;
                }

                if (mnt >= 60) {
                    hour++;
                    mnt = 0;
                }

                if (hour < 10) {
                    str = "0" + hour;
                } else {
                    str = "" + hour;
                }

                if (mnt < 10) {
                    str = str + ":" + "0" + mnt;
                } else {
                    str = str + ":" + mnt;
                }

                if (scd < 10) {
                    str = str + ":" + "0" + scd;
                } else {
                    str = str + ":" + scd;
                }

                time.setText(str);
                if (isLookMap) {
                    JOptionPane.showMessageDialog(null, "You will have " + lookTime + " seconds to view the map\n" + "please hurry up");
                    isLookMap = false;
                }
                lookMap();
                gameOver();
            }
        };
        timer = new Timer(1000, keepTime);
        time = new JLabel("00:00:00");
        time.setForeground(Color.RED);
        time.setFont(new Font("??", 1, 25));
        time.setBounds(160, 410, 200, 20);
        note.add(time);
        timer.start();
    }

    public void showTreasureNum() {
        treasureNumLabel.setText(String.valueOf(treasureNum));
    }

    public void lookMap() {
        if (time.getText().equals("00:00:0" + String.valueOf(lookTime))) {
            createHunter();
            restoreFog();
        }
    }

    public void initAll() {
        initData();
        //Clear layout
        Container con = getContentPane();
        con.setLayout(null);

        //Main panel
        mainPanel = new MainPanel();
        mainPanel.setBounds(0, 0, 1000, 800);
        mainPanel.setLayout(null);
        con.add(mainPanel);

        //Description panel
        note = new Note();
        note.setBounds(1000, 0, 300, 1000);
        note.setLayout(null);
        con.add(note);

        //Show the number of treasures
        treasureNumLabel.setBounds(170, 225, 20, 30);
        treasureNumLabel.setFont(tfont);
        treasureNumLabel.setForeground(Color.red);
        showTreasureNum();
        note.add(treasureNumLabel);

        //Display time
        initTime();

        //Remove the fog button
        driveButton.setBounds(80, 500, 150, 40);
        driveButton.setContentAreaFilled(false);
        driveButton.setBorderPainted(false);
        driveButton.setIcon(new ImageIcon("./image/driveButton.png"));
        driveButton.addActionListener(this);
        driveButton.setFocusable(false);
        note.add(driveButton);

        //Restore fog button
        darkButton.setBounds(80, 570, 150, 40);
        darkButton.setContentAreaFilled(false);
        darkButton.setBorderPainted(false);
        darkButton.setIcon(new ImageIcon("./image/darkButton.png"));
        darkButton.addActionListener(this);
        darkButton.setFocusable(false);
        note.add(darkButton);

        //Generate fog
        createDark();

        //Generate treasure
        createTreasure();

        //Generate a maze wall
        createWall();

        driveDark();

    }

    private void initData() {
        curLevel = DataUtil.getLevel(0);
        if (curLevel == null || curLevel.getMap() == null) {
            JOptionPane.showMessageDialog(this, "data load fail!");
            System.exit(0);
        }
        map = curLevel.getMap();
        lookTime = curLevel.getObserveTime();
        treasureNum = Map.getTreasureNum(map);
    }

    //Random positioning of treasure hunters
    public void createHunter() {
        hunter = new Hunter();
        boolean pflag = true;
        while (pflag) {
            int px = (int) (Math.random() * 20);
            int py = (int) (Math.random() * 16);
            if (map[px][py] == 0) {
                hunter.setXx(px);
                hunter.setYy(py);
                hunter.setBounds(hunter.getXx() * 50, hunter.getYy() * 50, 50, 50);
                mainPanel.add(hunter);
                addKeyListener(this);
                pflag = false;
                try {
                    PlayerMove(px, py);
                } catch (Exception e) {

                }

            }
        }
    }

    //Generate treasure
    public void createTreasure() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 3) {
                    position[i][j] = new Treasure();
                    position[i][j].setXx(i);
                    position[i][j].setYy(j);
                    position[i][j].setSize(50, 50);
                    mainPanel.add(position[i][j]);
                    setPosition(position[i][j]);
                }
            }
        }
    }

    //Generate a maze wall
    public void createWall() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2) {
                    position[i][j] = new Wall();
                    position[i][j].setXx(i);
                    position[i][j].setYy(j);
                    position[i][j].setSize(50, 50);
                    mainPanel.add(position[i][j]);
                    setPosition(position[i][j]);
                }
            }
        }
    }

    //Generate fog
    public void createDark() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                dark[i][j] = new Dark();
                dark[i][j].setXx(i);
                dark[i][j].setYy(j);
                dark[i][j].setSize(50, 50);
                mainPanel.add(dark[i][j]);
                setPosition(dark[i][j]);
            }
        }
    }

    //Restore the fog
    public void restoreFog() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                dark[i][j].setVisible(true);
            }
        }
        isDriveDark = false;
        PlayerMove(1, 1);
    }

    //Get rid of the fog
    public void driveDark() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                dark[i][j].setVisible(false);
            }
        }
        isDriveDark = true;
    }

    //Find treasure
    public void findTreasure() {
        if (map[hunter.getXx()][hunter.getYy()] == 3) {
            position[hunter.getXx()][hunter.getYy()].setVisible(false);
            map[hunter.getXx()][hunter.getYy()] = 0;
            treasureNum--;
            JOptionPane.showMessageDialog(this, "Find a treasure");
            showTreasureNum();
            gameOver();
        }
    }

    public void playAgain() {
        hour = 0;
        mnt = 0;
        scd = 0;
        treasureNum = Map.getTreasureNum(map);
        initAll();
    }

    //game over
    public void gameOver() {
        if (treasureNum == 0) {
            JOptionPane.showMessageDialog(this, "Congratulations on successfully finding all the treasures\nTime spent in this game: " + time.getText());
//			int flag = JOptionPane.showConfirmDialog(this, "Let's have another round!", "news", JOptionPane.YES_NO_OPTION);
//			if (flag == JOptionPane.YES_OPTION)
//			{
//				playAgain();
//			}
//			else
//			{
//				System.exit(0);
//			}
            System.exit(0);
        }
        if (time.getText().equals("00:10:00")) {
            JOptionPane.showMessageDialog(this, "Time is up");
            System.exit(0);
        }
    }

    public void PlayerMove(int x, int y) {
        hunter.setLocation(50 * hunter.getXx(), 50 * hunter.getYy());

        if (!isDriveDark) {
            //Restoring the fog in the previous step
            dark[x][y].setVisible(true);
            if (x - 1 >= 0) {
                dark[x - 1][y].setVisible(true);
            }

            if (x + 1 < 20) {
                dark[x + 1][y].setVisible(true);
            }

            if (y + 1 < 16) {
                dark[x][y + 1].setVisible(true);
            }

            if (y - 1 >= 0) {
                dark[x][y - 1].setVisible(true);
            }

            //Next step to disperse the fog
            dark[hunter.getXx()][hunter.getYy()].setVisible(false);

            if (hunter.getXx() - 1 >= 0) {
                dark[hunter.getXx() - 1][hunter.getYy()].setVisible(false);
            }

            if (hunter.getXx() + 1 < 20) {
                dark[hunter.getXx() + 1][hunter.getYy()].setVisible(false);
            }

            if (hunter.getYy() + 1 < 16) {
                dark[hunter.getXx()][hunter.getYy() + 1].setVisible(false);
            }

            if (hunter.getYy() - 1 >= 0) {
                dark[hunter.getXx()][hunter.getYy() - 1].setVisible(false);
            }
        }
    }

    public void setPosition(Position p) {
        p.setLocation(50 * p.getXx(), 50 * p.getYy());
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == driveButton) {
            driveDark();
        }
        if (e.getSource() == darkButton) {
            restoreFog();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!(hour == 0 && mnt == 0 && scd < lookTime)) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (hunter.getYy() > 0) {
                        if (map[hunter.getXx()][hunter.getYy() - 1] == 0 || map[hunter.getXx()][hunter.getYy() - 1] == 3) {
                            int beforeX = hunter.getXx();
                            int beforeY = hunter.getYy();
                            hunter.moveYY(-1);
                            PlayerMove(beforeX, beforeY);
                            findTreasure();
                        }

                    }
                    break;

                case KeyEvent.VK_S:
                    if (hunter.getYy() < 15) {
                        if (map[hunter.getXx()][hunter.getYy() + 1] == 0 || map[hunter.getXx()][hunter.getYy() + 1] == 3) {
                            int beforeX = hunter.getXx();
                            int beforeY = hunter.getYy();
                            hunter.moveYY(1);
                            PlayerMove(beforeX, beforeY);
                            findTreasure();
                        }
                    }
                    break;

                case KeyEvent.VK_A:
                    if (hunter.getXx() > 0) {
                        if (map[hunter.getXx() - 1][hunter.getYy()] == 0 || map[hunter.getXx() - 1][hunter.getYy()] == 3) {
                            int beforeX = hunter.getXx();
                            int beforeY = hunter.getYy();
                            hunter.moveXX(-1);
                            PlayerMove(beforeX, beforeY);
                            findTreasure();
                        }
                    }
                    break;
                case KeyEvent.VK_D:
                    if (hunter.getXx() < 19) {
                        if (map[hunter.getXx() + 1][hunter.getYy()] == 0 || map[hunter.getXx() + 1][hunter.getYy()] == 3) {
                            int beforeX = hunter.getXx();
                            int beforeY = hunter.getYy();
                            hunter.moveXX(1);
                            PlayerMove(beforeX, beforeY);
                            findTreasure();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
