package utils;

import entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Getter
@Setter
public class Books {

    @XmlElement(name = "book")
    private List<Book> books;

    public Books() {
        books = new ArrayList<>();
    }


}
