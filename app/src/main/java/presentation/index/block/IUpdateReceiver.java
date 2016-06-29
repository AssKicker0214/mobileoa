package presentation.index.block;

/**
 * Created by Ian on 2016/6/27.
 */
public interface IUpdateReceiver {
    public void updateStatus(BlockStatus status);

    public void setHeader();

    public void setRate(String my, String all);
}
