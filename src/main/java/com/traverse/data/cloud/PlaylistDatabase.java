package com.traverse.data.cloud;

import com.amazonaws.services.dynamodbv2.document.*;
import com.traverse.data.Playlist;
import com.traverse.data.Audio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
/**
 * Playlist class does not exist right now
 */

@Component
public class PlaylistDatabase {

    @Autowired
    private DBClient dbClient;

    private String tableName;

    public PlaylistDatabase(@Value("${cloud.dynamoDB_table_name_playlists}") String tableName) {
        this.tableName = tableName;
    }

    // POST create playlist
    public String createPlaylist(String playlistName, String playlistDescription, String userID) {
        int playlistID = new Random().nextInt(100000) + 1; // playlist_id will be ranodm number between 1 and 100,000
        String jsonData = new JSONObject()
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID)
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_NAME, playlistName)
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_CREATED_BY, userID)
                                .toString();
        return jsonData;
    }

    // GET playlist via playlist id
    public String getPlaylist(String playlistID) {
        Item playlist = dbClient.getDynamoDB().getTable(tableName).getItem(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID);
        return playlist.toJSON();
    }

    // PUT add audio to playlist
    public void addToPlaylist(String playlistID, String audioID) {
        String jsonData = new JSONObject()
                                .put(Playlist.DB_IDENTIFIER_PLAYLIST_ID, playlistID)
                                .put(Audio.DB_IDENTIFIER_AUDIO_ID, audioID)
                                .toString();
        // return dbClient.getDynamoDB().getTable(tableName).putItem(Item.fromJSON(jsonData)).toString();
        dbClient.getDynamoDB().getTable(tableName).putItem(Item.fromJSON(jsonData)).toString();        
    }
}
