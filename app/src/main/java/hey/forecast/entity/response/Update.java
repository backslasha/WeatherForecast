package hey.forecast.entity.response;

/**
 * Created by yhb on 17-12-14.
 */

public class Update {
    String loc,//: "2017-10-26 17:29",
            utc;//: "2017-10-26 09:29"

    @Override
    public String toString() {
        return "Update{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
