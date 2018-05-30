package edu.iis.mto.blog.domain.repository;

import java.util.List;

import edu.iis.mto.blog.UserBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User userJan;
    private User userKlaudia;

    @Before
    public void setUp() {
        userJan = new UserBuilder().build();

        userKlaudia = new UserBuilder().withFirstName("Klaudia")
                            .withLastName("Best").withEmail("klaudia@domain.com").build();
    }

    @Test
    public void shouldFindOneUserIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(userJan);
        List<User> users = repository.findAll();

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {
        User persistedUser = repository.save(userJan);

        Assert.assertThat(persistedUser.getId(), Matchers.notNullValue());
    }

    @Test
    public void findOneUserByEmailWhenOneMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", userJan.getEmail());

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
    }

    @Test
    public void findTwoUsersByEmailWhenTwoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "domain");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(userKlaudia.getEmail()));
    }

    @Test
    public void findNoUsersByEmailWhenNoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "p.lodz.pl");

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void findOneUserByFirstNameWhenOneMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("j", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
    }

    @Test
    public void findTwoUsersByFirstNameWhenTwoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("a", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(userKlaudia.getEmail()));
    }

    @Test
    public void findNoUsersByFirstNameWhenNoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("s", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void findOneUserByLastNameWhenOneMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "tt", "null");

        Assert.assertThat(users, Matchers.hasSize(1));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
    }

    @Test
    public void findTwoUsersByLastNameWhenTwoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "be", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
        Assert.assertThat(users.get(0).getEmail(), Matchers.equalTo(userJan.getEmail()));
        Assert.assertThat(users.get(1).getEmail(), Matchers.equalTo(userKlaudia.getEmail()));
    }

    @Test
    public void findNoUsersByLastNameWhenNoMatchingInRepo() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "worse", "null");

        Assert.assertThat(users, Matchers.hasSize(0));
    }

    @Test
    public void findUserIgnoresCaseForFirstName() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("A", "null", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
    }

    @Test
    public void findUserIgnoresCaseForLastName() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "bE", "null");

        Assert.assertThat(users, Matchers.hasSize(2));
    }

    @Test
    public void findUserIgnoresCaseForEmail() {
        repository.save(userJan);
        repository.save(userKlaudia);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase
                ("null", "null", "dOMAin");

        Assert.assertThat(users, Matchers.hasSize(2));
    }
}
