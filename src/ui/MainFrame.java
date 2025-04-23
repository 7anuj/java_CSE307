package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import src.dao.TrackDAO;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("🎵 Music Files Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image
        ImageIcon bgIcon = new ImageIcon("src/assets/bg.jpg"); // Add your image in assets folder
        Image bgImage = bgIcon.getImage();
        
        // Custom JPanel to paint background
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setOpaque(false);

        // Title label
        JLabel titleLabel = new JLabel("🎶 Music Files Management System");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Buttons
        JButton addBtn = createStyledButton("➕ Add Track");
        JButton viewBtn = createStyledButton("📄 View Tracks");
        JButton delBtn = createStyledButton("❌ Delete Track");
        JButton searchBtn = createStyledButton("🔍 Search Track"); // Search button

        // Button actions
        addBtn.addActionListener(e -> handleAddTrack());
        viewBtn.addActionListener(e -> handleViewTracks());
        delBtn.addActionListener(e -> handleDeleteTrack());
        searchBtn.addActionListener(e -> handleSearchTrack()); // Action for Search Track button

        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(addBtn);
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(viewBtn);
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(delBtn);
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(searchBtn); // Add Search Track button

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(40, 167, 69)); // Green color for buttons
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void handleAddTrack() {
        try {
            String title = JOptionPane.showInputDialog("Enter Title");
            int album = Integer.parseInt(JOptionPane.showInputDialog("Enter Album ID"));
            int genre = Integer.parseInt(JOptionPane.showInputDialog("Enter Genre ID"));
            int artist = Integer.parseInt(JOptionPane.showInputDialog("Enter Artist ID"));
            int lang = Integer.parseInt(JOptionPane.showInputDialog("Enter Language ID"));
            int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Release Year"));
            new TrackDAO().addTrack(title, album, genre, artist, lang, year);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void handleViewTracks() {
        try {
            var rs = new TrackDAO().getAllTracks();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Title: ").append(rs.getString("title")).append(" | ")
                        .append("Album ID: ").append(rs.getInt("album_id")).append(" | ")
                        .append("Genre ID: ").append(rs.getInt("genre_id")).append(" | ")
                        .append("Artist ID: ").append(rs.getInt("artist_id")).append(" | ")
                        .append("Language ID: ").append(rs.getInt("language_id")).append(" | ")
                        .append("Release Year: ").append(rs.getInt("rlsyr")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No tracks found.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching tracks.");
        }
    }

    private void handleDeleteTrack() {
        String title = JOptionPane.showInputDialog("Enter Track Title to Delete");
        new TrackDAO().deleteTrack(title);
    }

    private void handleSearchTrack() {
        String title = JOptionPane.showInputDialog("Enter Track Title to Search");
        try {
            var rs = new TrackDAO().searchTrackByTitle(title);
            if (rs.next()) {
                String trackInfo = "Title: " + rs.getString("title") + " | " +
                                   "Album ID: " + rs.getInt("album_id") + " | " +
                                   "Genre ID: " + rs.getInt("genre_id") + " | " +
                                   "Artist ID: " + rs.getInt("artist_id") + " | " +
                                   "Language ID: " + rs.getInt("language_id") + " | " +
                                   "Release Year: " + rs.getInt("rlsyr");
                JOptionPane.showMessageDialog(this, trackInfo);
            } else {
                JOptionPane.showMessageDialog(this, "Track not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching track.");
        }
    }
}
