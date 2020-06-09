package com.sreehari.content;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sreehari.content.rest.ContentCrawler;
import com.sreehari.content.rest.ContentHelper;

@SpringBootTest
class ContentCrawlerApplicationTests {

	private static final String TEST_URL = "test";

	@Autowired
	private ContentCrawler contentCrawler;

	@MockBean
	ContentHelper contentHelper;

	private static final String ORIGINAL_CONTENT = "<div class=\"et_pb_blurb_description\"><span><span class=\"et-pb-icon et-waypoint et_pb_animation_top et-animated divi_font_awesome_icon divi_font_awesome_icon--undefined divi_font_awesome_icon--font-awesome\" data-family=\"font-awesome\" style=\"color:#ffc654;\">?</span>&nbsp;&nbsp;I turned down the new position because I knew that I was right where I was supposed to be. At first, I stayed because I thought the residents needed me. It didn’t take me long to realize that I needed them just as much, or more, than they needed me. This company, this facility, the residents and the staff have been an absolute blessing to me for more than 10 years now.”</span><p></p><p><strong>Scott L.</strong><br> Senior Admissions Director</p></div><div class=\"et_pb_blurb_description\"><span><span class=\"et-pb-icon et-waypoint et_pb_animation_top et-animated divi_font_awesome_icon divi_font_awesome_icon--undefined divi_font_awesome_icon--font-awesome\" data-family=\"font-awesome\" style=\"color:#ff9ece;\">?</span>&nbsp;&nbsp;One of our new residents wanted nothing to do with us and would use every trick in the book to push our buttons and keep us away. One day, in an attempt to lift her spirits, I asked her in a teasing voice if she was going to stay grumpy all day. Her response was, ‘Of course. It’s more fun that way.’ I laughed and had agreed with her.<p></p><p>Since then, we ask each other that question every day and the answer is always, ‘Of course. It’s more fun that way.’</p><p>The truth is, she’s the one that makes my days more fun.”</p><p><strong>Deb N.</strong><br> Nurse Manager</p></span></div>";
	private static final String MODIFIED_CONTENT = "<div class=\"et_pb_blurb_description\"><span><span class=\"et-pb-icon et-waypoint et_pb_animation_top et-animated divi_font_awesome_icon divi_font_awesome_icon--undefined divi_font_awesome_icon--font-awesome\" data-family=\"font-awesome\" style=\"color:#ffc654;\">?</span>&nbsp;&nbsp;I turned down the new position because I knew that I was right where I was supposed to be. At first, I stayed because I thought the residents needed me. It didn’t take me long to realize that I needed them just as much, or more, than they needed me. This company, this facility, the residents and the staff have been an absolute blessing to me for more than 10 years now.”</span><p></p><p><strong>Deb N.</strong><br> Senior Admissions Director</p></div><div class=\"et_pb_blurb_description\"><span><span class=\"et-pb-icon et-waypoint et_pb_animation_top et-animated divi_font_awesome_icon divi_font_awesome_icon--undefined divi_font_awesome_icon--font-awesome\" data-family=\"font-awesome\" style=\"color:#ff9ece;\">?</span>&nbsp;&nbsp;One of our new residents wanted nothing to do with us and would use every trick in the book to push our buttons and keep us away. One day, in an attempt to lift her spirits, I asked her in a teasing voice if she was going to stay grumpy all day. Her response was, ‘Of course. It’s more fun that way.’ I laughed and had agreed with her.<p></p><p>Since then, we ask each other that question every day and the answer is always, ‘Of course. It’s more fun that way.’</p><p>The truth is, she’s the one that makes my days more fun.”</p><p><strong>Scott L.</strong><br> Nurse Manager</p></span></div>";

	@Test
	void should_retun_description_html() {

		// given
		Mockito.when(contentHelper.crawl(Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(ORIGINAL_CONTENT));

		// when
		String result = contentCrawler.getContent(TEST_URL);

		// then
		assertEquals(ORIGINAL_CONTENT, result);
	}

	@Test
	void should_return_updated_names() {

		// given
		Mockito.when(contentHelper.crawl(Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(Optional.of(MODIFIED_CONTENT));

		// when
		String result = contentCrawler.modifiedContent(TEST_URL);

		// then
		assertEquals(MODIFIED_CONTENT, result);
	}

}
