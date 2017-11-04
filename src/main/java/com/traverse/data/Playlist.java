package com.traverse.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Playlist {

    private static final Log logger = LogFactory.getLog(User.class);

    public static final String
            DB_IDENTIFIER_PLAYLIST_ID = "playlist_id",
            DB_IDENTIFIER_PLAYLIST_NAME= "playlist_name",
            DB_IDENTIFIER_PLAYLIST_DESCRIPTION = "description",
            DB_IDENTIFIER_PLAYLIST_CREATED_BY = "user_id";


    private String playlistName;
    private String playlistID;
    private String playlistDescription;
    private String userID;

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setPlaylistID(String id) {
        this.playlistID = id;
    }

    public void setPlaylistDescription(String description) {
        this.playlistDescription = description;
    }

    public void setPlaylistOwner(String userID) {
        this.userID = userID;
    }

    public String toJson(){
        return new JSONObject()
                .put(DB_IDENTIFIER_PLAYLIST_ID, playlistName)
                .put(DB_IDENTIFIER_PLAYLIST_NAME, playlistID)
                .put(DB_IDENTIFIER_PLAYLIST_DESCRIPTION, playlistDescription)
                .put(DB_IDENTIFIER_PLAYLIST_CREATED_BY, userID)
                .toString();
    }
}
