package com.se1.postservice.api.internalApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.se1.postservice.domain.payload.ApiResponseEntity;
import com.se1.postservice.domain.payload.GetPostResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post/external")
@RequiredArgsConstructor
public class PostExternalController {

	private final ApiResponseEntity apiResponseEntity;
	private final ObjectMapper mapper;
	
	String postDummy1 = "{\r\n"
			+ "  \"id\": 1,\r\n"
			+ "  \"title\": \"Mạng xã hội và những tác động tới trẻ em\",\r\n"
			+ "  \"metaTitle\": \"mang xa hoi va nhung tac dong toi tre em\",\r\n"
			+ "  \"slug\": \"mang-xa-hoi-va-nhung-tac-dong-toi-tre-em\",\r\n"
			+ "  \"summary\": \"Mạng xã hội và những tác động tới trẻ em\",\r\n"
			+ "  \"context\": \"STO - Cùng với sự phát triển mạnh mẽ của internet và các thiết bị thông minh, nhiều người dễ dàng tiếp cận với mạng xã hội (MXH), trong đó có cả trẻ em. Bên cạnh mặt tích cực, mạng xã hội còn tồn tại những mặt trái có thể ảnh hưởng đến sự phát triển và tâm lý của trẻ.\\r\\n\\r\\nSự phát triển của công nghệ và mạng internet đang ảnh hưởng mạnh mẽ và làm thay đổi thói quen sinh hoạt của nhiều người, trong đó có những tác động to lớn đối với trẻ em. Ngày nay, nhiều trẻ em được tiếp xúc với mạng internet ngay từ khi đi học mẫu giáo, thông qua các thiết bị điện tử thông minh, trẻ em có thể học nhiều điều hay từ internet, song môi trường trên mạng cũng mang lại rất nhiều rủi ro, bởi lẽ trên mạng internet, nhất là các trang mạng xã hội hay nền tảng chia sẻ video đang có nhiều hình ảnh, video được đăng tải có không ít nội dung có thể tác động trực tiếp đến tâm lý, hành vi và nhân cách của trẻ em.Nổi bật trong các ứng dụng được nhiều người cài đặt và sử dụng gần đây là TikTok. Đây là nền tảng chia sẻ video có nguồn gốc từ Trung Quốc, bằng những video âm nhạc ngắn được chia sẻ trên nền tảng này mà TikTok thu hút hàng triệu người dùng. Một mạng xã hội chia sẻ video khác được rất nhiều người sử dụng là YouTube, hiện đang được dùng ở mọi lứa tuổi, đặc biệt là giới trẻ, truy cập và sử dụng hàng ngày. Thông qua các video được đăng tải trên các ứng dụng này, nhiều người có thể giải trí, học tập hay truyền tải thông tin của cá nhân mình, thậm chí nếu tận dụng tốt các ứng dụng còn trở thành công cụ kiếm tiền. Tuy nhiên, trên các ứng dụng này cũng tồn tại nhiều video có nội dung phản cảm, không phù hợp với trẻ nhỏ.\\r\\n\\r\\nChị Trần Thị Thu Hương, ở huyện Cù Lao Dung cho biết: “Ở quê ít có tụ điểm vui chơi cho trẻ nhỏ nên khi thằng con tôi học bài xong thì tôi đưa điện thoại để nó chơi game hay coi video giải trí. Điều đáng lo là gần đây tôi phát hiện trên YouTube hay TikTok có một số video khiến tôi giật mình, bởi các video này có nội dung “khoe thân” của các bạn trẻ hay những clip có nội dung gây sốc để câu like (lượt thích) hay câu view (lượt xem)”.\\r\\n\\r\\nNhững video có lượt xem nhiều, thích nhiều thì sẽ tự xuất hiện trên mục “Thịnh hành” ở YouTube hay mục “Xu hướng” trên TikTok. Do đó nếu trẻ em truy cập các ứng dụng này sẽ vô thức truy cập vào những nội dung đó. Đáng lo ngại hơn khi hiện nay xuất hiện trường hợp trẻ em học theo các video trên mạng để quay clip câu view theo phong trào. Anh Trịnh Thành Được, ở huyện Long Phú ngỡ ngàng kể lại: “Hồi tuần rồi, thằng con tôi chỉ mới học lớp 5 mà nó dám rủ mấy đứa bạn trong xóm bắt con chó nhỏ để thảy xuống sông trước nhà để quay clip. Hỏi ra mới biết là nó muốn tải lên ứng dụng TikTok để được nhiều người tương tác”.\\r\\n\\r\\nKhông phải phụ huynh nào cũng nhận biết rõ những ảnh hưởng của việc tiếp xúc sớm với mạng xã hội vì công nghệ ngày càng phát triển trong khi các bậc phụ huynh lại quá bận bịu với nhiều công việc, nếu không tiếp cận thường xuyên sẽ không biết về những mặt trái này. Điều đáng lo ngại hơn cả là có nhiều em nhỏ lại bị cuốn hút với những video nguy hiểm, có nội dung bạo lực… Trong khi, nhiều bậc phụ huynh chưa có biện pháp hiệu quả để kiểm soát việc con em mình tiếp cận với các nội dung này. Một số phụ huynh còn lợi dụng các thiết bị điện tử thông minh để “dụ” trẻ ăn cơm hoặc chọn là phần thưởng khi trẻ đạt thành tích cao trong học tập, vô hình trung làm nhiều trẻ nghiện sử dụng các thiết bị này.\\r\\n\\r\\nTrong khi các biện pháp kiểm soát, ngăn chặn các nội dung xấu trên không gian mạng vẫn còn hạn chế thì các bậc phụ huynh phải tăng cường quản lý nội dung mà trẻ em truy cập trên các nền tảng chia sẻ video nói riêng hay các trang mạng xã hội nói chung. Thực tế đã có nhiều trường hợp trẻ em bị nghiện điện thoại và có không ít hậu quả đáng tiếc đã xảy ra. Thế nên, để giúp con mình tránh được các hiểm họa từ môi trường mạng, thì các bậc phụ huynh cần kiểm soát, định hướng chặt chẽ để giúp con em chọn lọc được thông tin phù hợp, hữu ích với lứa tuổi.\",\r\n"
			+ "  \"likeCount\": 1000,\r\n"
			+ "  \"disLikeCount\": 100,\r\n"
			+ "  \"commentCount\": 100,\r\n"
			+ "  \"shareCount\": 50,\r\n"
			+ "  \"hashTag\": null,\r\n"
			+ "  \"imageList\": [\r\n"
			+ "    \"https://www.baosoctrang.org.vn/uploads/image/2020/11/25/Dien%20thoai.jpg\"\r\n"
			+ "  ],\r\n"
			+ "  \"topicTag\": {\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Mạng xã hội\"\r\n"
			+ "  },\r\n"
			+ "  \"user\": {\r\n"
			+ "    \"id\": 1,\r\n"
			+ "    \"name\": \"Thanh Vinh Expert\",\r\n"
			+ "    \"email\": \"thanhvinhexpert@gmail.com\",\r\n"
			+ "    \"imageUrl\": \"https://i.pinimg.com/236x/d9/23/1d/d9231dd1faf237fc69a6e4d5f6723d05.jpg\",\r\n"
			+ "    \"isExpert\": true,\r\n"
			+ "    \"ratingCount\": 4.5,\r\n"
			+ "    \"topicId\": \"c155b4ea-b4ba-4d95-a037-6f10adae35c4\"\r\n"
			+ "  },\r\n"
			+ "  \"publishAt\": \"2023-02-23 06:11:09.034\",\r\n"
			+ "  \"comment\": [\r\n"
			+ "    {\r\n"
			+ "      \"id\": 1,\r\n"
			+ "      \"text\": \"... comment 1\",\r\n"
			+ "      \"likeCount\": 5,\r\n"
			+ "      \"disLikeCount\": 1,\r\n"
			+ "      \"commentChildCount\": 2,\r\n"
			+ "      \"createAt\": \"2023-02-23 06:11:09.034\",\r\n"
			+ "      \"user\": {\r\n"
			+ "        \"id\": 2,\r\n"
			+ "        \"name\": \"Thanh Vinh User1\",\r\n"
			+ "        \"email\": \"thanhvinhuser1@gmail.com\",\r\n"
			+ "        \"imageUrl\": \"https://i.pinimg.com/236x/d9/23/1d/d9231dd1faf237fc69a6e4d5f6723d05.jpg\",\r\n"
			+ "        \"isExpert\": false,\r\n"
			+ "        \"ratingCount\": null,\r\n"
			+ "        \"topicId\": \"dc5de767-1e43-4625-9269-b52bc4bc098f\"\r\n"
			+ "      }\r\n"
			+ "    },\r\n"
			+ "    {\r\n"
			+ "      \"id\": 2,\r\n"
			+ "      \"text\": \"... comment 2\",\r\n"
			+ "      \"likeCount\": 5,\r\n"
			+ "      \"disLikeCount\": 1,\r\n"
			+ "      \"commentChildCount\": 2,\r\n"
			+ "      \"createAt\": \"2023-02-23 06:11:09.034\",\r\n"
			+ "      \"user\": {\r\n"
			+ "        \"id\": 3,\r\n"
			+ "        \"name\": \"Thanh Vinh User2\",\r\n"
			+ "        \"email\": \"thanhvinhuser2@gmail.com\",\r\n"
			+ "        \"imageUrl\": \"https://i.pinimg.com/236x/d9/23/1d/d9231dd1faf237fc69a6e4d5f6723d05.jpg\",\r\n"
			+ "        \"isExpert\": false,\r\n"
			+ "        \"ratingCount\": null,\r\n"
			+ "        \"topicId\": \"6893aa83-51e5-441c-bdb3-aeb2266a643e\"\r\n"
			+ "      }\r\n"
			+ "    }\r\n"
			+ "  ]\r\n"
			+ "}";
	
