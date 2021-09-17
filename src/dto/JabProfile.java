/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author kiman
 */
public class JabProfile implements Serializable{
    private LocalDate date;
    private String place;

    public JabProfile() {
        this.date = null;
        this.place = null;
    }
    
    public JabProfile(LocalDate date, String place) {
        this.date = date;
        if(place.equals("")) this.place = null;
        else this.place = place;
    }

    public JabProfile(String date, String place) {
        this.place = place;
        if(date != null) this.date = LocalDate.parse(date);
        else this.date = null;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    public String toString() {
        return this.date + ", " + this.place;
    }
}
