/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kiman
 */
public class StudentListManagement {
    private List<Student> studentsList;

    public StudentListManagement(List<Student> studentsList) {
        this.studentsList = studentsList;
    }
    
    public List getStudentIdList() {
        List<Integer> studentIdList = studentsList.stream().map(student -> student.getId()).collect(Collectors.toList());
        if(studentIdList.size() == 0) return null;
        return studentIdList;
    }
}
