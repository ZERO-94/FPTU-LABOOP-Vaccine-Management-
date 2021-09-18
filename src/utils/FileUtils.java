package utils;

import dto.Injection;
import dto.JabProfile;
import dto.Student;
import dto.Vaccine;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiman
 */
public class FileUtils {

    public static void writeInjectionsToFileText(String filename, List<Injection> data) throws Exception {
        PrintWriter w = null;
        try {
            w = new PrintWriter(filename);
            for (Injection d : data) {
                w.println(d);
            }
        } finally {
            if (w != null) {
                w.close();
            }
        }
    }

    public static List<Student> readStudentsFromFileText(String filename) throws FileNotFoundException, IOException {
        FileReader f = null;
        BufferedReader bf = null;
        try {
            f = new FileReader(filename);
            bf = new BufferedReader(f);
            List<Student> list = new ArrayList<>();
            while (bf.ready()) {
                String s = bf.readLine();
                String[] tmp = s.split(",");
                if (tmp.length == 2) { //Each line will have format Student-Id, Student-name -> wrong format -> won't read
                    int studentId = new Integer(tmp[0].trim()); //trim to eliminate all redundent space
                    String studentName = tmp[1].trim(); //trim to eliminate all redundent space
                    Student d = new Student(studentId, studentName);
                    list.add(d);
                }
            }
            return list;
        } finally {
            if (bf != null) {
                bf.close();
            }
            if (f != null) {
                f.close();
            }
        }
    }

    public static List<Vaccine> readVaccinesFromFileText(String filename) throws FileNotFoundException, IOException {
        FileReader f = null;
        BufferedReader bf = null;
        try {
            f = new FileReader(filename);
            bf = new BufferedReader(f);
            List<Vaccine> list = new ArrayList<>();
            while (bf.ready()) {
                String s = bf.readLine();
                String[] tmp = s.split(",");
                if (tmp.length == 2) { //Each line will have format Vaccine-Id, Vaccine-name -> wrong format -> won't read
                    int vaccineId = new Integer(tmp[0].trim()); //trim to eliminate all redundent space
                    String vaccineName = tmp[1].trim(); //trim to eliminate all redundent space
                    Vaccine d = new Vaccine(vaccineId, vaccineName);
                    list.add(d);
                }
            }
            return list;
        } finally {
            if (bf != null) {
                bf.close();
            }
            if (f != null) {
                f.close();
            }
        }

    }

    private static JabProfile convertJabProfileFormat(String date, String place, boolean isFristJab) {
        if (isFristJab == true && date.equals("null") && place.equals("null")) { //first jab must exist
            return null;
        }

        if (date.equals("null") && place.equals("null")) {
            return new JabProfile();
        }

        return new JabProfile(date, place);
    }

    public static List<Injection> readInjectionsFromFileText(String filename) throws FileNotFoundException, IOException {
        FileReader f = null;
        BufferedReader bf = null;
        try {
            f = new FileReader(filename);
            bf = new BufferedReader(f);
            ArrayList<Injection> list = new ArrayList<>();
            while (bf.ready()) {
                String s = bf.readLine();
                String[] tmp = s.split(",");
                if (tmp.length == 7) {//file format is injectionId, studentId, vaccineId, firstDate, firstPlace, secondDate, secondPlace -> wrong format -> won't read
                    JabProfile firstJab = null;
                    JabProfile secondJab = null;

                    int injectionId = new Integer(tmp[0].trim());
                    int studentId = new Integer(tmp[1].trim());
                    int vaccineId = new Integer(tmp[2].trim());
                    String firstDate = tmp[3].trim();
                    String firstPlace = tmp[4].trim();
                    if (firstDate.equals("null") ^ firstPlace.equals("null")) {
                        continue; //both information must exist
                    }
                    String secondDate = tmp[5].trim();
                    String secondPlace = tmp[6].trim();
                    if (secondDate.equals("null") ^ secondPlace.equals("null")) {
                        continue; //both information must exist
                    }
                    firstJab = convertJabProfileFormat(firstDate, firstPlace, true);
                    if (firstJab == null) {
                        continue; //first jab can't be null
                    }
                    secondJab = convertJabProfileFormat(secondDate, secondPlace, false);

                    Injection d = new Injection(injectionId, studentId, vaccineId, firstJab, secondJab);
                    list.add(d);
                }
            }
            return list;
        } finally {
            if (bf != null) {
                bf.close();
            }
            if (f != null) {
                f.close();
            }
        }
    }
}
