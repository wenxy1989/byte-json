package snake.tools.config;

/**
 * Created by HP on 2017/12/27.
 */
public class Action implements Comparable<Action> {

    private int index;
    private Command request;
    private Command response;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    @Override
    public int compareTo(Action o) {
        return getIndex() - o.getIndex();
    }
}
