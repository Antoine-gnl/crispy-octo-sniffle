package ch.epfl.esl.commons;

import android.content.res.Resources;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;

public class Datas {
    private static final String TAG = "Profile";

    public String accx;
    public String accy;
    public String accz;

    public Datas() {
        // Empty constructor
    }

    public Datas(DataMap map, Resources res, GoogleApiClient mGoogleApiClient) {
        // Construct instance from the datamap
        accx = map.getString("accx");
        accy = map.getString("accy");
        accz = map.getString("accz");
    }

    public DataMap toDataMap() {
        DataMap map = new DataMap();
        map.putString("accx", accx);
        map.putString("accy", accy);
        map.putString("accz", accz);
        return map;
    }
}