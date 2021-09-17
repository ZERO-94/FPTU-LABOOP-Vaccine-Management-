/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author kiman
 */
public class Vaccine implements Serializable{
    private int id;
    private String name;

    public Vaccine(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vaccine)) {
            return false;
        }

        Vaccine comparedFood = (Vaccine) obj;

        if (comparedFood.id != this.id) {
            return false;
        }

        return true;
    }
    
    public String toString() {
        return this.id + ", " + this.name;
    }
}
