package com.addressbook.domain.builder;

import com.addressbook.domain.enums.AddressType;
import com.addressbook.domain.exceptions.BeanValidationException;
import com.addressbook.domain.model.Address;
import com.addressbook.domain.model.Country;
import com.addressbook.domain.model.User;
import com.addressbook.domain.model.ZipCode;
import com.addressbook.domain.validation.BeanValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserBuilderTest {

    private BeanValidator validator;

    @BeforeEach
    void init() {
        validator = new BeanValidator();
    }

    @AfterEach
    void terminate() {
        validator.close();
    }


    @Test
    void should_build_a_user_with_id() {

        User user = User.aNew().withId(1).get();
        assertEquals(1, user.getId());

    }

    @Test
    void should_build_a_user_with_first_name() {

        User user = User.aNew().withFirstName("Mike").get();
        assertEquals("Mike", user.getFirstName());

    }

    @Test
    void should_build_a_user_with_last_name() {

        User user = User.aNew().withLastName("Tyson").get();
        assertEquals("Tyson", user.getLastName());

    }

    @Test
    void should_build_a_user_with_email_address() {

        User user = User.aNew().withEmailAddress("tyson.mike@boxing.com").get();
        assertEquals("tyson.mike@boxing.com", user.getEmailAddress());

    }

    @Test
    void should_build_a_user_with_birth_date() {

        User user = User.aNew().withBirthDate(LocalDate.of(1969, 3, 3)).get();
        assertEquals(LocalDate.of(1969, 3, 3), user.getBirthDate());

    }

    @Test
    void should_build_a_user_with_phone_number() {

        User user = User.aNew().withPhoneNumber("5061534466").get();
        assertEquals("5061534466", user.getPhoneNumber());

    }

    @Test
    void should_build_a_user_with_address() {
        //todo object create with builder method
        Set<Address> addresses = new HashSet<>();
        Set<User> users = new HashSet<>();
        users.add(new User(10, "Jack", "Wilder", "jack@gmail.com", LocalDate.of(1994, 11, 2), "5062333444", addresses));
        Country turkey = new Country(1, "Turkey", "TR", "90", new HashSet<>());
        Address address = new Address(1, "My Home Address", AddressType.HOME,
                new ZipCode(12, "3256", "Istanbul", "Pendik", "Yenisehir Neighborhood", "Marmara", turkey),
                "Brown Street", "Go street ahead follow this rote there is on the left", users);
        addresses.add(address);
        User user = User.aNew().withAddress(addresses).get();
        assertEquals(addresses, user.getAddresses());

    }

    @Test
    void should_throw_BeanValidationException_if_no_last_name_specified() {

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5062333444")
                .validateAndGet(validator));

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName(null)
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5062333444")
                .validateAndGet(validator));

    }

    @Test
    void should_throw_BeanValidationException_if_no_first_name_specified() {

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName(null)
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5062333444")
                .validateAndGet(validator));

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5062333444")
                .validateAndGet(validator));

    }

    @Test
    void should_throw_BeanValidationException_if_no_address_specified() {

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(null)
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5062333444")
                .validateAndGet(validator));

    }

    @Test
    void should_throw_BeanValidationException_if_no_phone_number_specified() {

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("455646546546550623444")
                .validateAndGet(validator));

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("null")
                .validateAndGet(validator));

        assertThrows(BeanValidationException.class, () -> User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber(null)
                .validateAndGet(validator));


    }

    @Test
    void should_build_all_args() {
        assertNotNull(User.aNew()
                .withId(10)
                .withLastName("Jones")
                .withFirstName("Jack")
                .withBirthDate(LocalDate.of(1994, 11, 2))
                .withAddress(new HashSet<>())
                .withEmailAddress("jack@gmail.com")
                .withPhoneNumber("5061326565")
                .validateAndGet(validator));
    }


}
