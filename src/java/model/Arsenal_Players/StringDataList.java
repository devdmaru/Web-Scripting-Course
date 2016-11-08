package model.Arsenal_Players;

import dbUtils.FormatUtils;
import dbUtils.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StringDataList {

    public String dbError = "";
    private ArrayList<StringData> recordList = new ArrayList();

    // Default constructor just leaves the 2 data members initialized as above
    public StringDataList() {
    }

    // overloaded constructor populates the list (and possibly the dbError)
    public StringDataList(String playerNameStartsWith, DbConn dbc) {

        StringData sd = new StringData();

        System.out.println("Searching for player names that start with " + playerNameStartsWith);

        try {

            String sql = "SELECT Arsenal_Players_id, First_Name, Last_Name, Joined, Goals, Total_Apps, Player_email, "
                    + "img_url FROM Arsenal_Players WHERE First_Name LIKE ? ORDER BY Last_Name";

            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, playerNameStartsWith + "%");
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                try {
                    sd = new StringData();
                    sd.Arsenal_Players_id = FormatUtils.formatInteger(results.getObject("Arsenal_Players_id"));
                    sd.First_Name = FormatUtils.formatString(results.getObject("First_Name"));
                    sd.Last_Name = FormatUtils.formatString(results.getObject("Last_Name"));
                    sd.Joined = FormatUtils.formatInteger(results.getObject("Joined"));
                    sd.Goals = FormatUtils.formatInteger(results.getObject("Goals"));
                    sd.Total_Apps = FormatUtils.formatInteger(results.getObject("Total_Apps"));
                    sd.img_url = FormatUtils.formatString(results.getObject("img_url"));
                    sd.Player_email = FormatUtils.formatString(results.getObject("Player_email"));
                    this.recordList.add(sd);
                } catch (Exception e) {
                    sd.errorMsg = "Record Level Error in model.Arsenal_Players.StringDataList Constructor: " + e.getMessage();
                    this.recordList.add(sd);
                }
            } // while
        } catch (Exception e) {
            this.dbError = "List Level Error in model.Arsenal_Players.StringDataList Constructor: " + e.getMessage();
        }
    } // method

} // class
