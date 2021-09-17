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
    
    public static void writeTextInjections(String filename, List<Injection> data) throws Exception {
        PrintWriter w = new PrintWriter(filename);
        for(Injection d : data) {
            w.println(d);
        }
        w.close();
    }
    
    public static List<Student> readTextStudents(String filename) throws FileNotFoundException, IOException{
        FileReader f = new FileReader(filename);
        BufferedReader bf = new BufferedReader(f);
        List<Student> list = new ArrayList<>();
        while(bf.ready()) {
            String s = bf.readLine();
            String[] tmp = s.split(",");
            if(tmp.length==2) {
               Student d = new Student(new Integer(tmp[0].trim()),tmp[1].trim());
                list.add(d);
            }
        }
        f.close(); bf.close();
        return list;
    }
    
    public static List<Vaccine> readTextVaccines(String filename) throws FileNotFoundException, IOException{
        FileReader f = new FileReader(filename);
        BufferedReader bf = new BufferedReader(f);
        List<Vaccine> list = new ArrayList<>();
        while(bf.ready()) {
            String s = bf.readLine();
            String[] tmp = s.split(",");
            if(tmp.length==2) {
                
                Vaccine d = new Vaccine(new Integer(tmp[0].trim()),tmp[1].trim());
                list.add(d);
            }
        }
        f.close(); bf.close();
        return list;
    }
    
    public static List<Injection> readTextInjections(String filename) throws FileNotFoundException, IOException{
        FileReader f = new FileReader(filename);
        BufferedReader bf = new BufferedReader(f);
        ArrayList<Injection> list = new ArrayList<>();
        while(bf.ready()) {
            String s = bf.readLine();
            String[] tmp = s.split(",");
            if(tmp.length==7) {
                JabProfile firstJab = null;
                JabProfile secondJab = null;
                
                if(tmp[3].trim().equals("null") && tmp[4].trim().equals("null")) {
                firstJab = new JabProfile();
                } else firstJab = new JabProfile(tmp[3].trim(), tmp[4].trim());
                
                if(tmp[5].trim().equals("null") && tmp[6].trim().equals("null")) {
                secondJab = new JabProfile();
                } else secondJab = new JabProfile(tmp[5].trim(), tmp[6].trim());
                
                Injection d = new Injection(new Integer(tmp[0].trim()),new Integer(tmp[1].trim()),new Integer(tmp[2].trim()), firstJab, secondJab);
                list.add(d);
            }
        }
        f.close(); bf.close();
        return list;
    }
}
