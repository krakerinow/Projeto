package teste.domain;
// Generated 27-Feb-2021 06:34:51 by Hibernate Tools 3.2.0.b9


import java.util.Date;

/**
 * Components generated by hbm2java
 */
public abstract class Components  implements java.io.Serializable {


     private long id;
     private Date updateDate;
     private String path;
     private String text;
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
    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    public String getText() {
        return this.text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    public Section getSection() {
        return this.section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }




}


