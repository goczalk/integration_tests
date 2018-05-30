package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
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


@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository blogPostRepository;

    @MockBean
    LikePostRepository likePostRepository;

    @Autowired
    DataMapper dataMapper;

    @Autowired
    BlogService blogService;
    
    private Long likingUserId;
    private User likingUser;
    private Long postId;

    @Before
    public void setup(){
        Long postOwnerId = 1L;
        likingUserId = 2L;
        postId = 3L;

        User postOwner = new User();
        postOwner.setId(postOwnerId);

        likingUser = new User();
        likingUser.setId(likingUserId);


        BlogPost post = new BlogPost();
        post.setUser(postOwner);
        post.setId(postId);

        Mockito.when(userRepository.findOne(likingUserId)).thenReturn(likingUser);
        Mockito.when(blogPostRepository.findOne(postId)).thenReturn(post);
        Mockito.when(likePostRepository.findByUserAndPost(likingUser, post))
                                        .thenReturn(java.util.Optional.of(new LikePost()));
    }

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test(expected = DomainError.class)
    public void NEWuserCannotAddLikeToPost(){
        likingUser.setAccountStatus(AccountStatus.NEW);
        blogService.addLikeToPost(likingUserId, postId);
    }

    @Test(expected = DomainError.class)
    public void REMOVEDuserCannotAddLikeToPost(){
        likingUser.setAccountStatus(AccountStatus.REMOVED);
        blogService.addLikeToPost(likingUserId, postId);
    }

    @Test
    public void CONFIRMEDuserCanAddLikeToPost(){
        likingUser.setAccountStatus(AccountStatus.CONFIRMED);
        blogService.addLikeToPost(likingUserId, postId);
    }
}
