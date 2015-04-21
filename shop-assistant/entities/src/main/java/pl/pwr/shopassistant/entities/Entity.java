package pl.pwr.shopassistant.entities;

public interface Entity {
    public void setId(Integer id);
    public Integer getId();

    public Boolean isNew();
}