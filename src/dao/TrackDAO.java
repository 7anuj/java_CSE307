package src.dao;

import src.db.DBConnection;
import java.sql.*;

public class TrackDAO {

    public void addTrack(String title, int albumId, int genreId, int artistId, int languageId, int year) {
        String sql = "INSERT INTO Track (title, album_id, genre_id, artist_id, language_id, rlsyr) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, title);
            pst.setInt(2, albumId);
            pst.setInt(3, genreId);
            pst.setInt(4, artistId);
            pst.setInt(5, languageId);
            pst.setInt(6, year);

            pst.executeUpdate();
            System.out.println("Track added!");

        } catch (SQLException e) {
            System.out.println("Error adding track: " + e.getMessage());
        }
    }

    public ResultSet getAllTracks() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            return stmt.executeQuery("SELECT * FROM Track");
        } catch (SQLException e) {
            System.out.println("Error fetching tracks: " + e.getMessage());
            return null;
        }
    }

    public void deleteTrack(String title) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement("DELETE FROM Track WHERE title = ?")) {
            pst.setString(1, title);
            pst.executeUpdate();
            System.out.println("Track deleted!");
        } catch (SQLException e) {
            System.out.println("Error deleting track: " + e.getMessage());
        }
    }

    // New method to search for a song by its title
    public ResultSet searchTrackByTitle(String title) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM Track WHERE title = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, title);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
        
    
}
