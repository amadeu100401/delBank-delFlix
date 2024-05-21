package utils.builders;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import br.com.delflix.shared.Enum.GenderEnum;
import br.com.delflix.shared.request.AuthorRequest.RequestAuthorRegisterJson;
import br.com.delflix.shared.request.DvdRequest.RequestDvdJson;
import br.com.delflix.shared.request.RentRequest.RequestRentJson;
import utils.tools.UtilsTools;

@Service
public class RequestBuilderFactory {

    private static final Faker faker = new Faker();

    public static RequestDvdJson RequestDvdBuilder() {
        return new RequestDvdJson(
                faker.book().title(),
                RequestAuthorBuilder(),
                GenderEnum.ACTION,
                new Date(),
                UtilsTools.GetRandomNumberBetween(0, 1000),
                UtilsTools.RandomBoolean()
        );
    }

    public static RequestDvdJson InvalidRequestDvdBuilder() {
        return new RequestDvdJson(
                null,
                RequestAuthorBuilder(),
                GenderEnum.ACTION,
                new Date(),
                UtilsTools.GetRandomNumberBetween(0, 1000),
                UtilsTools.RandomBoolean()
        );
    }

    public static RequestAuthorRegisterJson RequestAuthorBuilder() {
        return new RequestAuthorRegisterJson(
                faker.name().firstName(),
                faker.name().lastName()
        );
    }

    public static RequestRentJson RequestRentBuilder() {
        return new RequestRentJson(
                UUID.randomUUID().toString(),
                faker.random().nextInt(1, 7)
        );
    }
}
