package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlType(name = "main.entity.Author")
public class Author {
    private Long id;

    private String name;

    private String surname;

    private Date birthday;
}
