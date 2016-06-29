package presentation.index;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bl.CurrentLogin;
import bl.HttpProxy;
import presentation.business.BussinessIndexActivity;
import presentation.contact.ContactListActivity;
import presentation.contract.ContractListActivity;
import presentation.customer.CustomerListActivity;
import presentation.index.block.CustomerBlock;
import presentation.index.block.IndexBlock;
import presentation.index.block.IndexBlockController;
import presentation.opportunity.OpportunityListActivity;
import presentation.product.ProductListActivity;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ArrayList<IndexBlockController> controllers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setJump();

        if(CurrentLogin.logged){
            navigationView.getHeaderView(1);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_current_login_name)).setText(CurrentLogin.name);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_current_login_id)).setText(CurrentLogin.id);
//            ((TextView)findViewById(R.id.nav_current_login_id)).setText("id: "+CurrentLogin.id);

        }

        setUpBlock();
    }

    private void setUpBlock(){
        IndexBlockController cusController = new IndexBlockController("客户", this);
        controllers.add(cusController);
        IndexBlockController productController = new IndexBlockController("产品", this);
        controllers.add(productController);
        IndexBlockController oppoController = new IndexBlockController("商机", this);
        controllers.add(oppoController);
        IndexBlockController contractController = new IndexBlockController("合同", this);
        controllers.add(contractController);
        IndexBlockController contactController = new IndexBlockController("联系人", this);
        controllers.add(contactController);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.onStop();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setJump() {
        setListener(findViewById(R.id.customerRipple), CustomerListActivity.class);
        setListener(findViewById(R.id.oppoRipple), OpportunityListActivity.class);
        setListener(findViewById(R.id.contractRipple), ContractListActivity.class);
        setListener(findViewById(R.id.businessRipple), BussinessIndexActivity.class);
        setListener(findViewById(R.id.contactRipple), ContactListActivity.class);
        setListener(findViewById(R.id.productRipple), ProductListActivity.class);


    }

    private void setListener(View view, Class next){
        RippleView ripple = (RippleView) view;
        assert ripple != null;
        ripple.setOnRippleCompleteListener(new JumpListener(this, next));
        ripple.setFrameRate(5);
        ripple.setRippleDuration(100);
    }

    private void setDrawer(){

    }

    public void startUpdate(){
        for(IndexBlockController controller : controllers){
            controller.setUpTimer(10000);
            controller.setUpUITick(5000);
        }
    }

    public void stopUpdate(){
        for(IndexBlockController controller : controllers){
            controller.stopTimer();
            controller.stopUITick();
        }
    }

    public void onResume(){
        super.onResume();
        startUpdate();
    }

    public void onPause(){
        super.onPause();
        stopUpdate();
    }

}
