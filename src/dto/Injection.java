package dto;

import java.io.Serializable;

/**
 *
 * @author kiman
 */
public class Injection implements Serializable {

    private int id;
    private int studentId;
    private int vaccineId;
    private JabProfile firstJab;
    private JabProfile secondJab;

    public Injection(int id, int studentId, int vaccineId, JabProfile firstJab, JabProfile secondJab) {
        this.id = id;
        this.studentId = studentId;
        this.vaccineId = vaccineId;
        this.firstJab = firstJab;
        this.secondJab = secondJab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public JabProfile getFirstJab() {
        return firstJab;
    }

    public void setFirstJab(JabProfile firstJab) throws IllegalArgumentException {
        if (firstJab == null) {
            throw new IllegalArgumentException("invalid first jab");
        }
        this.firstJab = firstJab;
    }

    public JabProfile getSecondJab() {
        return secondJab;
    }

    public void setSecondJab(JabProfile secondJab) throws IllegalArgumentException {
        if (this.firstJab == null) {
            throw new IllegalArgumentException("The first jab must be set first!");
        }
        if (secondJab != null) {
            if (secondJab.getDate() == null && secondJab.getPlace() == null) {
                
                this.secondJab = secondJab;
                return;
            }
            if (secondJab.getDate().minusWeeks(4).isBefore(this.firstJab.getDate()) || secondJab.getDate().minusWeeks(12).isAfter(this.firstJab.getDate())) 
                throw new IllegalArgumentException("The date of second jab must be between 4-12 weeks");
        }
        this.secondJab = secondJab;

    }

    public String toString() {
        return this.id + ", " + this.studentId + ", " + this.vaccineId + ", " + this.firstJab + ", " + this.secondJab;
    }
}