	String context = "STO - Cùng với sự phát triển mạnh mẽ của internet và các thiết bị thông minh, nhiều người dễ dàng tiếp cận với mạng xã hội (MXH), trong đó có cả trẻ em. Bên cạnh mặt tích cực, mạng xã hội còn tồn tại những mặt trái có thể ảnh hưởng đến sự phát triển và tâm lý của trẻ.\\r\\n\\r\\nSự phát triển của công nghệ và mạng internet đang ảnh hưởng mạnh mẽ và làm thay đổi thói quen sinh hoạt của nhiều người, trong đó có những tác động to lớn đối với trẻ em. Ngày nay, nhiều trẻ em được tiếp xúc với mạng internet ngay từ khi đi học mẫu giáo, thông qua các thiết bị điện tử thông minh, trẻ em có thể học nhiều điều hay từ internet, song môi trường trên mạng cũng mang lại rất nhiều rủi ro, bởi lẽ trên mạng internet, nhất là các trang mạng xã hội hay nền tảng chia sẻ video đang có nhiều hình ảnh, video được đăng tải có không ít nội dung có thể tác động trực tiếp đến tâm lý, hành vi và nhân cách của trẻ em.Nổi bật trong các ứng dụng được nhiều người cài đặt và sử dụng gần đây là TikTok. Đây là nền tảng chia sẻ video có nguồn gốc từ Trung Quốc, bằng những video âm nhạc ngắn được chia sẻ trên nền tảng này mà TikTok thu hút hàng triệu người dùng. Một mạng xã hội chia sẻ video khác được rất nhiều người sử dụng là YouTube, hiện đang được dùng ở mọi lứa tuổi, đặc biệt là giới trẻ, truy cập và sử dụng hàng ngày. Thông qua các video được đăng tải trên các ứng dụng này, nhiều người có thể giải trí, học tập hay truyền tải thông tin của cá nhân mình, thậm chí nếu tận dụng tốt các ứng dụng còn trở thành công cụ kiếm tiền. Tuy nhiên, trên các ứng dụng này cũng tồn tại nhiều video có nội dung phản cảm, không phù hợp với trẻ nhỏ.\\r\\n\\r\\nChị Trần Thị Thu Hương, ở huyện Cù Lao Dung cho biết: “Ở quê ít có tụ điểm vui chơi cho trẻ nhỏ nên khi thằng con tôi học bài xong thì tôi đưa điện thoại để nó chơi game hay coi video giải trí. Điều đáng lo là gần đây tôi phát hiện trên YouTube hay TikTok có một số video khiến tôi giật mình, bởi các video này có nội dung “khoe thân” của các bạn trẻ hay những clip có nội dung gây sốc để câu like (lượt thích) hay câu view (lượt xem)”.\\r\\n\\r\\nNhững video có lượt xem nhiều, thích nhiều thì sẽ tự xuất hiện trên mục “Thịnh hành” ở YouTube hay mục “Xu hướng” trên TikTok. Do đó nếu trẻ em truy cập các ứng dụng này sẽ vô thức truy cập vào những nội dung đó. Đáng lo ngại hơn khi hiện nay xuất hiện trường hợp trẻ em học theo các video trên mạng để quay clip câu view theo phong trào. Anh Trịnh Thành Được, ở huyện Long Phú ngỡ ngàng kể lại: “Hồi tuần rồi, thằng con tôi chỉ mới học lớp 5 mà nó dám rủ mấy đứa bạn trong xóm bắt con chó nhỏ để thảy xuống sông trước nhà để quay clip. Hỏi ra mới biết là nó muốn tải lên ứng dụng TikTok để được nhiều người tương tác”.\\r\\n\\r\\nKhông phải phụ huynh nào cũng nhận biết rõ những ảnh hưởng của việc tiếp xúc sớm với mạng xã hội vì công nghệ ngày càng phát triển trong khi các bậc phụ huynh lại quá bận bịu với nhiều công việc, nếu không tiếp cận thường xuyên sẽ không biết về những mặt trái này. Điều đáng lo ngại hơn cả là có nhiều em nhỏ lại bị cuốn hút với những video nguy hiểm, có nội dung bạo lực… Trong khi, nhiều bậc phụ huynh chưa có biện pháp hiệu quả để kiểm soát việc con em mình tiếp cận với các nội dung này. Một số phụ huynh còn lợi dụng các thiết bị điện tử thông minh để “dụ” trẻ ăn cơm hoặc chọn là phần thưởng khi trẻ đạt thành tích cao trong học tập, vô hình trung làm nhiều trẻ nghiện sử dụng các thiết bị này.\\r\\n\\r\\nTrong khi các biện pháp kiểm soát, ngăn chặn các nội dung xấu trên không gian mạng vẫn còn hạn chế thì các bậc phụ huynh phải tăng cường quản lý nội dung mà trẻ em truy cập trên các nền tảng chia sẻ video nói riêng hay các trang mạng xã hội nói chung. Thực tế đã có nhiều trường hợp trẻ em bị nghiện điện thoại và có không ít hậu quả đáng tiếc đã xảy ra. Thế nên, để giúp con mình tránh được các hiểm họa từ môi trường mạng, thì các bậc phụ huynh cần kiểm soát, định hướng chặt chẽ để giúp con em chọn lọc được thông tin phù hợp, hữu ích với lứa tuổi.";
	
	@GetMapping("/findAllPost")
	public ResponseEntity<?> findAll() throws JsonMappingException, JsonProcessingException{

		return this.okResponse(mapper.readValue(postDummy1, Object.class), null);
	}
	
	@GetMapping("/find-post")
	public ResponseEntity<?> findPostBySlug(@RequestParam ("post_id") Integer postId) throws JsonMappingException, JsonProcessingException{

		return this.okResponse(mapper.readValue(postDummy1, Object.class), null);
	}
	
	private ResponseEntity<?> okResponse(Object data, List<String> errorMessage){
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
