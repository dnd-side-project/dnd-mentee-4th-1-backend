package org.dnd4.yorijori;

import static junit.framework.TestCase.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dnd4.yorijori.domain.ingredient.repository.IngredientRepository;
import org.dnd4.yorijori.domain.monthly_view.service.MonthlyViewService;
import org.dnd4.yorijori.domain.recipe.dto.IngredientDto;
import org.dnd4.yorijori.domain.recipe.dto.RequestDto;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.repository.RecipeRepository;
import org.dnd4.yorijori.domain.recipe.service.RecipeListService;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.dnd4.yorijori.domain.theme.entity.Theme;
import org.dnd4.yorijori.domain.theme.repository.ThemeRepository;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
public class RecipeViewRankTest {

	@Autowired
	MonthlyViewService monthlyViewService;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, Long> zSetOperations;
	
	@Autowired
	RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
	ThemeRepository themeRepository;
    @Autowired
    RecipeListService recipeListService;
    
	@Before
	public void 초기화() {
		List<IngredientDto> mainIngredientDtos = new ArrayList<>();
		IngredientDto ingredientDto = new IngredientDto("재료");
		mainIngredientDtos.add(ingredientDto);

		List<Long> themeIds = new ArrayList<>();
		Theme theme = Theme.builder().name("분위기").build();
		Long themeId = themeRepository.save(theme).getId();
		themeIds.add(themeId);

		RequestDto.Step step = new RequestDto.Step();
		step.setDescription("des");
		step.setImageUrl("http://image.dongascience.com/Photo/2020/03/5bddba7b6574b95d37b6079c199d7101.jpg");

		List<RequestDto.Step> steps = new ArrayList<>();
		steps.add(step);

		User user = User.builder().name("soob").email("sjklf@slkj.com").build();

		Long userId = userRepository.save(user).getId();

		RequestDto requestDto = new RequestDto("title",
				"http://image.dongascience.com/Photo/2020/03/5bddba7b6574b95d37b6079c199d7101.jpg", mainIngredientDtos,
				null, themeIds, steps, 15, userId, null);
		
		Long savedId = recipeService.add(requestDto);
		zSetOperations.incrementScore("view", savedId, 5); // id=1, view=5
		savedId = recipeService.add(requestDto);
		zSetOperations.incrementScore("view", savedId, 4); // id=2, view=4
		savedId = recipeService.add(requestDto);
		zSetOperations.incrementScore("view", savedId, 3); // id=3, view=3
		savedId = recipeService.add(requestDto);
		zSetOperations.incrementScore("view", savedId, 2); // id=4, view=2
		savedId = recipeService.add(requestDto);
		zSetOperations.incrementScore("view", savedId, 1); // id=5, view=1
	}

	@Test
	public void rank() throws Exception {
		List<ResponseDto> rank = monthlyViewService.rank(3);
		assertEquals(rank.get(0).getId(), Long.valueOf(1));
		assertEquals(rank.get(1).getId(), Long.valueOf(2));
		assertEquals(rank.get(2).getId(), Long.valueOf(3));
	}
	
}
