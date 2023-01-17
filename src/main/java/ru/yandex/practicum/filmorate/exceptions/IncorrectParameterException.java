package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectParameterException {
    private final String parameter;

    public IncorrectParameterException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
