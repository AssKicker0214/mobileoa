package presentation.contact;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.ian.mobileoa.R;

import entity.Contact;
import presentation.customer.CustomerDetailActivity;
import presentation.universal.ListItem;

/**
 * Created by Ian on 2016/6/2.
 */
public class ContactListItem extends ListItem {
    private Contact contact;
    private TextView nameText;
    private TextView contactCustomerName;
    private TextView contactDepartmentName;
    private ImageView phoneImage;
    private ImageView emailImage;
    private ImageView qqImage;
    private ImageView wechatImage;
    public ContactListItem(Context context, Contact contact){
        super(context, 100);
        this.contact = contact;

        init();
    }

    private void init(){
        LinearLayout layout = (LinearLayout)inflate(this.getContext(), R.layout.contact_content, content.getMainContent());
        nameText = (TextView) layout.findViewById(R.id.smallContactName);
        nameText.setText(contact.name);
        contactCustomerName = (TextView) layout.findViewById(R.id.contact_customer_name);
        contactCustomerName.setText(contact.customerName);
        contactDepartmentName = (TextView) layout.findViewById(R.id.contact_department_name);
        contactDepartmentName.setText(contact.departmentName);

        phoneImage = (ImageView) layout.findViewById(R.id.smallContactPhone);
        emailImage = (ImageView) layout.findViewById(R.id.smallContactEmail);
        qqImage = (ImageView) layout.findViewById(R.id.smallContactQQ);
        wechatImage = (ImageView) layout.findViewById(R.id.smallContactWechat);
        setContactMeansState();


//        this.addContent(layout);
    }

    private void setContactMeansState(){
        if(!contact.fixedlinePhone.equals("") || !contact.mobilePhone.equals("")){
            phoneImage.setColorFilter(Color.parseColor("#43a047"));
        }
        if(!contact.email.equals("")){
            emailImage.setColorFilter(Color.parseColor("#43a047"));
        }
        if(!contact.qq.equals("")){
            qqImage.setColorFilter(Color.parseColor("#43a047"));
        }
        if(!contact.wechat.equals("")){
            wechatImage.setColorFilter(Color.parseColor("#43a047"));
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Contact", contact);
        Intent intent = new Intent(this.getContext(), ContactDetailActivity.class);
        intent.putExtras(bundle);
        this.getContext().startActivity(intent);
    }

    protected void addButtons(){
        //重写方法，不添加按钮
    }
}
