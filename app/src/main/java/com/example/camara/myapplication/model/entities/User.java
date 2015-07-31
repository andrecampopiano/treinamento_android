package com.example.camara.myapplication.model.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.camara.myapplication.model.persistence.SQLiteUserRepository;

/**
 * Created by andre on 30/07/15.
 */
public class User implements Parcelable {

    private Integer id;
    private String usuario;
    private String password;

    public User(){
        super();
    }


    public User(Parcel in){
        readToParcel(in);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public User(Integer id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    public Boolean findUSer(){
        return  SQLiteUserRepository.getInstance().findUser(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!usuario.equals(user.usuario)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + usuario.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(usuario == null ? "" : usuario);
        dest.writeString(usuario == null ? "" : usuario);
    }

    public void readToParcel(Parcel in) {
        int particleId = in.readInt();
        id = particleId == -1 ? null : particleId;
        usuario = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel source){
            return new User(source);
        }

        public User[] newArray(int size){
            return new User[size];
        }
    };
    
}
