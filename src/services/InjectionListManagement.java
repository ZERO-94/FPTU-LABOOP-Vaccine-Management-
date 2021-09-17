package services;

import dto.Injection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InjectionListManagement {

    private List<Injection> injectionsCollection = new ArrayList<>();

    public InjectionListManagement() {
        this.injectionsCollection = new ArrayList<>();
    } 
    
    public InjectionListManagement(List<Injection> injectionsCollection) {
        this.injectionsCollection = injectionsCollection;
    } 
    
    public List getInjectionsCollection() {
        List<Injection> newInjectionsCollection = injectionsCollection.stream().collect(Collectors.toList());
        return newInjectionsCollection;
    }

    public boolean isEmpty() {
        return injectionsCollection.isEmpty();
    }

    public boolean addInjection(Injection injection) {
        if (injectionsCollection.contains(injection)) {
            return false;
        }

        return injectionsCollection.add(injection);
    }

    public Injection searchInjectionById(int injectionId) {
        Injection neededInjection = injectionsCollection.stream()
                .filter(injection -> injection.getId() == injectionId)
                .reduce(null, (acc, injection) -> acc = injection);

        return neededInjection;
    }
    
    public Injection searchInjectionByStudentId(int studentId) {
        Injection neededInjection = injectionsCollection.stream()
                .filter(injection -> injection.getStudentId() == studentId)
                .reduce(null, (acc, injection) -> acc = injection);

        return neededInjection;
    }

    public boolean removeInjection(Injection injection) {
        return injectionsCollection.remove(injection);
    }

    public boolean removeInjection(int removedInjectionId) {
        Injection removedInjection = searchInjectionById(removedInjectionId);
        return injectionsCollection.remove(removedInjection);
    }

    @Override
    public String toString() {
        String output = injectionsCollection.stream()
                .map(injection -> injection.toString())
                .reduce("", (acc, injectionString) -> acc + injectionString + "\n");
        return output;
    }
    
    public int getMaxInjectionId() {
        int max = 0;
        for(Injection injection : injectionsCollection) 
            if(injection.getId() > max) max = injection.getId();
        return max;
    }
}
