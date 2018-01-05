package me.snake.tools.config;

/**
 * Created by HP on 2017/12/27.
 */
public class Action {

    private Command request;
    private Command response;

    public Command getResponse() {
        return response;
    }

    public void setResponse(Command response) {
        this.response = response;
    }

    public Command getRequest() {
        return request;
    }

    public void setRequest(Command request) {
        this.request = request;
    }

}
