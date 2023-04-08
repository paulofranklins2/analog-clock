import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class AnalogClock extends JFrame {
    private Calendar cal;
    private int hour, minute, second;
    private JPanel clockPanel;
    private boolean darkMode = false;

    public AnalogClock() {
        setTitle("Analog Clock");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        clockPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();
                int cx = width / 2;
                int cy = height / 2;
                int r = Math.min(width, height) / 2 - 10;
                if (darkMode) {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, width, height);
                    g2.setColor(Color.WHITE);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, width, height);
                    g2.setColor(Color.BLACK);
                }
                g2.drawOval(cx - r, cy - r, 2 * r, 2 * r);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g2.drawString("12", cx - 10, cy - r + 20);
                g2.drawString("3", cx + r - 20, cy + 5);
                g2.drawString("6", cx - 5, cy + r - 10);
                g2.drawString("9", cx - r + 10, cy + 5);
                g2.setStroke(new BasicStroke(2.0f));
                g2.drawLine(cx, cy, (int) (cx + 0.8 * r * Math.sin(hour * 2 * Math.PI / 12)),
                        (int) (cy - 0.8 * r * Math.cos(hour * 2 * Math.PI / 12)));
                g2.drawLine(cx, cy, (int) (cx + 0.9 * r * Math.sin(minute * 2 * Math.PI / 60)),
                        (int) (cy - 0.9 * r * Math.cos(minute * 2 * Math.PI / 60)));
                g2.setStroke(new BasicStroke(0.5f));
                g2.drawLine(cx, cy, (int) (cx + 0.8 * r * Math.sin(second * 2 * Math.PI / 60)),
                        (int) (cy - 0.8 * r * Math.cos(second * 2 * Math.PI / 60)));
                // Add second hand
                g2.setStroke(new BasicStroke(1.0f));
                g2.setColor(Color.RED);
                g2.drawLine(cx, cy, (int) (cx + 0.9 * r * Math.sin(second * 2 * Math.PI / 60)),
                        (int) (cy - 0.9 * r * Math.cos(second * 2 * Math.PI / 60)));
            }
        };
        add(clockPanel);
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem darkModeMenuItem = new JMenuItem("Dark mode");
        darkModeMenuItem.addActionListener(e -> {
            darkMode = !darkMode;
            clockPanel.repaint();
        });
        optionsMenu.add(darkModeMenuItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
        Timer timer = new Timer(100, e -> {
            cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR);
            minute = cal.get(Calendar.MINUTE);
            second = cal.get(Calendar.SECOND);
            clockPanel.repaint();
        });
        timer.start();
        setVisible(true);
    }
}
