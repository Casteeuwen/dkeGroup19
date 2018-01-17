import javax.swing.*;
import java.awt.*;

public class UserInterface implements Runnable {
    private JFrame frame;
    private JSlider headingSlider, pitchSlider;
    private MainPanel mPanel;

    public UserInterface() {

    }

    @Override
    public void run() {
        mPanel = new MainPanel(this);
        frame = new JFrame("3D Renderer");
        frame.setPreferredSize(new Dimension(400, 400));
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
        container.add(pitchSlider, BorderLayout.EAST);

        container.add(mPanel, BorderLayout.CENTER);
    }

    public JSlider getHeadingSlider() {
        return headingSlider;
    }

    public JSlider getPitchSlider() {
        return pitchSlider;
    }
}