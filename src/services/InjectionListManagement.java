package services;

import dto.Injection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InjectionListManagement {

    private List<Injection> injectionsCollection;

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

    public boolean isInjectionCollectionEmpty() {
        return injectionsCollection.isEmpty();
    }

    public boolean addInjection(Injection injection) {
        if (injectionsCollection.contains(injection)) {
            return false;
        }

        return injectionsCollection.add(injection);
    }

    public Injection searchInjectionById(int injectionId) {
        int indexOfNeededInjection = injectionsCollection.indexOf(new Injection(injectionId));

        if(indexOfNeededInjection < 0) return null;
        
        return injectionsCollection.get(indexOfNeededInjection);
    }
    
    public Injection searchInjectionByStudentId(int studentId) {
        for(Injection injection : injectionsCollection)
            if(injection.getStudentId() == studentId)
                return injection;

        return null;
    }

    public boolean removeInjection(Injection injection) {
        return injectionsCollection.remove(injection);
    }

    public boolean removeInjection(int removedInjectionId) {
        return injectionsCollection.remove(new Injection(removedInjectionId));
    }

    @Override
    public String toString() {
        String output = injectionsCollection.stream()
                .map(injection -> injection.toString())
                .reduce("", (acc, injectionString) -> acc + injectionString + "\n");
        return output;
    }
}
