package tumblrr.utd.com.stepcounter.retrofit;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("capture_external_data")
    @FormUrlEncoded
    Observable<Atributte> savePost(@Field("id_player") int id_player,
                                   @Field("id_sensor_endpoint") int id_sensor_endpoint,
                                   @Field("data_changes")  String data_changes,
                                   @Field("watch_parameters")String  watch_parameters);
/*    fun registerAttribute(@Field("id_player") id_player: Int,
                          @Field("nameat") nameat: String,
                          @Field("namecategory") namecategory: String,
                          @Field("data") data: Int,
                          @Field("data_type") data_type: String,
                          @Field("input_source") input_source: String,
                          @Field("date_time") date_time: String): Observable1<String>*/
}