package utils.builders;

import java.util.List;
import java.util.UUID;

import com.github.javafaker.Faker;

import br.com.delflix.shared.response.dvdResponse.ResponseDvdJson;
import br.com.delflix.shared.response.dvdResponse.ResponseDvdsCatalogJson;
import br.com.delflix.shared.response.rentResponse.ResponseRentDvdJson;
import utils.tools.UtilsTools;

public class ResponseBuilderFactory {

    private static final Faker faker = new Faker();

    public static ResponseDvdsCatalogJson ResponseDvdsCatalogBuilder() {
        return new ResponseDvdsCatalogJson(
                ResponseDvdListBuilder()
        );
    }

    public static List<ResponseDvdJson> ResponseDvdListBuilder() {
        return List.of(ResponseDvdBuilder());
    }

    public static ResponseDvdJson ResponseDvdBuilder() {
        return new ResponseDvdJson(
                faker.book().title(),
                UtilsTools.GetRandomNumberBetween(0, 10),
                UUID.randomUUID().toString(),
                faker.random().nextBoolean()
        );
    }

    public static ResponseRentDvdJson ResponseRentDvdBuilder() {
        return new ResponseRentDvdJson(
                faker.book().title(),
                UUID.randomUUID().toString(),
                faker.random().nextInt(1, 7)
        );
    }
}
