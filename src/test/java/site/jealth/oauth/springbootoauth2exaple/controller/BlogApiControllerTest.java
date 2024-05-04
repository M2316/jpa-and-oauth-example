package site.jealth.oauth.springbootoauth2exaple.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;
import site.jealth.oauth.springbootoauth2exaple.dto.ArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.dto.UpdateArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.repository.BlogRepository;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;


    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }


    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content test 데이터임";
        final ArticleRequest userRequest = new ArticleRequest(title,content);

        //객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(
                post(url).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(requestBody)
        );

        // then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);


    }

    @DisplayName("findAllArticles : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception{

        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content test 데이터임";

        blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }
    @DisplayName("findArticle : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception{

        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content test 데이터임";

        Article savedArticle = blogRepository.save(Article.builder()
                        .title(title)
                        .content(content)
                .build());
        //when
        final ResultActions resultActions = mockMvc.perform(get(url,savedArticle.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteArticle : 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content test 데이터임";


        final Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build()
        );

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(delete(url,savedArticle.getId()))
                .andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle : 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final String title = "title수정하면 성공~~";
        final String content = "content test 데이터임 수정해봐~~";

        final Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build()
        );

        final String newTitle = "신규 타이틀~!";
        final String newContent = "신규 내용입ㄴ디ㅏ .ㅂㅈ디ㅜㅏㅓ줃ㅎ";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle,newContent);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(put(url,savedArticle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getContent()).isEqualTo(newContent);
        assertThat(article.getTitle()).isEqualTo(newTitle);

    }

}