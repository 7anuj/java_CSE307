package src.ui;

import src.dao.TrackDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchTrackForm extends JFrame {

    private JTextField searchField;
    private JButton searchBtn;
    private JTextArea resultArea;

    public SearchTrackForm() {
        setTitle("Search Song by Title");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Search field
        searchField = new JTextField();
        panel.add(searchField, BorderLayout.NORTH);

        // Search button
        searchBtn = new JButton("Search");
        panel.add(searchBtn, BorderLayout.CENTER);

        // Result display area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Action for the Search button
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                if (!searchTerm.isEmpty()) {
                    searchTrack(searchTerm);
                } else {
                    JOptionPane.showMessageDialog(SearchTrackForm.this, "Please enter a song title to search.");
                }
            }
        });

        add(panel);
    }

    // Method to perform search by song title
    private void searchTrack(String title) {
        TrackDAO trackDAO = new TrackDAO();
        try {
            ResultSet rs = trackDAO.searchTrackByTitle(title);
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Title: ").append(rs.getString("title"))
                  .append("\nAlbum ID: ").append(rs.getInt("album_id"))
                  .append("\nGenre ID: ").append(rs.getInt("genre_id"))
                  .append("\nArtist ID: ").append(rs.getInt("artist_id"))
                  .append("\nLanguage ID: ").append(rs.getInt("language_id"))
                  .append("\nRelease Year: ").append(rs.getInt("rlsyr"))
                  .append("\n\n");
            }
            if (sb.length() == 0) {
                sb.append("No tracks found with the title '" + title + "'.");
            }
            resultArea.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching for track: " + e.getMessage());
        }
    }
}

