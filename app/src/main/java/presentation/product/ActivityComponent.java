package presentation.product;

import bl.BLProvider;
import bl.BusinessBL;
import dagger.Component;
import presentation.business.BussinessIndexActivity;

/**
 * Created by Ian on 2016/6/29.
 */
@Component(modules = bl.BLProvider.class)
public interface ActivityComponent {
    void inject(ProductListActivity productListActivity);

    void inject(BussinessIndexActivity bussinessIndexActivity);


}
