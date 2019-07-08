package by.bsu.slabko.vladislav.pharmhelper.searchResults;

public class SearchInfo {
    private static SearchInfo instance;
    public SearchInfo(){
        this.instance = this;
    }

    public static SearchInfo getInstance() {
        return instance;
    }

}
