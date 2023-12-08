package com.example.ProjectExam.models.entities;

import com.example.ProjectExam.models.enums.PowerEnum;
import jakarta.persistence.*;

@Table(name="powers")
@Entity
public class PowerEntity extends BaseEntity{

    @Enumerated(value=EnumType.STRING)
    private PowerEnum name;
    @Column(columnDefinition = "varchar(255)")
    private String description;

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
        setDescription(description);

    }

    public PowerEnum getName() {
        return name;
    }

    public void setName(PowerEnum name) {

        this.name = name;
        setDescription(name);
    }

    private void setDescription(PowerEnum name) {

        String description = "";

        switch (name) {
            case FLY -> description = "Fly only in the night";
            case INVISIBILITY -> description = "Invisible in the daytime";
            case JUMP -> description = "Jump all directions, all heights";
            case TRANSFORM -> description = "Transform in all kind of animals and birds";

          }
           this.description  = description;
    }
}
