package com.se1.postservice;

import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.payload.GetPostResponseDto;
import com.se1.postservice.domain.payload.GetPostResponseDto.TopicTag;

@SpringBootTest
class PostServiceApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void contextLoads() throws JsonProcessingException {
		GetPostResponseDto getPostResponseDto = new GetPostResponseDto();
		getPostResponseDto.setId(1);
		getPostResponseDto.setTitle("Mạng xã hội và những tác động tới trẻ em");
		getPostResponseDto.setMetaTitle("mang xa hoi va nhung tac dong toi tre em");
		getPostResponseDto.setSlug("mang-xa-hoi-va-nhung-tac-dong-toi-tre-em");
		getPostResponseDto.setContext("STO - Cùng với sự phát triển mạnh mẽ của internet và các thiết bị thông minh, nhiều người dễ dàng tiếp cận với mạng xã hội (MXH), trong đó có cả trẻ em. Bên cạnh mặt tích cực, mạng xã hội còn tồn tại những mặt trái có thể ảnh hưởng đến sự phát triển và tâm lý của trẻ.\r\n"
				+ "\r\n"
				+ "Sự phát triển của công nghệ và mạng internet đang ảnh hưởng mạnh mẽ và làm thay đổi thói quen sinh hoạt của nhiều người, trong đó có những tác động to lớn đối với trẻ em. Ngày nay, nhiều trẻ em được tiếp xúc với mạng internet ngay từ khi đi học mẫu giáo, thông qua các thiết bị điện tử thông minh, trẻ em có thể học nhiều điều hay từ internet, song môi trường trên mạng cũng mang lại rất nhiều rủi ro, bởi lẽ trên mạng internet, nhất là các trang mạng xã hội hay nền tảng chia sẻ video đang có nhiều hình ảnh, video được đăng tải có không ít nội dung có thể tác động trực tiếp đến tâm lý, hành vi và nhân cách của trẻ em."
				+ "Nổi bật trong các ứng dụng được nhiều người cài đặt và sử dụng gần đây là TikTok. Đây là nền tảng chia sẻ video có nguồn gốc từ Trung Quốc, bằng những video âm nhạc ngắn được chia sẻ trên nền tảng này mà TikTok thu hút hàng triệu người dùng. Một mạng xã hội chia sẻ video khác được rất nhiều người sử dụng là YouTube, hiện đang được dùng ở mọi lứa tuổi, đặc biệt là giới trẻ, truy cập và sử dụng hàng ngày. Thông qua các video được đăng tải trên các ứng dụng này, nhiều người có thể giải trí, học tập hay truyền tải thông tin của cá nhân mình, thậm chí nếu tận dụng tốt các ứng dụng còn trở thành công cụ kiếm tiền. Tuy nhiên, trên các ứng dụng này cũng tồn tại nhiều video có nội dung phản cảm, không phù hợp với trẻ nhỏ.\r\n"
				+ "\r\n"
				+ "Chị Trần Thị Thu Hương, ở huyện Cù Lao Dung cho biết: “Ở quê ít có tụ điểm vui chơi cho trẻ nhỏ nên khi thằng con tôi học bài xong thì tôi đưa điện thoại để nó chơi game hay coi video giải trí. Điều đáng lo là gần đây tôi phát hiện trên YouTube hay TikTok có một số video khiến tôi giật mình, bởi các video này có nội dung “khoe thân” của các bạn trẻ hay những clip có nội dung gây sốc để câu like (lượt thích) hay câu view (lượt xem)”.\r\n"
				+ "\r\n"
				+ "Những video có lượt xem nhiều, thích nhiều thì sẽ tự xuất hiện trên mục “Thịnh hành” ở YouTube hay mục “Xu hướng” trên TikTok. Do đó nếu trẻ em truy cập các ứng dụng này sẽ vô thức truy cập vào những nội dung đó. Đáng lo ngại hơn khi hiện nay xuất hiện trường hợp trẻ em học theo các video trên mạng để quay clip câu view theo phong trào. Anh Trịnh Thành Được, ở huyện Long Phú ngỡ ngàng kể lại: “Hồi tuần rồi, thằng con tôi chỉ mới học lớp 5 mà nó dám rủ mấy đứa bạn trong xóm bắt con chó nhỏ để thảy xuống sông trước nhà để quay clip. Hỏi ra mới biết là nó muốn tải lên ứng dụng TikTok để được nhiều người tương tác”.\r\n"
				+ "\r\n"
				+ "Không phải phụ huynh nào cũng nhận biết rõ những ảnh hưởng của việc tiếp xúc sớm với mạng xã hội vì công nghệ ngày càng phát triển trong khi các bậc phụ huynh lại quá bận bịu với nhiều công việc, nếu không tiếp cận thường xuyên sẽ không biết về những mặt trái này. Điều đáng lo ngại hơn cả là có nhiều em nhỏ lại bị cuốn hút với những video nguy hiểm, có nội dung bạo lực… Trong khi, nhiều bậc phụ huynh chưa có biện pháp hiệu quả để kiểm soát việc con em mình tiếp cận với các nội dung này. Một số phụ huynh còn lợi dụng các thiết bị điện tử thông minh để “dụ” trẻ ăn cơm hoặc chọn là phần thưởng khi trẻ đạt thành tích cao trong học tập, vô hình trung làm nhiều trẻ nghiện sử dụng các thiết bị này.\r\n"
				+ "\r\n"
				+ "Trong khi các biện pháp kiểm soát, ngăn chặn các nội dung xấu trên không gian mạng vẫn còn hạn chế thì các bậc phụ huynh phải tăng cường quản lý nội dung mà trẻ em truy cập trên các nền tảng chia sẻ video nói riêng hay các trang mạng xã hội nói chung. Thực tế đã có nhiều trường hợp trẻ em bị nghiện điện thoại và có không ít hậu quả đáng tiếc đã xảy ra. Thế nên, để giúp con mình tránh được các hiểm họa từ môi trường mạng, thì các bậc phụ huynh cần kiểm soát, định hướng chặt chẽ để giúp con em chọn lọc được thông tin phù hợp, hữu ích với lứa tuổi.");
		getPostResponseDto.setLikeCount(1000);
		getPostResponseDto.setDisLikeCount(100);
		getPostResponseDto.setCommentCount(100);
		getPostResponseDto.setShareCount(50);
		getPostResponseDto.setHashTag(null);
		getPostResponseDto.setImageList(List.of("https://www.baosoctrang.org.vn/uploads/image/2020/11/25/Dien%20thoai.jpg"));
		
		GetPostResponseDto.TopicTag topicTag = new GetPostResponseDto().new TopicTag();
		topicTag.setId(1);
		topicTag.setName("Mạng xã hội");
		getPostResponseDto.setTopicTag(topicTag);
		
		GetPostResponseDto.User userPost = new GetPostResponseDto().new User();
		userPost.setId(1);
		userPost.setName("Thanh Vinh Expert");
		userPost.setEmail("thanhvinhexpert@gmail.com");
		userPost.setImageUrl("https://i.pinimg.com/236x/d9/23/1d/d9231dd1faf237fc69a6e4d5f6723d05.jpg");
		userPost.setIsExpert(true);
		userPost.setRatingCount(4.5);
		userPost.setTopicId(UUID.randomUUID().toString());
		getPostResponseDto.setUser(userPost);
		getPostResponseDto.setPublishAt(new Date());
		
		String jsonResponse = objectMapper.writeValueAsString(getPostResponseDto);
	}

	@Test
	public void tesst1() {
		PriorityQueue<GetPostResponseDto> priorityQueue = new PriorityQueue<>();
	}
}
