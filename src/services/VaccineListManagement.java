/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.Vaccine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kiman
 */
public class VaccineListManagement {
    private List<Vaccine> vaccinesList = new ArrayList<>();
    
    public VaccineListManagement(List<Vaccine> vaccinesList) {
        this.vaccinesList = vaccinesList;
    }
    
    public List getVaccineIdList() {
        List<Integer> vaccineIdList = vaccinesList.stream().map(vaccine -> vaccine.getId()).collect(Collectors.toList());
        if(vaccineIdList.size() == 0) return null;
        return vaccineIdList;
    }
}
