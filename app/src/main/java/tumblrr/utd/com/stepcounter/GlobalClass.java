package tumblrr.utd.com.stepcounter;

import android.app.Application;

public class GlobalClass extends Application {
    private int id_player;

    public int getId_player(){
        return id_player;
    }
    public void setId_player(int id){
        id_player = id;
    }
}
