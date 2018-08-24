package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book implements Comparable {

    private Long id;

    private String name;

    private int numberOfPages;

    private Long authorId;


    @Override
    public int compareTo(Object o) {
        Book b = (Book) o;
        return this.getName().compareTo(b.getName());
    }
}
