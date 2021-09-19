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
public class JabProfile implements Serializable {

    private LocalDate date;
    private String place;

    public JabProfile() {
        this.date = null;
        this.place = null;
    }

    public JabProfile(LocalDate date, String place) throws IllegalArgumentException {
        if (place != null && place.trim().equals("")) {
            place = null;
        }
        if (date == null ^ place == null) {
            throw new IllegalArgumentException("Invalid jab profile information");
        }
        this.date = date;
        this.place = place;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String toString() {
        return this.date + ", " + this.place;
    }
}
