package com.epam.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.xml.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Food", propOrder = { "name", "price", "description", "calories" })
public class Food {
    @XmlAttribute(required = true)
    int id;

    @XmlElement(required = true)
    String name;

    @XmlElement(required = true)
    String price;

    @XmlElement(required = true)
    String description;

    @XmlElement(required = true)
    int calories;
}
