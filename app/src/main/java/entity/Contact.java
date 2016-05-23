package entity;

/**
 * Created by Ian on 2016/5/15.
 */
public class Contact {
    private String name;
    private String fixedlinePhone;
    private String mobilePhone;
    private Customer releventCus;

    public Contact(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFixedlinePhone(String numbers){
        this.fixedlinePhone = numbers;
    }

    public String getFixedlinePhone(){
        return fixedlinePhone;
    }

    public void setMobilePhone(String numbers){
        this.mobilePhone = numbers;
    }

    public String getMobilePhone(){
        return mobilePhone;
    }

    public void setReleventCus(Customer cus){
        this.releventCus = cus;
    }

    public Customer getReleventCus(){
        return releventCus;
    }
}
