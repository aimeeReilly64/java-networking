public class Pair<F, S> {
    private F first;
    private S second;

//contructors don't have void
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    //get and set
    public F getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }
    public void setFirst(F first) {
        this.first = first;
    }
    public void setSecond(S second) {
        this.second = second;
    }
}
