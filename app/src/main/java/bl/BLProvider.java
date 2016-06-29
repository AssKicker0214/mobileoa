package bl;

import dagger.Module;
import dagger.Provides;
import entity.Customer;
import presentation.product.ProductListActivity;

/**
 * Created by Ian on 2016/6/29.
 * injection
 */

@Module
public class BLProvider {
    @Provides
    public ProductBL provideProductBL(){
        return new ProductBL();
    }

    @Provides
    public BusinessBL provideBusinessBL(){
        return new BusinessBL();
    }

    @Provides
    public CustomerBL provideCustomerBL(){
        return new CustomerBL();
    }

    @Provides
    public ContactBL provideContactBL(){
        return new ContactBL();
    }

    @Provides
    public ContractBL provideContractBL(){
        return new ContractBL();
    }
}
