package com.traverse.data.cloud;

import com.amazonaws.services.dynamodbv2.document.*;
import com.traverse.data.Playlist;
import com.traverse.data.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONObject;
import com.traverse.data.User;

import java.util.Random;

@Component
public class PlaylistDatabase {

    @Autowired
    private DBClient dbClient;

    private String tableName;

    public PlaylistDatabase(@Value("${cloud.dynamoDB_table_name_playlists}") String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param playlistName - name of the created playlist
     * @param playlistDescription - description of the created playlist
     * @param user - User who created the playlist
     */
    public String createPlaylist(String playlistName, String playlistDescription, User user) {
        int playlistID = new Random().nextInt(100000) + 1; // playlist_id will be ranodm number between 1 and 100,000
        String userID = user.getUserID(); // get id of provided user
        user.addPlaylist(Integer.toString(playlistID)); // add created playlist by id to user's collection of created playlists
        String jsonData = new JSONObject()
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID)
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_NAME, playlistName)
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_DESCRIPTION, playlistDescription)
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_CREATED_BY, userID)
                                .toString();
        return jsonData;
    }

    /**
     * @param playlistID - id of the playlist to get from database
     */
    public String getPlaylist(String playlistID) {
        Item playlist = dbClient.getDynamoDB().getTable(tableName).getItem(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID);
        return playlist.toJSON();
    }

    /**
     * @param playlistID - id of the playlist to add to
     * @param audioID - id of audio to add to playlist
     */
    public void addToPlaylist(String playlistID, String audioID) {
        String jsonData = new JSONObject()
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID)
                                .put(Audio.DB_IDENTIFIER_AUDIO_ID, audioID)
                                .toString();
        dbClient.getDynamoDB().getTable(tableName).putItem(Item.fromJSON(jsonData)).toString();        
    }
}
