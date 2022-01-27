package de.eahjena.wi.mae.weatherapp;

/**
 * in this class we declare all strings/attributes of the petrol stations that we want to display in the app
 * every attribute gets a getter+setter method
 */
public class ContentModelClass {

    String s_name;
    String s_open;
    String s_street;
    String s_house_number;
    String s_zip;
    String s_city;
    String s_price_e5;
    String s_price_e10;
    String s_price_diesel;

    //now we need a constructor where we pass those values above when we create an object
    public ContentModelClass (String station_name, String station_open, String station_street,
                              String station_house_number, String station_zip, String station_city,
                              String station_price_e5, String station_price_e10, String station_price_diesel){
        s_name = station_name;
        s_open = station_open;
        s_street = station_street;
        s_house_number = station_house_number;
        s_zip = station_zip;
        s_city = station_city;
        s_price_e5 = station_price_e5;
        s_price_e10 = station_price_e10;
        s_price_diesel = station_price_diesel;

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
    public void setShopOpen(String s_open) {

        if ("false".equals(s_open))
        {
            s_open = "Nein";
        }
        else {
            s_open = "Ja";
        }
        this.s_open = "Zur Zeit geöffnet?" + s_open;
    }

    public String getS_street(){
        return s_street;
    }
    public void setS_street(String s_street){
        this.s_street = s_street;
    }

    public String getS_house_number(){return s_house_number;}
    public void setS_house_number(String s_house_number){this.s_house_number = s_house_number;}

    public String getS_zip() {
        return s_zip;
    }
    public void setS_zip(String s_zip) {
        this.s_zip = s_zip;
    }

    public String getS_city() {
        return s_city;
    }
    public void setS_city(String s_city) {
        this.s_city = s_city;
    }

    public String getS_price_e5() {
        return s_price_e5;
    }
    public void setS_price_e5(String s_price_e5) {
        this.s_price_e5 = s_price_e5;
    }

    public String getS_price_e10() {
        return s_price_e10;
    }
    public void setS_price_e10(String s_price_e10) {
        this.s_price_e10 = s_price_e10;
    }

    public String getS_price_diesel() {
        return s_price_diesel;
    }
    public void setS_price_diesel(String s_price_diesel) {
        this.s_price_diesel = s_price_diesel;
    }
}
