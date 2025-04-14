package ui;

import dao.TrackDAO;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("🎵 Music Files Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton addBtn = new JButton("Add Track");
        JButton viewBtn = new JButton("View Tracks");
        JButton delBtn = new JButton("Delete Track");

        addBtn.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Enter Title");
            int album = Integer.parseInt(JOptionPane.showInputDialog("Enter Album ID"));
            int genre = Integer.parseInt(JOptionPane.showInputDialog("Enter Genre ID"));
            int artist = Integer.parseInt(JOptionPane.showInputDialog("Enter Artist ID"));
            int lang = Integer.parseInt(JOptionPane.showInputDialog("Enter Language ID"));
            int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Release Year"));

            new TrackDAO().addTrack(title, album, genre, artist, lang, year);
        });

        viewBtn.addActionListener(e -> {
            try {
                var rs = new TrackDAO().getAllTracks();
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append(rs.getString("title")).append(" | ")
                      .append(rs.getInt("rlsyr")).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        delBtn.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Enter Track Title to Delete");
            new TrackDAO().deleteTrack(title);
        });

        add(addBtn);
        add(viewBtn);
        add(delBtn);
    }
}

