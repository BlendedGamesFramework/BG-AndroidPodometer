package tumblrr.utd.com.stepcounter.retrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Atributte{

    @SerializedName("id_player")
    @Expose
    private int id_player;
    @SerializedName("id_sensor_endpoint")
    @Expose
    private int id_sensor_endpoint;
    @SerializedName("data_changes")
    @Expose
    private Integer[] data_changes;
    @SerializedName("watch_parameters")
    @Expose
    private String[][] watch_parameters;

    public Atributte(int id_p, int id_s, Integer[] data_c, String[][] watch_p){
          /*
            var id_player = req.body.id_player
            var id_sensor_endpoint = req.body.id_sensor_endpoint
            // [2,20,4,0,0]
            var data_changes = req.body.data_changes
            // Ej: [['chess_blitz','records',win'], ['elo'],['puzzle_challenge','record'],['puzzle_rush'],['chess_rapid','record','win']]
            var watch_parameters = req.body.watch_parameters

        */
         this.id_player = id_p;
         this.id_sensor_endpoint = id_s;
         this.data_changes = data_c;
         this.watch_parameters = watch_p;
    }

    public int getId_player() {
        return id_player;
    }

    public void setId_player(int id_player) {
        this.id_player = id_player;
    }

    public Integer[] getData_changes() {
        return data_changes;
    }

    public void setNameat(Integer[] data_changes) {
        this.data_changes = data_changes;
    }

    public String[][] getWatch_parameters() {
        return watch_parameters;
    }

    public void setWatch_parameters(String[][] watch_parameters) {
        this.watch_parameters = watch_parameters;
    }


}