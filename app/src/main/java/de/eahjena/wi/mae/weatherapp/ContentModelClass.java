package de.eahjena.wi.mae.weatherapp;

public class ContentModelClass {

    String s_name;
    String s_open;

    //now we need a constructor where we pass those values above when we create an object
    public ContentModelClass (String station_name, String station_open){
        s_name = station_name;
        s_open = station_open;
    }

    public ContentModelClass() {
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_open() {
        return s_open;
    }

    public void setS_open(String s_open) {
        if (s_open == "false")
        {
            s_open = "Zur Zeit geöffnet? Nein";
        }
        else {
            s_open = "Zur Zeit geöffnet? Ja";
        }
        this.s_open = s_open;
    }
}
