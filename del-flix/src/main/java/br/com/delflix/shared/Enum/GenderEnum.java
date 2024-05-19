package br.com.delflix.shared.Enum;

import br.com.delflix.shared.exception.ErrorOnValidationException;

public enum GenderEnum 
{
    ACTION(1),
    COMEDY(2),
    DRAMA(3),
    ROMANCE(4),
    HORROR(5);

    private final int value;

    GenderEnum(int value) {
        this.value = value;
    }

    public int GetValue() {
        return value;
    }

    public static GenderEnum GetByValue(int value) {
        for (GenderEnum genre : GenderEnum.values()) {
            if (genre.GetValue() == value) {
                return genre;
            }
        }
        throw new ErrorOnValidationException("Invalid value for MovieGenre: " + value);
    }

    public static GenderEnum GetByName(String genderName)
    {
        for (GenderEnum gender : GenderEnum.values())
        {
            if (gender.name().equals(genderName))
            {
                return gender;
            }
        }
        throw new ErrorOnValidationException("Invalid value for MovieGenre: " + genderName);
    }
}
