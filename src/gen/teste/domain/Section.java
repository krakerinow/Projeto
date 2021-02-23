package teste.domain;
// Generated 23-Feb-2021 02:53:43 by Hibernate Tools 3.2.0.b9


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Section generated by hbm2java
 */
public abstract class Section  implements java.io.Serializable {


     private long id;
     private Date updateDate;
     private String title;
     private Page page;
     private Set<ComponentsImpl> components = new HashSet<ComponentsImpl>(0);

    public Section() {
    }

   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public Date getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public Page getPage() {
        return this.page;
    }
    
    public void setPage(Page page) {
        this.page = page;
    }
    public Set<ComponentsImpl> getComponents() {
        return this.components;
    }
    
    public void setComponents(Set<ComponentsImpl> components) {
        this.components = components;
    }




}

