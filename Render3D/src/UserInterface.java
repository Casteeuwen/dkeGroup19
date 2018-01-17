import javax.swing.*;
import java.awt.*;

public class UserInterface implements Runnable {
    private JFrame frame;
    private JSlider headingSlider, pitchSlider;
    private MainPanel mPanel;
    private SidePanel sPanel;
    private Logic logic;

    public UserInterface() {

    }

    @Override
    public void run() {
        logic = new Logic();
        mPanel = new MainPanel(this);
        sPanel = new SidePanel(this, mPanel, logic);
        frame = new JFrame("3D Renderer");
        frame.setPreferredSize(new Dimension(800, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.setLayout(new BorderLayout());

        headingSlider = new JSlider(0, 360, 180);
        pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);

        headingSlider.addChangeListener(e -> mPanel.repaint());
        pitchSlider.addChangeListener(e -> mPanel.repaint());

        container.add(headingSlider, BorderLayout.SOUTH);
        container.add(pitchSlider, BorderLayout.WEST);

        container.add(mPanel, BorderLayout.CENTER);
        container.add(sPanel, BorderLayout.EAST);
    }

    public JSlider getHeadingSlider() {
        return headingSlider;
    }

    public JSlider getPitchSlider() {
        return pitchSlider;
    }

    public JFrame getFrame() {
        return frame;
    }
}
