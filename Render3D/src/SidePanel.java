import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidePanel extends JPanel {
    UserInterface ui;
    MainPanel mPanel;
    Logic logic;

    public SidePanel(UserInterface ui, MainPanel mPanel, Logic logic) {
        this.ui = ui;
        this.mPanel = mPanel;
        this.logic = logic;
        setPreferredSize(new Dimension(300, 500));
        createComponents();
    }

    public void paintComponent(Graphics g) {

    }

    private void createComponents() {
        JLabel labelA = new JLabel("A", SwingConstants.CENTER);
        JLabel labelB = new JLabel("B", SwingConstants.CENTER);
        JLabel labelC = new JLabel("C", SwingConstants.CENTER);

        JTextField numberOfA = new JTextField();
        JTextField numberOfB = new JTextField();
        JTextField numberOfC = new JTextField();

        JTextField valueOfA = new JTextField();
        JTextField valueOfB = new JTextField();
        JTextField valueOfC = new JTextField();

        super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JComboBox list = new JComboBox(new String[]{"Parcels", "Pentominoes"});

        JPanel inputOptions = new JPanel();
        inputOptions.setLayout(new GridLayout(0, 3, 10, 0));

        inputOptions.add(labelA);
        inputOptions.add(numberOfA);
        inputOptions.add(valueOfA);
        inputOptions.add(labelB);
        inputOptions.add(numberOfB);
        inputOptions.add(valueOfB);
        inputOptions.add(labelC);
        inputOptions.add(numberOfC);
        inputOptions.add(valueOfC);

        JTextField output = new JTextField(20);
        output.setPreferredSize(new Dimension(300, 300));


        JPanel buttonPanel = new JPanel();
        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton addBox = new JButton("Add Box");
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mPanel.reset();
                mPanel.repaint();
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mPanel.printBoxes();
            }
        });
        addBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mPanel.addBox(logic.addRandomBox());
                mPanel.repaint();
            }
        });
        buttonPanel.add(startBtn);
        buttonPanel.add(pauseBtn);
        buttonPanel.add(addBox);
        buttonPanel.add(reset);

        super.add(list);
        super.add(inputOptions);
        super.add(output);
        super.add(buttonPanel);
    }
}
