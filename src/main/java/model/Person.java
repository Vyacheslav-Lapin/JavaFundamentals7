package model;

import com.epam.jdbc.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@FieldNameConstants
@AllArgsConstructor
public class Person implements Identifiable<Person> {

  Long id;
  String firstName;
  String lastName;
  boolean permission;
  LocalDate dob;
  String email;
  String password;
  String address;
  String telephone;

  public Person(String firstName, String lastName, boolean permission, LocalDate dob, String email, String password, String address, String telephone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.permission = permission;
    this.dob = dob;
    this.email = email;
    this.password = password;
    this.address = address;
    this.telephone = telephone;
  }
}
