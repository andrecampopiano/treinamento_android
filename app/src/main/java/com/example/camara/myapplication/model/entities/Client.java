package com.example.camara.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.camara.myapplication.model.persistence.SQLiteClientRepository;

import java.util.List;

/**
 * Created by Camara on 20/07/2015.
 */
public class Client implements Parcelable{
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String phone;

    private ClientAddress clientAddress;

    public ClientAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(ClientAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Client(){
        super();
    }

    public Client(Parcel in){
        readToParcel(in);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void save(){
        SQLiteClientRepository.getInstance().save(this);
    }

    public static List<Client> getAll(){
        return  SQLiteClientRepository.getInstance().getAll();
    }

    public void delete() {
        SQLiteClientRepository.getInstance().delete(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (address != null ? !address.equals(client.address) : client.address != null)
            return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        return !(clientAddress != null ? !clientAddress.equals(client.clientAddress) : client.clientAddress != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (clientAddress != null ? clientAddress.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", clientAddress=" + clientAddress +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(phone == null ? "" : phone);
        dest.writeString(address == null ? "" : address);
        dest.writeParcelable(clientAddress, flags);
    }

    private void readToParcel(Parcel in) {

        int particleId = in.readInt();
        id = particleId == -1 ? null : particleId;
        name = in.readString();
        int particleAge = in.readInt();
        age = particleAge == -1 ? null : particleAge;
        phone = in.readString();
        address = in.readString();
        clientAddress = in.readParcelable(ClientAddress.class.getClassLoader());
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>(){
        public Client createFromParcel(Parcel source){
            return new Client(source);
        }

        public Client[] newArray(int size){
            return new Client[size];
        }
    };

}
