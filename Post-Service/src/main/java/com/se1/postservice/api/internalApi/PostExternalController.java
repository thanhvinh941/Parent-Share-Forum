package com.se1.postservice.api.internalApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
			+ "    \"id\" : 1,\r\n"
			+ "    \"title\" : \"How to Take Care of Young Children\",\r\n"
			+ "    \"metaTitle\" : \"how-to-take-care-of-young-children\",\r\n"
			+ "    \"slug\" : \"how-to-take-care-of-young-children\",\r\n"
			+ "    \"summary\" : \"How to Take Care of Young Children\",\r\n"
			+ "    \"context\" : \"\",\r\n"
			+ "    \"likeCount\" : 1000,\r\n"
			+ "    \"hashTag\" : \"#how-to-take-care-of-young-children\",\r\n"
			+ "    \"topicTag\" : {\r\n"
			+ "      \"id\" : 1,\r\n"
			+ "      \"name\" : \"How to Take Care\"\r\n"
			+ "    },\r\n"
			+ "    \"user\" : {\r\n"
			+ "      \"id\" : 1,\r\n"
			+ "      \"name\" : \"Thanh Vinh\",\r\n"
			+ "      \"email\" : \"vinh@gmail.com\",\r\n"
			+ "      \"imageUrl\" : \"\",\r\n"
			+ "      \"isExpert\" : false,\r\n"
			+ "      \"licenceId\" : null,\r\n"
			+ "      \"ratingCount\" : null,\r\n"
			+ "      \"topicId\" : \"47ba41fa-b1e9-11ed-afa1-0242ac120002\"\r\n"
			+ "    }\r\n"
			+ "  }";
	
	private String contextDummy1 = "Young children, ranging from toddlers to those who have just started school, are all different in their own way. That's why you as a parent, guardian, relative, or babysitter play a crucial part in their development. Not only are you helping their fine, gross, and motor skills develop, you're shaping their perspective of life and their surroundings each and every time you are with them. Taking care of a young child might seem challenging at first, especially with all of the tantrums and crying, but by the end of the day you'll be a pro. Read on to discover how you can take care of young children!<h1>1</h1><div><b>Wake the children up at a set time every day.</b> This can easily be adjusted when necessary, but start with a time such as seven thirty or eight in the morning. You can have an alarm go off for the children to wake up to, such as a favorite children's song or a soft bell. Avoid something too loud, such as a rooster crowing or rock music. If they don't wake up right away, you can gently tap them and say something such as, 'Hey, it's time to get up, sleepyhead'. Once they are awake, you can start getting them ready for the day by getting them dressed, doing their hair, and brushing their teeth.<ul><li>Let the kids feel responsible and a part of their routine by giving them options. You can lay out two shirts and let them choose a shirt, let them choose to wash their face or brush their teeth first, or allow them to either help you set the table or get you the food you need from the refrigerator.</li></ul><a href=\"https://www.wikihow.com/Take-Care-of-Young-Children\">link tham khao</a> ";
	
	
	String postDummy2 = "{\r\n"
			+ "    \"id\" : 2,\r\n"
			+ "    \"title\" : \"How to Be a Good Child\",\r\n"
			+ "    \"metaTitle\" : \"how to be a good child\",\r\n"
			+ "    \"slug\" : \"how-to-be-a-good-child\",\r\n"
			+ "    \"summary\" : \"How to Be a Good Child\",\r\n"
			+ "    \"context\" : \"\",\r\n"
			+ "    \"likeCount\" : 1000000,\r\n"
			+ "    \"hashTag\" : \"#how-to-be-a-good-child\",\r\n"
			+ "    \"topicTag\" : {\r\n"
			+ "      \"id\" : 3,\r\n"
			+ "      \"name\" : \"How to Be\"\r\n"
			+ "    },\r\n"
			+ "    \"user\" : {\r\n"
			+ "      \"id\" : 2,\r\n"
			+ "      \"name\" : \"Bao Huynh\",\r\n"
			+ "      \"email\" : \"baohuynh@gmail.com\",\r\n"
			+ "      \"imageUrl\" : \"\",\r\n"
			+ "      \"isExpert\" : true,\r\n"
			+ "      \"licenceId\" : 1,\r\n"
			+ "      \"ratingCount\" : 4.6,\r\n"
			+ "      \"topicId\" : \"47ba466e-b1e9-11ed-afa1-0242ac120002\"\r\n"
			+ "    }\r\n"
			+ "  }";
	
	String contextDummy2 = "1\r\n"
			+ "Accept your responsibilities. It is easy to say that a good child listens to their parents (and other authority figures) and does what they are told. While this is normally true, it is more important that children learn to take responsibility for what they need to do. As a child who strives to be your best, you need to accept that there are things you have to do, for the benefit of yourself and others.[2]\r\n"
			+ "The goal of being a good child is not really about giving your parents a little less grief (though they will welcome that). Good children learn qualities that will help them become happy, successful, “good” adults.\r\n"
			+ "For example, you need to take responsibility for doing your homework and completing your chores, without constant reminders or resistance. This will help you to become more self-motivated, self-sufficient, and successful in work and life as an adult.";
	
	String postDummy3 = "{\r\n"
			+ "    \"id\" : 3,\r\n"
			+ "    \"title\" : \"How to Develop a Child Care Philosophy\",\r\n"
			+ "    \"metaTitle\" : \"how to develop a child care philosophy\",\r\n"
			+ "    \"slug\" : \"how-to-develop-a-child-care-philosophy\",\r\n"
			+ "    \"summary\" : \"How to Develop a Child Care Philosophy\",\r\n"
			+ "    \"context\" : \"\",\r\n"
			+ "    \"likeCount\" : 100000,\r\n"
			+ "    \"hashTag\" : \"#how-to-develop-a-child-care-philosophy\",\r\n"
			+ "    \"topicTag\" : {\r\n"
			+ "      \"id\" : 2,\r\n"
			+ "      \"name\" : \"How to Develop\"\r\n"
			+ "    },\r\n"
			+ "    \"user\" : {\r\n"
			+ "      \"id\" : 3,\r\n"
			+ "      \"name\" : \"Vinh Truong\",\r\n"
			+ "      \"email\" : \"vinhtruong@gmail.com\",\r\n"
			+ "      \"imageUrl\" : \"\",\r\n"
			+ "      \"isExpert\" : true,\r\n"
			+ "      \"licenceId\" : 2,\r\n"
			+ "      \"ratingCount\" : 4.5,\r\n"
			+ "      \"topicId\" : \"47ba47b8-b1e9-11ed-afa1-0242ac120002\"\r\n"
			+ "    }\r\n"
			+ "  }";
	
	String contextDummy3 = "1\r\n"
			+ "Ask yourself about your child care beliefs.[2] As a care provider, how do you think about children's child care and development? It will help to define clearly what you believe about a child's developmental strengths and needs in order to develop a philosophy of child care.[3]\r\n"
			+ "If you are partnering with others to develop your child care philosophy, this will give you an opportunity to better articulate your individual experiences and to learn about the perspective of others.\r\n"
			+ "Considering the role that you believe play, art, dramatic and academic activities should have in a child's life will help you think about your beliefs.\r\n"
			+ "An example of a statement regarding a program's beliefs regarding child care might be: \"We are committed to providing safe, affordable, high-quality service for children living in the South Lake community that is based in hands-on experiential learning.\"";
	
	String postDummy4 = "{\r\n"
			+ "    \"id\" : 4,\r\n"
			+ "    \"title\" : \"How to Care for a Sick Child\",\r\n"
			+ "    \"metaTitle\" : \"how to care for a sick child\",\r\n"
			+ "    \"slug\" : \"how-to-care-for-a-sick-child\",\r\n"
			+ "    \"summary\" : \"How to Care for a Sick Child\",\r\n"
			+ "    \"context\" : \"\",\r\n"
			+ "    \"likeCount\" : 100000,\r\n"
			+ "    \"hashTag\" : \"#how-to-care-for-a-sick-child\",\r\n"
			+ "    \"topicTag\" : {\r\n"
			+ "      \"id\" : 1,\r\n"
			+ "      \"name\" : \"How to Take Care\"\r\n"
			+ "    },\r\n"
			+ "    \"user\" : {\r\n"
			+ "      \"id\" : 4,\r\n"
			+ "      \"name\" : \"Quoc Bao\",\r\n"
			+ "      \"email\" : \"quocbao@gmail.com\",\r\n"
			+ "      \"imageUrl\" : \"\",\r\n"
			+ "      \"isExpert\" : false,\r\n"
			+ "      \"licenceId\" : null,\r\n"
			+ "      \"ratingCount\" : null,\r\n"
			+ "      \"topicId\" : \"47ba48da-b1e9-11ed-afa1-0242ac120002\"\r\n"
			+ "    }\r\n"
			+ "  }";
	
	String contextDummy4 = "5\r\n"
			+ "Keep your home at a comfortable temperature. Your child may feel hot or cold depending on the illness, so adjusting the temperature in your home may help your child to feel more comfortable. It may be helpful to keep your home between 65 and 70 degrees, but you can also adjust this temperature if your child is too hot or too cold.[4]\r\n"
			+ "For example, if your child is complaining that he or she is too cold, then turn up the heat a bit. If your child complains that he or she is hot, then turn on an air conditioner or fan.";
	
	@GetMapping("/findAllPost")
	public ResponseEntity<?> findAll() throws JsonMappingException, JsonProcessingException{

		GetPostResponseDto getPostResponseDto = mapper.readValue(postDummy1, GetPostResponseDto.class);
		getPostResponseDto.setContext(contextDummy1);
		getPostResponseDto.setImageList(List.of("https://www.wikihow.com/images/thumb/9/9f/Develop-a-Child-Care-Philosophy-Step-2-Version-2.jpg/aid2409989-v4-728px-Develop-a-Child-Care-Philosophy-Step-2-Version-2.jpg.webp"));
		
		GetPostResponseDto getPostResponseDto1 = mapper.readValue(postDummy2, GetPostResponseDto.class);
		getPostResponseDto1.setContext(contextDummy2);
		getPostResponseDto.setImageList(List.of("https://www.wikihow.com/Develop-a-Child-Care-Philosophy#/Image:Develop-a-Child-Care-Philosophy-Step-3-Version-2.jpg"));
		
		GetPostResponseDto getPostResponseDto2 = mapper.readValue(postDummy3, GetPostResponseDto.class);
		getPostResponseDto2.setContext(contextDummy3);
		getPostResponseDto.setImageList(List.of("https://www.wikihow.com/images/thumb/1/11/Develop-a-Child-Care-Philosophy-Step-4-Version-2.jpg/aid2409989-v4-728px-Develop-a-Child-Care-Philosophy-Step-4-Version-2.jpg.webp"));
		
		GetPostResponseDto getPostResponseDto3 = mapper.readValue(postDummy4, GetPostResponseDto.class);
		getPostResponseDto3.setContext(contextDummy4);
		getPostResponseDto.setImageList(List.of("https://www.wikihow.com/images/thumb/8/8c/Develop-a-Child-Care-Philosophy-Step-5-Version-2.jpg/aid2409989-v4-728px-Develop-a-Child-Care-Philosophy-Step-5-Version-2.jpg.webp","https://www.wikihow.com/images/thumb/9/96/Develop-a-Child-Care-Philosophy-Step-6-Version-2.jpg/aid2409989-v4-728px-Develop-a-Child-Care-Philosophy-Step-6-Version-2.jpg.webp"));
		
		return this.okResponse(List.of(getPostResponseDto,getPostResponseDto1,getPostResponseDto2,getPostResponseDto3), null);
	}
	
	private ResponseEntity<?> okResponse(Object data, List<String> errorMessage){
		apiResponseEntity.setData(data);
		apiResponseEntity.setErrorList(errorMessage);
		apiResponseEntity.setStatus(1);
		return ResponseEntity.ok().body(apiResponseEntity);
	}
}
