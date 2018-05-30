package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.api.request.PostRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.DataMapper;
import edu.iis.mto.blog.services.BlogService;

import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository blogPostRepository;

    @Autowired
    DataMapper dataMapper;

    @Autowired
    BlogService blogService;

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test//(expected = DomainError.class)
    public void test(){
        Long postOwnerId = 1L;
        Long likingUserId = 2L;
        Long postId = 3L;

        User postOwner = new User();
        postOwner.setId(postOwnerId);
//        postOwner.setFirstName("John");
//        postOwner.setEmail("john@domain.com");

        User likingUser = new User();
        likingUser.setId(likingUserId);
//        likingUser.setFirstName("Klaudia");
//        likingUser.setEmail("klaudia@domain.com");
        likingUser.setAccountStatus(AccountStatus.NEW);

        BlogPost post = new BlogPost();
//        post.setEntry("test");
        post.setUser(postOwner);
        post.setId(postId);

        Mockito.when(userRepository.findOne(likingUserId)).thenReturn(likingUser);
        Mockito.when(blogPostRepository.findOne(postId)).thenReturn(post);

        blogService.addLikeToPost(likingUserId, postId);
    }
}

//Dodanie polubienia do wpisu może dokonać tylko użytkownik ze statusem konta CONFIRMED.
// Próba dodania polubienia do wpisu przez użytkownika z innym statusem
// powinna skończyć się zgłoszeniem wyjątku typu DomaniError.