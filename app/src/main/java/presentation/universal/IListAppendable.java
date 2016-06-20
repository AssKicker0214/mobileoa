package presentation.universal;

/**
 * Created by Ian on 2016/6/12.
 */
public interface IListAppendable {
    public void appendContent(boolean isAll, int page);

    public int getPageCount(boolean isAll);
}
