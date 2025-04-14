package co.com.bancolombia.model.franchise.model;



import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;

    public Franchise(String id, String name,List<Branch> branches) {
        this.id = id;
        this.name = name;
        this.branches=branches !=null ? branches : new ArrayList<>();
    }

    public Franchise() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
