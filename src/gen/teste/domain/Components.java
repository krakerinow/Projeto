package teste.domain;
// Generated 25-Feb-2021 18:46:49 by Hibernate Tools 3.2.0.b9


import java.util.Date;

/**
 * Components generated by hbm2java
 */
public abstract class Components  implements java.io.Serializable {


     private long id;
     private Date updateDate;
     private Section section;

    public Components() {
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
    public Section getSection() {
        return this.section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }




}


