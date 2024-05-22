package utils.builders;

import java.util.Date;

import com.github.javafaker.Faker;

import br.com.delflix.domain.entity.Author;
import br.com.delflix.domain.entity.Dvd;
import br.com.delflix.shared.Enum.GenderEnum;
import utils.tools.UtilsTools;

public class EntityBuilderFactory {

    private static final Faker faker = new Faker();

    public static Dvd DvdBuilder() {
        return new Dvd(
                faker.book().title(),
                AuthorBuilder(),
                GenderEnum.HORROR.toString(),
                new Date(),
                UtilsTools.GetRandomNumberBetween(0, 100));
    }

    public static Author AuthorBuilder() {
        return new Author(
                faker.name().firstName(),
                faker.name().lastName());
    }
}
