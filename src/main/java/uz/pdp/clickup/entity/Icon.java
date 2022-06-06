package uz.pdp.clickup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickup.entity.template.AbstractLongEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Icon extends AbstractLongEntity {

    public Icon(Attachment attachment, String color) {
        this.attachment = attachment;
        this.color = color;
    }

    @OneToOne
    private Attachment attachment;

    @Column(nullable = false)
    private String color;

    private String initialLetter;

    @PrePersist //Malumotlar bazasiga qo'shishdan oldin
    @PreUpdate // malumotlar bazasida tahrirlashdan oldin
    public void setInitialLetterMyMethod(){
        initialLetter = attachment.getName().substring(0, 1);
    }

}
